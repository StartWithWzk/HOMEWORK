package Child;

import Super.HomeWork;
import Super.Person;

import java.util.*;

/**
 * Created by 小吉哥哥 on 2017/7/10.
 */
public class Student extends Person {
    //作业列表
    private List<HomeWork> homeWorkList = new ArrayList<>();

    public Student(String name, String sex, int age) {
        super(name, sex, age);
    }

    //提交作业
    public void submitHomeWork(Mentor to) {
        for (HomeWork homeWork : homeWorkList) {
            //找到对应导师的作业提交
            if (homeWork.releaseBy == to) {
                to.handHomeWork(homeWork);
            }
        }
        System.out.println("HomeWork submiting...");

    }

    //添加作业
    public void addHomeWork(HomeWork homeWork) {
        homeWorkList.add(homeWork);
        System.out.println("HomeWork received");
    }

    //做作业
    public void doHomeWork() {
        System.out.println("HomeWork doing...");
        for (HomeWork homeWork : homeWorkList) {
            homeWork.doneBy = this;
            homeWork.answer = "adsqwdqwdas";
        }
    }
}
