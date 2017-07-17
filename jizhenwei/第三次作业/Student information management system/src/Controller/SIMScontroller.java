package Controller;

import Model.SIMSmodel;
import Model.Student;
import View.SIMSview;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 小吉哥哥 on 2017/7/14.
 */
public class SIMScontroller {

    private final int CHECK_STUDENT_BY_RESULT_RING = 1;
    private final int CHECK_RESULT_BY_ID = 2;
    private final int CHECK_RESULT_BY_NAME = 3;
    private final int CHECK_STUDENT_BY_ID = 4;
    private final int CHECK_STUDENT_BY_NAME = 5;
    private final int SET_INFO = 6;
    private final int DELETE_INFO = 7;
    private final int SHOW_ALL = 8;
    private final int ORDER_BY_RESULT = 9;
    private final int ORDER_BY_RESULT_DESC = 10;
    private final int UNKNOW = -1;
    private final int INSERT = 11;
    private final int COMMAND_LIST = 12;

    private SIMSmodel mSIMSmodel;
    private SIMSview mSIMSview;

    public SIMScontroller(SIMSmodel model) {
        this.mSIMSmodel = model;
    }

    public void connect() {
        mSIMSmodel.connect("jdbc:mysql://localhost:3306/student", "root", "123456");
    }

    public void setView(SIMSview view) {
        this.mSIMSview = view;
    }

    //处理命令
    public void handleCommand(String input) {
        String[] inputArray = input.split("\\.");
        switch (what(input)) {
            case CHECK_STUDENT_BY_RESULT_RING:
                int max = Integer.parseInt(inputArray[3]) > Integer.parseInt(inputArray[5]) ? Integer.parseInt(inputArray[3]) : Integer.parseInt(inputArray[5]);
                int min = Integer.parseInt(inputArray[3]) < Integer.parseInt(inputArray[5]) ? Integer.parseInt(inputArray[3]) : Integer.parseInt(inputArray[5]);
                mSIMSview.showResultList(mSIMSmodel.getStudentsInResultRing(inputArray[1], min, max));
                break;
            case CHECK_RESULT_BY_ID:
                mSIMSview.checkResultFeedback(mSIMSmodel.getResult(Integer.parseInt(inputArray[2]), inputArray[1]));
                break;
            case CHECK_RESULT_BY_NAME:
                mSIMSview.checkResultFeedback(mSIMSmodel.getResult(inputArray[2], inputArray[1]));
                break;
            case CHECK_STUDENT_BY_ID:
                mSIMSmodel.getStudent(stringToId(inputArray[1]));
                break;
            case CHECK_STUDENT_BY_NAME:
                mSIMSmodel.getStudent(inputArray[1]);
                break;
            case SET_INFO:
                mSIMSview.commandFeedback(mSIMSmodel.setInfo(Integer.parseInt(inputArray[1]), inputArray[2], inputArray[4]));
                break;
            case DELETE_INFO:
                mSIMSview.commandFeedback(mSIMSmodel.deleteStudent(Integer.parseInt(inputArray[1])));
                break;
            case SHOW_ALL:
                mSIMSmodel.getAllStudents();
                break;
            case ORDER_BY_RESULT_DESC:
                System.out.println("ORDER_BY_RESULT_DESC");
                mSIMSview.showResultList(mSIMSmodel.orderByResultDesc(inputArray[2]));
                break;
            case ORDER_BY_RESULT:
                mSIMSview.showResultList(mSIMSmodel.orderByResult(inputArray[2]));
                break;
            case UNKNOW:
                System.out.println("unknown command");
                break;
            case INSERT:
                Student student = new Student();
                for (int i = inputArray.length - 2; i > 0; i -= 2) {
                    if (inputArray[i].equals("name")) {
                        student.setName(inputArray[i + 1]);

                    } else if (inputArray[i].equals("sex")) {
                        student.setSex(inputArray[i + 1]);

                    } else if (inputArray[i].equals("major")) {
                        student.setMajor(inputArray[i + 1]);

                    } else if (inputArray[i].equals("clas")) {
                        try {
                            student.setClas(Integer.parseInt(inputArray[i + 1]));
                        } catch (Exception e) {
                        }
                    }
                }
                mSIMSview.commandFeedback(mSIMSmodel.insertStudent(student));
                break;
            case COMMAND_LIST:
                mSIMSview.showCommandList();
                break;
        }
    }

    //判断命令是哪种
    private int what(String input) {
        String[] inputArray = input.split("\\.");
        if (inputArray.length == 6 && isOrderContain(input, "check.between.and") && stringToId(inputArray[5]) != -1 && stringToId(inputArray[3]) != -1) {
            //范围科目查找
            return CHECK_STUDENT_BY_RESULT_RING;
        } else if (isOrderContain(input, "check.")) {

            //System.out.println(input+"  "+inputArray.length);
            if (inputArray.length == 3) {
                //查成绩
                if (stringToId(inputArray[2]) != -1) {
                    //id查成绩
                    return CHECK_RESULT_BY_ID;
                } else {
                    //name查成绩
                    return CHECK_RESULT_BY_NAME;
                }
            } else {
                //查信息
                if (inputArray.length == 2 && stringToId(inputArray[1]) != -1) {
                    //id查信息
                    return CHECK_STUDENT_BY_ID;
                } else if (inputArray.length == 2) {
                    //name查信息
                    return CHECK_STUDENT_BY_NAME;
                }
            }
        } else if (inputArray.length == 5 && isOrderContain(input, "set.to") && stringToId(inputArray[1]) != -1) {
            //修改信息
            return SET_INFO;
        } else if (inputArray.length == 2 && isOrderContain(input, "delete.") && stringToId(inputArray[1]) != -1) {
            //删除信息
            return DELETE_INFO;
        } else if (input.equals("show.all")) {
            //显示所有学生
            return SHOW_ALL;
        } else if ((inputArray.length == 3 || inputArray.length == 4) && isOrderContain(input, "order.by")) {
            if (input.contains("desc")) {
                //按成绩降序
                return ORDER_BY_RESULT_DESC;
            } else {
                //按成绩升序
                return ORDER_BY_RESULT;
            }
        } else if (inputArray.length >= 3 && inputArray.length % 2 != 0 && isOrderContain(input, "insert.name.")) {
            int o = INSERT;
            for (int i = inputArray.length - 2; i > 0; i -= 2) {
                if (!inputArray[i].equals("name") && !inputArray[i].equals("sex") && !inputArray[i].equals("major") && !inputArray[i].equals("clas")) {
                    o = UNKNOW;
                }
            }
            return o;
        } else if(input.equals("\\help")){
            return COMMAND_LIST;
        }
        return UNKNOW;
    }


    //判断字符串是否顺序包含
    private static boolean isOrderContain(String text, String target) {
        if (text == null || target == null) {
            return false;
        }
        char[] targetArray = target.toCharArray();
        String textCopy = new String(text.toCharArray());
        for (int i = 0; i < targetArray.length; i++) {
            if (textCopy.indexOf(targetArray[i]) == -1) {
                return false;
            }
            if ((textCopy = textCopy.substring(textCopy.indexOf(targetArray[i]) + 1)).equals("") && i != targetArray.length - 1) {
                return false;
            }

        }
        return true;
    }

    //是id返回id，不是则返回-1
    private int stringToId(String input) {
        try {
            return Integer.parseInt(input);
        } catch (Exception e) {
            return -1;
        }
    }
}
