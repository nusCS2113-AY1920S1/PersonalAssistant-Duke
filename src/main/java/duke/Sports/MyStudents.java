package duke.Sports;

/**
 * Represents a student.
 */
public class MyStudents {

    private String name;
    private int age;

    public MyStudents(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }

    public void changeName(String newName) {
        this.name = newName;
    }

    public void changeAge() {
        this.age++;
    }

}

