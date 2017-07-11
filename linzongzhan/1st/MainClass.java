package exercise;

import java.util.ArrayList;
import java.util.List;

public class MainClass {
	
	private static List<HomeWork> homeworkList = new ArrayList<HomeWork> ();
	private static List<Question> questionList = new ArrayList<Question>();

	static Question question = new Question();
	static HomeWork homework = new HomeWork();
	
	public static void main (String[] args) {
		
		Teacher teacher = new Teacher ("导师","男",22);
		Student student = new Student ("学员","男",20);
		
		teacher.makeOutQuestion(10);
		student.doHomework(10);
		
		questionList = teacher.teacherResult();
		homeworkList = student.studentResult();
		
		for(int i = 0;i < questionList.size();i++) {
			question = questionList.get(i);
			System.out.println(question.getMan() + " " + question.getQuestion());
		}
		
		System.out.println("\n");
		
		for(int i = 0;i < homeworkList.size();i++) {
			homework = homeworkList.get(i);
			System.out.println(homework.getWorkman() + " " + homework.getAnswer());
		}
		
	}
}
