package Child;

import Super.HomeWork;
import Super.Person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 小吉哥哥 on 2017/7/10.
 */
public class Mentor extends Person {
    //作业发布表
    private List<HomeWork> homeWorkToRelease = new ArrayList<>();
    //作业接受表
    private List<HomeWork> homeWorkList = new ArrayList<>();
    public Mentor(String name, String sex, int age) {
        super(name, sex, age);
    }
    //生成作业
    public void createHomeWork(String toDo) {
        homeWorkToRelease.add(new HomeWork(this,toDo,null,null));
        System.out.println("HomeWork creating...");
    }
    //发布作业
    public void releaseHomeWork(Student to) {
        System.out.println("HomeWork releasing...");
        for (HomeWork homeWork : homeWorkToRelease) {
               //生成作业副本发布
                to.addHomeWork(homeWork.copy());
        }
    }
    //接受作业
    public void handHomeWork(HomeWork homeWork){
        if (homeWorkList.contains(homeWork)) {
            return;
        }
        homeWorkList.add(homeWork);
    }
    //查看作业
    public void showHomeWork(){
        for (HomeWork homeWork : homeWorkList) {
            homeWork.show();
        }
    }

}



