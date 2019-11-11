package duke.models.students;

import java.util.ArrayList;

//@@ danisheddie
public class StudentList implements IStudentList {
    /**
     * An array list for the list of students.
     */
    private final ArrayList<Student> studentList;


    /**
     * Constructor of Manage Students to initialise ManageStudent class.
     */
    public StudentList() {
        studentList = new ArrayList<>();
    }

    /**
     * Function to get all available students in the list.
     *
     * @return list of all the students.
     */
    public ArrayList<Student> getStudentList() {
        return studentList;
    }

    /**
     * Method to list all the names of the students in the list.
     */
    public void listAllStudents() {
        int index = 1;
        for (Student i : getStudentList()) {
            System.out.println(index++ + ". " + i.getName());
        }
    }

    /**
     * This method retrieve the total number of student in the list.
     *
     * @return Number of student in the list.
     */
    public int getStudentListSize() {
        return studentList.size();
    }

    /**
     * Method to return the name of the student at the particular index.
     *
     * @param index of the student in the list.
     * @return name of student.
     */
    public String getStudentName(final int index) {
        return studentList.get(index - 1).getName();
    }

    public Student getStudent(int index) {
        return studentList.get(index - 1);
    }

    /**
     * Adding a new student and listing the total number of students.
     *
     * @param name of the new student added
     */
    public void addStudent(final Student name) {
        studentList.add(name);
        System.out.println("Student have been added: \n"
            + getStudentList().get(getStudentListSize() - 1).toString()
            + "\n"
            + "Now you have " + getStudentListSize() + " students.");
    }

    /**
    @Override
    public final void getStudent(final int i) {
        System.out.println(studentList.get(i - 1).toString());
    }
    */


    /**
     * Method to remove a particular student in a list.
     *
     * @param index of the student in the list.
     */
    public void deleteStudent(final int index) {
        try {
            System.out.println("Noted.\n"
                + getStudentList().get(index - 1).getName()
                + " has been removed from the list.");
            if ((getStudentListSize() - 1) == 0) {
                System.out.println("Now you have no student in your list.");
            } else {
                System.out.println("Now you have "
                    + (getStudentListSize() - 1)
                    + " students in your list.");
            }
            getStudentList().remove(index - 1);
        } catch (IndexOutOfBoundsException e) {
            if (getStudentListSize() == 0) {
                System.err.println("Oops! "
                    + "You have no student in the list!");
            } else {
                System.err.println("Oops! You only have "
                    + getStudentListSize()
                    + " students in the list!");
            }

        }
    }

}


