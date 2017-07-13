package FirstHomework;

import java.util.ArrayList;
import java.util.List;

public class Student extends Coder{
	
	private List<Homework> mHomeworkList = new ArrayList<>();
	
	public Student(String name, String group){
		super(name, group);
	}
	
	//接收作业
	public void receive(List<Homework> homeworkList){
		for(Homework homework : homeworkList){
			mHomeworkList.add(homework);
		}
		System.out.println(name + " receive homework...");
	}
	
	//做作业
	public void doHomework(){
		for(Homework homework : mHomeworkList){
			homework.setStudent(this);
			homework.setAnswer("答案");
		}
		System.out.println(name + " do homework...");
	}
	
	//提交作业
	public void submit(Tutor tutor){
		System.out.println(name + " submit homework...");
		tutor.receive(mHomeworkList);
	}
}
