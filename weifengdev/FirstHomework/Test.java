package FirstHomework;

public class Test {

	public static void main(String[] args) {
		Student student = new Student("学员", "移动组");
		Tutor tutor = new Tutor("导师", "移动组");
		tutor.addHomework(new Homework("Java总结"));
		tutor.addHomework(new Homework("Android总结"));
		tutor.release(student);
		student.doHomework();
		student.submit(tutor);
		tutor.checkHomework();
	}

}
