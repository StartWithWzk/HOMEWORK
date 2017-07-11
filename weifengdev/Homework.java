package FirstHomework;

import java.util.Date;

public class Homework {

	private Student student;  //学生
	private String todo;	  //作业题目
	private String answer;	  //提交内容
	
	public Homework(Student student, String todo, String answer){
		this.student = student;
		this.todo = todo;
	}
	
	public Homework(String todo){
		this(null, todo, null);
	}
	
	
	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public String getTodo() {
		return todo;
	}

	public void setTodo(String todo) {
		this.todo = todo;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	//显示作业详情
	public void show(){
		System.out.println("name is " + student.name);
		System.out.println("todo is " + todo);
		System.out.println("answer is " + answer);
	}
}
