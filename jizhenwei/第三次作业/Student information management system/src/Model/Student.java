package Model;

/**
 * Created by 小吉哥哥 on 2017/7/14.
 */
public class Student {

    private int id;
    private String name = null;
    private String sex = null;
    private String major = null;
    private int chinese;
    private int math;
    private int english;
    private int clas = 0;

    public int getChinese() {
        return chinese;
    }

    public void setChinese(int chinese) {
        this.chinese = chinese;
    }

    public int getMath() {
        return math;
    }

    public void setMath(int math) {
        this.math = math;
    }

    public int getEnglish() {
        return english;
    }

    public void setEnglish(int english) {
        this.english = english;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public void setClas(int clas) {
        this.clas = clas;
    }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    public String getMajor() {
        return major;
    }

    public Student(int id, String name, String sex, String major, int clas) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.major = major;
        this.clas = clas;
    }

    public int getClas() {
        return clas;
    }

    @Override
    public String toString() {
        return "学号：" + id +" 姓名：" + name +" 性别：" + sex +" 专业：" + major +" 班级：" + clas;
    }

    public Student(String name, String sex, String major, int clas) {
        this(0, name, sex, major, clas);
    }

    public Student() {

    }

    public void showResult() {
        System.out.println("学号：" + id +" 姓名：" + name +" 语文：" + chinese +" 数学：" + math + " 英语：" + english);
    }
}
