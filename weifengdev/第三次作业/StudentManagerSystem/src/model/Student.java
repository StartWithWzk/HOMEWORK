package model;

import java.lang.reflect.Field;

/**
 * Created by 黄伟烽 on 2017/7/14.
 */
public class Student{

    private String number;
    private String major;
    private String clas;
    private String name;
    private String sex;
    private int Chinese;
    private int Maths;
    private int English;

    public Student(){
    }

    public Student(String number, String major, String clas, String name,
                   String sex, int chinese, int maths, int english) {
        this.number = number;
        this.major = major;
        this.clas = clas;
        this.name = name;
        this.sex = sex;
        Chinese = chinese;
        Maths = maths;
        English = english;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getClas() {
        return clas;
    }

    public void setClas(String clas) {
        this.clas = clas;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getChinese() {
        return Chinese;
    }

    public void setChinese(int chinese) {
        Chinese = chinese;
    }

    public int getMaths() {
        return Maths;
    }

    public void setMaths(int maths) {
        Maths = maths;
    }

    public int getEnglish() {
        return English;
    }

    public void setEnglish(int english) {
        English = english;
    }

    @Override
    public String toString() {
        String info = null;
        try{
            info = reflect(this);
        }catch (Exception e){
            e.printStackTrace();
        }
        return info;
    }

    public static String reflect(Student student) throws Exception{
        StringBuilder sb = new StringBuilder();
        Class cls = student.getClass();
        Field[] fields = cls.getDeclaredFields();
        for(int i=0; i<fields.length; i++){
            Field f = fields[i];
            f.setAccessible(true);
            sb.append(f.get(student) + "    ");
        }
        return sb.toString();
    }

    public static void main(String[] args){
        Student student = new Student("888","软工","软工","软工","计算机",4,5,6);
        try{
            System.out.println(reflect(student));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
