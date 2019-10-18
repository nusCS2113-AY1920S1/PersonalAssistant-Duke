package duke.sports;

import duke.module.Details;

import java.util.ArrayList;

/**
 * This class is called when option 2 is chosen.
 * All commands related to students will be managed by this class
 */
public class ManageStudents implements Details {

    /**
     * An array list for the list of students.
     */
    private ArrayList<MyStudent> studentList;

    private String detailType;

    private String detailMessage;

    /**
     * Constructor of Manage Students to initialise ManageStudent class.
     */
    public ManageStudents() {
        studentList = new ArrayList<>();
    }



//    public void setStudentList(ArrayList<MyStudent> studentList.txt) {
//        this.studentList.txt = studentList.txt;
//    }

    /**
     * @return list of all the students.
     */
    public ArrayList<MyStudent> getStudentList() {
        return studentList;
    }

//    public void addStudentList (Storage storage) {
//        try {
//            setStudentList((Objects.requireNonNull(storage.loadFile())));
//        } catch (NullPointerException e) {
//            System.out.println("No previous list loaded");
//        }
//    }

    /**
     * Method to list all the names of the students in the list.
     */
    public void listAllStudents() {
        System.out.println("Here are your list of students: ");
        int index = 1;
        for (MyStudent i : getStudentList()) {
            System.out.println(index++ + ". " + i.getName());
        }
    }

    /**
     * This method retrieve the total number of student in the list.
     * @return Number of student in the list.
     */
    private int getStudentListSize() {
        return studentList.size();
    }

    /**
     * Method to return the name of the student at the particular index
     * @param index of the student in the list.
     * @return name of student.
     */
    public void getStudentName(int index) {
        System.out.println("You have selected:");
        System.out.println(getStudentList().get(index - 1));
    }

    /**
     * Adding a new student and listing the total number of students.
     * @param name of the new student added
     */
    @SuppressWarnings("checkstyle:LineLength")
    public void addStudent(final MyStudent name) {
        studentList.add(name);
        System.out.println("Student have been added: \n"
                + getStudentList().get(getStudentListSize() - 1).toString()
                + "\n"
                + "Now you have " + getStudentListSize() + " students.");
    }

    public void setDetail(String type, String detailMessage) {
        this.detailType = type;
        this.detailMessage = detailMessage;
    }

    public String getDetailType() {
        return detailType;
    }

    public String getDetailMessage() {
        return detailMessage;
    }

    /**
     * Method to remove a particular student in a list.
     * @param index of the student in the list.
     */
    public void deleteStudent(int index) {
        try {
            System.out.println("Noted.\n" + getStudentList().get(index - 1).getName() + " has been removed from the list.");
            if ((getStudentListSize() - 1) == 0) {
                System.out.println("\nNow you have no student in your list.");
            } else if ((getStudentListSize() - 1) == 1) {
                System.out.println("\nNow you have " + (getStudentListSize() - 1) + " student in your list.");
            } else {
                System.out.println("\nNow you have " + (getStudentListSize() - 1) + " students in your list.");
            }
            getStudentList().remove(index - 1);
        } catch (IndexOutOfBoundsException e) {
            if (getStudentListSize() == 0) {
                System.err.println("Oops! You only have no student in the list!");
            } else if (getStudentListSize() == 1) {
                System.err.println("Oops! You only have " + getStudentListSize() + " student in the list!");
            } else {
                System.err.println("Oops! You only have " + getStudentListSize() + " students in the list!");
            }

        }
    }

    /**
     * A method to find a specific student in the list
     * @param name of student to search
     * @return
     */
    public String findName(String name) {
        StringBuilder listString = new StringBuilder();
        int index = 1;
        for (MyStudent i : getStudentList()) {
            if (i.getName().contains(name)) {
                if (index == 1) {
                    System.out.println("Here are the matching names in your list:");
                }
                System.out.println(index++ + ". " + i.getName());
            }
        }

        if (index == 1) {
            System.out.println("Sorry, there are no names matching your search");
        }
        return name;
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

}
