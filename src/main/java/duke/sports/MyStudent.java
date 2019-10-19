package duke.sports;

/**
 * Represents a student.
 */
public class MyStudent {

    /**
     * Represents the name of the student.
     */
    private final String name;

    /**
     * Represents the age of the student.
     */
    private final String age;

    /**
     * Represents the address of the student.
     */
    private final String address;

    /**
     * Constructor for the students.
     *
     * @param myName Name of the student
     * @param myAge age of the student
     * @param myAddress address of the student
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public MyStudent(final String myName, final String myAge,
                     final String myAddress) {
        this.name = myName;
        this.age = myAge;
        this.address = myAddress;
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
    private String getAge() {
        return this.age;
    }

    /**
     * This method is to retrieve the address of the student.
     * @return Represents the address of the student.
     */
    private String getAddress() {
        return address;
    }

    /**
     * This method prints out the student name and their address.
     * (Or any RELEVANT details)
     * @return Represents a string containing the student details to be shown,
     * name and address.
     */
    public String toString() {
        return "Name: " + getName() + "\nAge: " + getAge() + "\nAddress: "
                + getAddress();
    }


}
