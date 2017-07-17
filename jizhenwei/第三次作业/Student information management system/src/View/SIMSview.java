package View;

import Controller.SIMScontroller;
import Model.SIMSmodel;
import Model.Student;
import java.util.List;
import java.util.Scanner;

/**
 * Created by 小吉哥哥 on 2017/7/14.
 */
public class SIMSview implements SIMSmodel.ConnectListener, SIMSmodel.GetStudentListener {
    private final String help ="'\\help' 查看帮助";
    private final String manual = "=====本系统采用命令方式运行=====\n" +
                                  "1.'check.id' 以id查找信息\n" +
                                  "2.'check.name' 以name查找信息\n" +
                                  "3.'check.科目.id(或name)' 以name或id查找某科成绩\n" +
                                  "4.'set.学号.**.to.**' 修改该学号学生的**信息\n" +
                                  "5.'delete.学号' 删除该学号学生\n" +
                                  "6.'show.all' 显示所有学生\n" +
                                  "7.'order.by.科目' 升序显示\n" +
                                  "8.'order.by.科目.desc' 降序显示\n" +
                                  "9.'check.科目.between.**.and.**' 范围科目查找\n" +
                                  "10.'insert.name.**.clas.**.major.**.sex.**' 插入学生记录，一定要有name，其他可选，顺序可换\n" +
                                  "============================";
    private SIMSmodel mModel;
    private SIMScontroller mController;

    public SIMSview(SIMSmodel mModel, SIMScontroller mController) {
        this.mModel = mModel;
        this.mController = mController;

        setListener();
    }

    private void setListener() {
        mModel.setConnectListener(this);
        mModel.setGetStudentListener(this);
    }

    //连接成功后
    @Override
    public void succeed() {
        //打印规则
        System.out.println(help);
        //进入循环输入状态
        Scanner in = new Scanner(System.in);
        while (true) {
            mController.handleCommand(in.nextLine());
        }
    }

    @Override
    public void failed() {
        System.out.println("连接失败");
    }


    //ui结果反馈
    public void commandFeedback(boolean isSucceed) {
        if (isSucceed) {
            System.out.println("Successful operation");
        } else {
            System.out.println("Operation failed");
        }
    }

    //查成绩结果
    public void checkResultFeedback(int result) {
        if (result == -1) {
            System.out.println("查询失败");
        } else {
            System.out.println(result);
        }

    }
    //公布成绩
    public void showResultList(List<Student> list){
        for (Student student : list) {
            student.showResult();
        }
    }
    public void showCommandList(){
        System.out.println(manual);
    }

    @Override
    public void onGet(List<Student> list) {
        for (Student student : list) {
            System.out.println(student);
        }
    }
}
