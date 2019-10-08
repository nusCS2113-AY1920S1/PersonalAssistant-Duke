package duke.Sports;

/**
 * Represents a student.
 */
public class MyStudent {

    /**
     * Represents the name of the student.
     */
    private String name;

    /**
     * Represents the age of the student.
     */
    private String age;

    /**
     * Represents the address of the student.
     */
    private String address;

    /**
     * Constructor for the students.
     *
     * @param myname Name of the student
     * @param myage age of the student
     * @param myaddress address of the student
     */
    public MyStudent(final String myname, final String myage,
                     final String myaddress) {
        this.name = myname;
        this.age = myage;
        this.address = myaddress;
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
     * @param newName Represents the name of the student to change to.
     */
    public void changeName(final String newName) {
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
     * @return Represents the address of the student.
     */
    public String getAddress() {
        return address;
    }

    /**
     * This method prints out the student name and their address.
     * (Or any RELEVANT details)
     * @return Represents a string containing the student details to be shown,
     * name and address.
     */
    public String toString() {
        return getName() + ", " + getAge();
    }


}
