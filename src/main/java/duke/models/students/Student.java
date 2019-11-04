package duke.models.students;

/**
 * Represents a student.
 */
public class Student implements IStudent {

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
     * @param myName    Name of the student
     * @param myAge     age of the student
     * @param myAddress address of the student
     */

    public Student(final String myName, final String myAge,
                   final String myAddress) {
        this.name = myName;
        this.age = myAge;
        this.address = myAddress;
    }

    /**
     * Method to update the details of the student.
     * @param name new name.
     * @param age new age.
     * @param address new address.
     */
    @Override
    public void updateDetails(String name, String age, String address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

    /**
     * The index of the student.
     * @return index
     */
    @Override
    public int getIndexNumber() {
        return 0;
    }

    /**
     * This method is to retrieve the name of the student.
     *
     * @return name of student
     */
    public String getName() {
        return this.name;
    }

    /**
     * This method is to retrieve the age of the student.
     *
     * @return age of student
     */
    public String getAge() {
        return this.age;
    }

    /**
     * This method is to retrieve the address of the student.
     *
     * @return Represents the address of the student.
     */
    public String getAddress() {
        return this.address;
    }

    /**
     * To get the details of the student.
     * @return student details.
     */
    @Override
    public String getDetails() {
        return "Name: " + getName() + "\nAge: " + getAge() + "\nAddress: "
                + getAddress();
    }

    /**
     * This method prints out the student name and their address.
     * (Or any RELEVANT details)
     *
     * @return Represents a string containing the student details to be shown,
     *         name and address.
     */
    public String toString() {
        return "Name: " + getName() + "\nAge: " + getAge() + "\nAddress: "
            + getAddress();
    }

    /**
     * Format to save student list into text.
     * @return student list.
     */
    public String getFormat() {
        return getName() + ", " + getAge() + ", " + getAddress();
    }


}
