package duke.sports;

import duke.module.Details;

/**
 * Represents a student.
 */
public class MyStudent implements Details {

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
     * @param myName    Name of the student
     * @param myAge     age of the student
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
        return address;
    }

    @Override
    public String getDetails() {
        return null;
    }

    @Override
    public void updateDetails(String name, String age, String address) {

    }

    @Override
    public void getIndexNumber() {

    }

    @Override
    public String getParticulars() {
        return null;
    }

    @Override
    public String updateParticulars() {
        return null;
    }

    @Override
    public void updateParticulars(String contact, String gender, String email, String DOB) {

    }

    @Override
    public String getPersonalBest() {
        return null;
    }

    @Override
    public String getDietaryPlan() {
        return null;
    }

    @Override
    public String getNotes() {
        return null;
    }

    /**
     * This method prints out the student name and their address.
     * (Or any RELEVANT details)
     *
     * @return Represents a string containing the student details to be shown,
     * name and address.
     */
    public String toString() {
        return "Name: " + getName() + "\nAge: " + getAge() + "\nAddress: " + getAddress();
    }


}
