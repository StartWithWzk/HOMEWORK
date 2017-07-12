package Main;

import Child.Mentor;
import Child.Student;

import javax.swing.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * Created by 小吉哥哥 on 2017/7/8.
 */
public class Main {
    private static Mentor mentor = new Mentor("a", "man", 1);
    private static Student student = new Student("b", "man", 2);


    public static void main(String[] args) {
        student.greeting(mentor);
        mentor.greeting(student);
        mentor.createHomeWork("woeifiwvnoqocmcsd");
        mentor.releaseHomeWork(student);
        student.doHomeWork();
        student.submitHomeWork(mentor);
        mentor.showHomeWork();
    }

}


