package exercise;

import java.util.ArrayList;
import java.util.List;

public class Teacher extends Person {

	private List<Question> questionList = new ArrayList<Question>();
	
	public Teacher (String name,String sex,int age) {
		super (name,sex,age);
	}
	
	public void makeOutQuestion (int amount) {
		for(int i = 1;i <= amount;i++) {
			Question question = new Question();
			question.setQuestion ("����" + i);
			question.setMan("������" + this.getName());
			questionList.add(question);
		}
	}
	
	public List<Question> teacherResult () {
		return questionList;
	}
}
