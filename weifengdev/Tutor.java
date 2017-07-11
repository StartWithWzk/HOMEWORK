package FirstHomework;

import java.util.ArrayList;
import java.util.List;

public class Tutor extends Coder{

	public Tutor(String name, String group){
		super(name, group);
	}
	
	//待发布作业
	private List<Homework> todoList = new ArrayList<>();
	
	//已接收作业
	private List<Homework> receiveList = new ArrayList<>();
	
	//添加作业
	public void addHomework(Homework homework){
		todoList.add(homework);
	}
	
	//发布作业
	public void release(Student student){
		System.out.println(name + " release homework...");
		student.receive(todoList);
	}
	
	//接收作业
	public void receive(List<Homework> homeworkList){
		for(Homework homework : homeworkList){
			receiveList.add(homework);
		}
		System.out.println(name + " receive homework...");
	}
	
	//检查作业
	public void checkHomework(){
		System.out.println(name + " check homework...");
		for(Homework homework : receiveList){
			homework.show();
		}
	}
}
