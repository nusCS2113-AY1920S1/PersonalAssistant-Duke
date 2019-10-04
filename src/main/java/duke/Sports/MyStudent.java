package duke.Sports;

/**
 * Represents a student.
 */
public class MyStudent{

    private String name;
    private String age;
    private String address;

    /**
     * Constructor for the students
     *
     * @param name Name of the student
     * @param age age of the student
     */
    public MyStudent(String name, String age) {
        this.name = name;
        this.age = age;
//        this.address = address;
    }

    /**
     * This method is to retrieve the name of the student.
     * @return name of student
     */
    public String getName() {
        return this.name;
    }

    /**
     * This method is to retrieve the age of the student.
     * @return age of student
     */
    public String getAge() {
        return this.age;
    }

    /**
     * This method is to edit the name of the student.
     * @param newName
     */
    public void changeName(String newName) {
        this.name = newName;
    }

//    /**
//     * This method is to increment/update the age of the student.
//     */
//    public void changeAge() {
//        this.age++;
//    }

    /**
     * This method is to retrieve the address of the student.
     * @return
     */
    public String getAddress() {
        return address;
    }

    /**
     * This method prints out the student name and their address (Or any RELEVANT details)
     * @return
     */
    public String toString() {
        return getName() + ", " + getAge();
    }


}