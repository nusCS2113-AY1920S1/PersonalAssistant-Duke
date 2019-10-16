package duke.sports;

import java.util.ArrayList;

/**
 * This class is called when option 2 is chosen.
 * All commands related to students will be managed by this class
 */
public class ManageStudents {

    /**
     * An array list for the list of students.
     */
    private ArrayList<MyStudent> studentList;

//    public void loadStudentList() {
//        Storage storage = new Storage ();
//        studentList.txt.addAll(storage.loadFile());
//    }

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
    public MyStudent getStudentName(int index) {
        return getStudentList().get(index - 1);
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

    public void findName(String name) {
        int cnt = 1;
        for (MyStudent i : getStudentList()) {
            if (i.getName().contains(name)) {
                if (cnt == 1) {
                    System.out.println("Here are the matching names in your list:");
                }
                System.out.println(cnt++ + ". " + i.toString());
            }
        }

        if (cnt == 1) {
            System.out.println("Sorry, there are no tasks matching your search");
        }
    }
}
