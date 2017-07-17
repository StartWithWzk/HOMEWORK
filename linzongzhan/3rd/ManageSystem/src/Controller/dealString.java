package Controller;

import Model.JudgeString;

/**
 * Created by linzongzhan on 2017/7/14.
 */
public class dealString {

    public static void add (String input,String add) {

        String line;

        String name;
        String sex;
        String number;
        String major;
        String clas;

        //将字符串中的信息拆开分别赋值给不同的字符串
        line = input.replace(add,"");
        name = line.substring(0,line.indexOf(","));
        line = line.replace(name + ",","");
        sex = line.substring(0,line.indexOf(","));
        line = line.replace(sex + ",","");
        number = line.substring(0,line.indexOf(","));
        line = line.replace(number + ",","");
        major = line.substring(0,line.indexOf(","));
        line = line.replace(major + ",","");
        clas = line;

        //以下调用数据库处理函数
        JudgeString.add(name,sex,number,major,clas);
    }

    public static void delete (String input,String delete) {

        String nameORnumber;

        nameORnumber = input.replace(delete,"");

        //以下调用数据库处理函数
        JudgeString.delete(nameORnumber);
    }

    public static void change (String input,String change) {

        String nameORnumber;

        String project;

        String result;

        String line;

        line = input.replace(change,"");
        nameORnumber = line.substring(0,line.indexOf(","));
        line = line.replace(nameORnumber + ",","");
        System.out.println(line.indexOf(","));
        project = line.substring(0,line.indexOf(","));
        line = line.replace(project + ",","");
        result = line;

        //以下调用数据库处理函数
        JudgeString.change(nameORnumber,project,result);
    }

    public static void checkAllMessage (String input,String checkAllMessage) {

        String nameORnumber;

        nameORnumber = input.replace(checkAllMessage,"");

        //以下调用数据库处理函数
        JudgeString.checkAllMessage(nameORnumber);
    }

    public static void addChineseMark (String input,String addChineseMark) {

        String nameORnumber;

        String result;

        String line;

        line = input.replace(addChineseMark,"");
        nameORnumber = line.substring(0,line.indexOf(","));
        line = line.replace(nameORnumber + ",","");
        result = line;

        //
        JudgeString.addChineseMark(nameORnumber,result);
    }

    public static void checkChineseMark (String input,String checkChineseMark) {

        String nameORnumber;

        nameORnumber = input.replace(checkChineseMark,"");

        //
        JudgeString.checkChineseMark(nameORnumber);
    }

    public static void addMathMark (String input,String addMathMark) {

        String nameORnumber;

        String result;

        String line;

        line = input.replace(addMathMark,"");
        nameORnumber = line.substring(0,line.indexOf(","));
        line = line.replace(nameORnumber + ",","");
        result = line;

        //
        JudgeString.addMathMark(nameORnumber,result);
    }

    public static void checkMathMark (String input,String checkMathMark) {

        String nameORnumber;

        nameORnumber = input.replace(checkMathMark,"");

        //
        JudgeString.checkMathMark(nameORnumber);
    }

    public static void addEnglishMark (String input,String addEnglishMark) {

        String nameORnumber;

        String result;

        String line;

        line = input.replace(addEnglishMark,"");
        nameORnumber = line.substring(0,line.indexOf(","));
        line = line.replace(nameORnumber + ",","");
        result = line;

        //
        JudgeString.addEnglishMark(nameORnumber,result);
    }

    public static void checkEnglishMark (String input,String checkEnglishMark) {

        String nameORnumber;

        nameORnumber = input.replace(checkEnglishMark,"");

        //
        JudgeString.checkEnglishMark(nameORnumber);
    }

    public static void sort (String input,String sort) {

        String subject;

        subject = input.replace(sort,"");

        //
        JudgeString.sort(subject);
    }

    public static void check (String input,String check) {

        String line;

        String subject;

        String lower;
        String upper;

        line = input.replace(check,"");
        subject = line.substring(0,line.indexOf(","));
        line = line.replace(subject + ",","");
        lower = line.substring(0,line.indexOf(","));
        line = line.replace(lower + ",","");
        upper = line;

        //
        JudgeString.check(subject,lower,upper);
    }
}
