package duke.parser;

import duke.data.StudentStorage;
import duke.models.students.StudentList;
import duke.models.students.Student;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import duke.view.CliView;

/**
 * This is the parser for manage students.
 * @author danisheddie
 */
public final class ParserManageStudents {
    /**
     * Boolean status to check if the class can exit.
     */
    private boolean isRunning = true;
    /**
     * Declaring Manage Students Object.
     */
    private StudentList students;
    /**
     * The scanner object to take input.
     */
    private Scanner sc;
    /**
     * Storage object.
     */
    private StudentStorage save;

    private boolean runManageStudent = true;

    /**
     * Constructor for Manage Students Parser.
     */
    ParserManageStudents() {
        students = new StudentList();
        sc = new Scanner(System.in);
        save = new StudentStorage(students.getStudentList());
        StudentStorage read = new StudentStorage(students.getStudentList());
        read.loadStudentListFile(students.getStudentList());
    }

    /**
     * To parse ManageStudents commands.
     *
     */
    public void parseCommand() {
        final int list = 1;
        final int add = 2;
        final int delete = 3;
        final int progress = 4;
        final int view = 5;
        final int back = 6;
        int cmd;
        boolean runManageStudent = true;
        try {
            while (runManageStudent) {
                new CliView().manageStudentsHeading();
                cmd = sc.nextInt();
                switch (cmd) {
                case list:
                    System.out.println("Here are your list of students: ");
                    students.listAllStudents();
                    break;
                case add:
                    System.out.println("Name: ");
                    String name = sc.next();
                    System.out.println("Age: ");
                    String age = sc.next();
                    System.out.println("Address: ");
                    String address = sc.next();
                    Student myNewStudent = new Student(
                            name, age, address);
                    students.addStudent(myNewStudent);
                    break;
                case delete:
                    System.out.println("Which student do you want to remove?");
                    students.listAllStudents();
                    students.deleteStudent(sc.nextInt());
                    //students.deleteStudent(Integer.parseInt(word[1]));
                    break;
                case progress:
                    studentProgressParser();
                    break;
                case view:
                    System.out.println("Which student details do you want to view?");
                    students.listAllStudents();
                    int index = sc.nextInt();
                    System.out.print("Viewing "
                            + students.getStudentName(
                                    index)
                            + " details:\n");
                    System.out.println(
                            students.getStudent(index).getDetails());
                    break;
                case back:
                    runManageStudent = false;
                    break;

                default:
                    System.out.println("Please enter the correct command.");
                }
            }
        } catch (InputMismatchException e) {
            new CliView().showCorrectCommand();
        }
        save.updateStudentList(students.getStudentList());
    }

    /**
     * Method to parse student progress commands.
     */
    public void studentProgressParser() {
        boolean runProgress = true;

        while (runProgress) {
            new CliView().studentProgressHeading();
            String cmd = sc.nextLine();
            //String[] word = input.split(" ");
            //String cmd = word[0];
            switch (sc.nextLine()) {
            case "list":
                students.listAllStudents();
                break;
            case "add":
                System.out.print("Who do you want to add progress for?\n");
                students.listAllStudents();
                String input = sc.nextLine();
                String[] word = input.split("-");
                students.getStudent(Integer.parseInt(word[0])).addStudentProgress(word[1]);
                break;
            case "delete":
                break;
            case "view":
                System.out.print("Whose progress do you want to see?\n");
                students.listAllStudents();
                //int index = sc.nextInt();
                System.out.println("Progress report:\n"
                        + students.getStudent(sc.nextInt()).getStudentProgress());
                break;
            case "back":
                runProgress = false;
                break;
            default:
                System.out.println("Please enter the "
                        + "correct command format.");
            }
        }
    }
}
