package exercise;

public class Question {

	private String question;
	private String man;
	//设置问题
	public void setQuestion (String question) {
		this.question = question;
	}
	//设置出题人
	public void setMan (String man) {
		this.man = man;
	}
	//得到答案
	public String getQuestion () {
		return question;
	}
	//得到出题人
	public String getMan () {
		return man;
	}
}
