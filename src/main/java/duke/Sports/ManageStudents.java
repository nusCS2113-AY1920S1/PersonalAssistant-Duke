package duke.Sports;

import duke.Data.Storage;

import java.util.ArrayList;
import java.util.Objects;

/**
 * This class is called when option 2 is chosen.
 * All commands related to students will be managed by this class
 */
public class ManageStudents {

    private ArrayList<MyStudent> studentList = new ArrayList<>();


    public void setStudentList(ArrayList<MyStudent> studentList) {
        this.studentList = studentList;
    }

    /**
     *  To get retrieve the list of students in the storage
     */
    public ArrayList<MyStudent> getStudentList() {
        return this.studentList;
    }

    public void addStudentList (Storage storage) {
//        try {
//            setStudentList((Objects.requireNonNull(storage.loadFile())));
//        } catch (NullPointerException e) {
//            System.out.println("No previous list loaded");
//        }
    }

    public void allStudents() {
        int index = 1;
        for (MyStudent i : getStudentList()) {
            System.out.println(index++ + ". " + i.getName());
        }
    }

    /**
     * This method retrieve the total number of student in the list
     * @return Number of student in the list.
     */
    public int getStudentListSize() {
        return studentList.size();
    }

    /**
     * Adding a new student and listing the total number of students
     * @param index of the new student added
     */
    public void addStudent(MyStudent student) {
        getStudentList().add(student);
        System.out.println("Student have been added: \n" +
                getStudentList().get(getStudentListSize()).toString() + "\n" +
                "Now you have " + getStudentListSize() + " students.");
    }


//
//    public void manageStudentsCategory() {
//        System.out.println("MANAGE STUDENTS:\n" +
//                "1. View all Students\n" +
//                "2. Student Details\n" +
//                "3. Class Details\n" +
//                "4. Student Progress\n" +
//                "5. Personal Best\n" +
//                "6. Dietary Plan\n");
//    }



}
