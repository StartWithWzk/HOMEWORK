package view;

/**
 * Created by 黄伟烽 on 2017/7/16.
 */
public class Console {

    public void illustrate(){
        System.out.println("一一一一一一一一一一一一一一学生信息管理系统操作命令一一一一一一一一一一一一一一");
        System.out.println("|  增加学生信息：add number major class name sex Chinese Maths English         |");
        System.out.println("|  修改学生全部信息：update number major class name sex Chinese Maths English|");
        System.out.println("|  修改学生单项信息：update number column info                                 |");
        System.out.println("|  删除学生信息：delete number                                                 |");
        System.out.println("|  查看某学生全部信息：query1 number                                           |");
        System.out.println("|  查看某学生某学科成绩：query2 number subejct                                 |");
        System.out.println("|  按某学科成绩排序：query3 subject                                            |");
        System.out.println("|  按某学科成绩进行范围查找：query4 subject min max                            |");
    }

}
