package duke.Sports;

import duke.Data.Storage;

import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

/**
 * This class is called when option 2 is chosen.
 * All commands related to students will be managed by this class
 */
public class ManageStudents {


    private ArrayList<MyStudent> studentList;

//    public void loadStudentList() {
//        Storage storage = new Storage (C:\Users\Dell\Desktop\main\src\main\java\duke\Data);
//        studentList.addAll(storage.loadFile());
//    }

    /**
     * Constructor of Manage Students to initialise ManageStudent class
     */
    public ManageStudents() {
        studentList = new ArrayList<MyStudent>();
    }



    public void setStudentList(ArrayList<MyStudent> studentList) {
        this.studentList = studentList;
    }

    /**
     *  To get retrieve the list of students in the storage
     */
    public ArrayList<MyStudent> getStudentList() {
        return studentList;
    }

    public void addStudentList (Storage storage) {
//        try {
//            setStudentList((Objects.requireNonNull(storage.loadFile())));
//        } catch (NullPointerException e) {
//            System.out.println("No previous list loaded");
//        }
    }

    public void listAllStudents() {
        System.out.println("Here are your list of students and their age: ");
        int index = 1;
        for (MyStudent i : getStudentList()) {
            System.out.println(index++ + ". " + i.toString());
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
     * @param name of the new student added
     */
    public void addStudent(MyStudent name) {
        studentList.add(name);
        System.out.println("Student have been added: \n" +
                getStudentList().get(getStudentListSize()-1).toString() + "\n" +
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


/**
 * This class is called when option 2 is chosen
 */
//public class ManageStudents {

//    public ManageStudents(String myCategory, String menu) {
//        super(myCategory, menu);
//    }

    public void manageStudentsCategory() {
        System.out.println("1. View all Students\n" +
                "2. Student Details\n" +
                "3. Class Details\n" +
                "4. Student Progress\n" +
                "5. Personal Best\n" +
                "6. Dietary Plan\n");
    }



}
