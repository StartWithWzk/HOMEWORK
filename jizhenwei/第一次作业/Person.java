package Super;

import java.util.HashMap;
import java.util.Map;

public class Person {


    private int age;
    public final String name;
    public final String sex;

    public Person(String name, String sex, int age) {
        this.age = age;
        this.name = name;
        this.sex = sex;
    }

    public void greeting() {
        System.out.println("Hello");
    }

    public void greeting(Person person) {
        System.out.println("Hello," + person.name);
    }

}
