package duke.models.students;

import java.util.ArrayList;

//@@ danisheddie
public class Student implements IStudent {

    private String name;

    private String age;

    private String address;

    private ArrayList<String> progressList;

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
        this.progressList = new ArrayList<>();
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
        return getName() + "," + getAge() + "," + getAddress();
    }

    /**
     * This method is to add student progress.
     * @param progress description of progress.
     */
    public void addStudentProgress(String progress) {
        progressList.add(progress);
    }

    /**
     * To return te student progress.
     * @return student progress.
     */
    public String getStudentProgress() {
        StringBuilder temp = new StringBuilder();
        int i = 1;
        for (String s : progressList) {
            temp.append(i + ". " + s + "\n");
            i++;
        }
        return temp.toString();
    }

}
