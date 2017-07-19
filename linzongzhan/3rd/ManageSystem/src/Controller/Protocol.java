package Controller;

/**
 * Created by linzongzhan on 2017/7/14.
 */
public class Protocol {

    private String add = "add:";
    private String delete = "delete:";
    private String change = "change:";
    private String checkAllMessage = "checkAllMessage:";
    private String checkChineseMark = "checkChineseMark:";
    private String addChineseMark = "addChineseMark:";
    private String checkMathMark = "checkMathMark:";
    private String addMathMark = "addMathMark:";
    private String checkEnglishMark = "checkEnglishMark:";
    private String addEnglishMark = "addEnglishMark:";
    private String sort = "sort:";
    private String check = "check:";
    private String help = "help";
    private String message = "***********************学生信息管理系统***********************\n" +
            "1.添加学生信息--add:姓名,性别,学号,专业,班级\n" +
            "2.删除学生--delete:姓名\n" +
            "3.修改学生信息--change:姓名,要修改的信息种类,修改后的信息\n" +
            "4.查看学生全部信息--checkAllMessage:姓名\n" +
            "5.查看学生语文成绩（数学，英语以此类推)--checkChineseMark:姓名\n" +
            "6.添加学生语文成绩 (数学，英语以此类推)--addChineseMark:姓名\n" +
            "7.根据学科排序--sort:学科\n" +
            "8.根据学科范围查找--check:学科,分数下限,分数上限\n" +
            "注意：逗号为英文的逗号。\n" +
            "***************************************************************";
    public boolean cheak (String input) {
        if (input.indexOf("，") != -1) {
            System.out.println("输入的逗号需为英文的逗号");
            return false;
        } else if (input.indexOf(add) == 0) {
            dealString.add(input,add);
            return true;
        } else if (input.indexOf(delete) == 0) {
            dealString.delete(input,delete);
            return true;
        } else if (input.indexOf(change) == 0) {
            dealString.change(input,change);
            return true;
        } else if (input.indexOf(checkAllMessage) == 0) {
            dealString.checkAllMessage(input,checkAllMessage);
            return true;
        } else if (input.indexOf(checkChineseMark) == 0) {
            dealString.checkChineseMark(input,checkChineseMark);
            return true;
        } else if (input.indexOf(checkMathMark) == 0) {
            dealString.checkMathMark(input,checkMathMark);
            return true;
        } else if (input.indexOf(checkEnglishMark) == 0) {
            dealString.checkEnglishMark(input,checkEnglishMark);
            return true;
        } else if (input.indexOf(addChineseMark) == 0) {
            dealString.addChineseMark(input,addChineseMark);
            return true;
        } else if (input.indexOf(addMathMark) == 0) {
            dealString.addMathMark(input,addMathMark);
            return true;
        } else if (input.indexOf(addEnglishMark) == 0) {
            dealString.addEnglishMark(input,addEnglishMark);
            return true;
        } else if (input.indexOf(sort) == 0) {
            dealString.sort(input,sort);
            return true;
        } else if (input.indexOf(check) == 0) {
            dealString.check(input,check);
            return true;
        } else if (input.indexOf(help) == 0) {
            System.out.println(message);
            return true;
        } else {
            System.out.println("输入命令错误");
            return false;
        }
    }

}
