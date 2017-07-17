package control;

import model.OperateDateBase;
import model.Student;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 黄伟烽 on 2017/7/16.
 */
public class Controller {

    private OperateDateBase odb = new OperateDateBase();

    public void dealWithInput(String line) {
        String[] strings = line.split(" ");
        String operation = strings[0];
        switch (operation){
            case "add":
                addStudent(strings);
                break;
            case "update":
                if(strings.length == 4){
                    updateStuCol(strings);
                }else if(strings.length == 9){
                    updateStudent(strings);
                }else{
                    System.out.println("无效命令，请重新输入");
                }
                break;
            case "delete":
                deleteStudent(strings);
                break;
            case "query1":
                queryStuAllInfo(strings);
                break;
            case "query2":
                queryStuSub(strings);
                break;
            case "query3":
                sortBySub(strings);
                break;
            case "query4":
                queryByRange(strings);
                break;
            default:
                System.out.println("无效命令，请重新输入");
                break;
        }
    }

    private void queryByRange(String[] strings) {
        if(strings.length != 4){
            System.out.println("输入的命令格式有误");
            return;
        }
        if(!isNumber(strings[2]) || !isNumber(strings[3])){
            System.out.println("学号非纯数字");
            return;
        }
        if(!isSubject(strings[1])){
            System.out.println("输入的科目名称有误");
            return;
        }
        int min = Integer.parseInt(strings[2]);
        int max = Integer.parseInt(strings[3]);
        if(min > max){
            int temp = min;
            min = max;
            max = temp;
        }
        String result = odb.queryByRange(strings[1], max, min);
        if(result != null){
            System.out.println(result);
        }else {
            System.out.println("列表无任何学生信息");
        }
    }

    private void sortBySub(String[] strings) {
        if(strings.length != 2){
            System.out.println("输入的命令格式有误");
            return;
        }
        if(!isSubject(strings[1])){
            System.out.println("输入的科目名称有误");
            return;
        }
        String result = odb.sortBySub(strings[1]);
        if(result != null){
            System.out.println(result);
        }else {
            System.out.println("列表无任何学生信息");
        }
    }

    private void queryStuSub(String[] strings) {
        if(strings.length != 3){
            System.out.println("输入的命令格式有误");
            return;
        }
        if(!isNumber(strings[1])){
            System.out.println("学号非纯数字");
            return;
        }
        if(!isSubject(strings[2])){
            System.out.println("输入的科目名称有误");
            return;
        }
        String result = odb.queryStuSub(strings[1], strings[2]);
        if(result != null){
            System.out.println(result);
        }else {
            System.out.println("列表无该学生信息");
        }
    }

    private void queryStuAllInfo(String[] strings) {
        if(strings.length != 2){
            System.out.println("输入的命令格式有误");
            return;
        }
        if(!isNumber(strings[1])){
            System.out.println("学号非纯数字");
            return;
        }
        String result = odb.queryStuAllInfo(strings[1]);
        if(result != null){
            System.out.println(result);
        }else {
            System.out.println("列表无该学生信息");
        }
    }

    private void deleteStudent(String[] strings) {
        if(strings.length != 2){
            System.out.println("输入的命令格式有误");
            return;
        }
        if(!isNumber(strings[1])){
            System.out.println("学号非纯数字");
            return;
        }
        int result = odb.deleteStudent(strings[1]);
        if(result == 1){
            System.out.println("删除学生信息成功");
        }else {
            System.out.println("删除学生信息失败");
        }
    }

    private void updateStudent(String[] strings) {
        if(strings.length != 9){
            System.out.println("输入的命令格式有误");
            return;
        }
        int[] index = {1,6,7,8};
        for(int i = 0; i < 4; i++){
            if(!isNumber(strings[index[i]])){
                System.out.println("学号或成绩非纯数字");
                return;
            }
        }
        Student student = packStudent(strings);
        int result = odb.updateStudent(student);
        if(result == 1){
            System.out.println("修改学生信息成功");
        }else {
            System.out.println("修改学生信息失败");
        }
    }

    private void updateStuCol(String[] strings){
        if(!isNumber(strings[1])){
            System.out.println("学号非纯数字");
            return;
        }
        String column = strings[2].toLowerCase();
        String[] allColumn = {"major", "class", "sex", "number", "chinese", "maths", "english"};
        boolean isContain = false;
        for(String str : allColumn){
            if (str.equals(column)){
                isContain = true;
                break;
            }
        }
        if(isContain == false){
            System.out.println("单项名称输入错误");
            return;
        }
        int result = odb.updateStuCol(strings[1],strings[2],strings[3]);
        if(result == 1){
            System.out.println("修改学生信息成功");
        }else {
            System.out.println("列表中无该学生");
        }
    }

    private void addStudent(String[] strings) {
        if(strings.length != 9){
            System.out.println("输入的命令格式有误");
            return;
        }
        int[] index = {1,6,7,8};
        for(int i = 0; i < 4; i++){
            if(!isNumber(strings[index[i]])){
                System.out.println("学号或成绩非纯数字");
                return;
            }
        }
        Student student = packStudent(strings);
        int result = odb.addStudent(student);
        if(result == 1){
            System.out.println("添加学生信息成功");
        }else {
            System.out.println("添加学生信息失败");
        }
    }

    private boolean isNumber(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if(!isNum.matches()){
            return false;
        }
        return true;
    }

    private boolean isSubject(String str){
        String subject = str.toLowerCase();
        if(subject.equals("chinese") || subject.equals("maths") || subject.equals("english")){
            return true;
        }
        return false;
    }

    private Student packStudent(String[] strings){

        Student student = new Student();
        student.setNumber(strings[1]);
        student.setMajor(strings[2]);
        student.setClas(strings[3]);
        student.setName(strings[4]);
        student.setSex(strings[5]);
        student.setChinese(Integer.parseInt(strings[6]));
        student.setMaths(Integer.parseInt(strings[7]));
        student.setEnglish(Integer.parseInt(strings[8]));
        return student;
    }
}
