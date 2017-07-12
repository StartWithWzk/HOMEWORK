package Super;

import Child.Mentor;
import Child.Student;

/**
 * Created by 小吉哥哥 on 2017/7/10.
 */
public class HomeWork {
    public Mentor releaseBy;
    public String toDo;
    public String answer;
    public Student doneBy;

    public HomeWork(Mentor releaseBy, String toDo, String answer, Student doneBy) {
        this.releaseBy = releaseBy;
        this.toDo = toDo;
        this.answer = answer;
        this.doneBy = doneBy;
    }

    public HomeWork(Mentor releaseBy, String toDo, String answer) {
        this(releaseBy, toDo, answer, null);
    }

    public HomeWork(Mentor releaseBy, String toDo) {
        this(releaseBy, toDo, null);
    }

    public void show() {
        System.out.println("releaseBy : "+ releaseBy.name);
        System.out.println("toDo : " + toDo);
        System.out.println("answer : " + answer);
        System.out.println("doneBy : " + doneBy.name);
    }
    //复制
    public HomeWork copy() {
        return new HomeWork(this.releaseBy,this.toDo, this.answer, this.doneBy);
    }
}
