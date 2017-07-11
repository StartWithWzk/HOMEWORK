package exercise;

import java.util.ArrayList;
import java.util.List;

public class Student extends Person {

	private List<HomeWork> homeworkList = new ArrayList<HomeWork> ();
	
	public Student (String name,String sex,int age) {
		super (name,sex,age);
	}
	
	public void doHomework (int amount) {
		for(int i = 1;i <= amount;i++) {
			HomeWork homework = new HomeWork ();
			homework.setAnswer("答案" + i);
			homework.setWorkman("解题人" + this.getName());
			homeworkList.add(homework);
		}
	}
	
	public List<HomeWork> studentResult () {
		return homeworkList;
	}
}
