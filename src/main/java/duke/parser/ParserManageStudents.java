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
public final class ParserManageStudents implements IParser {
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
     * @param input command.
     */
    @Override
    public void parseCommand(final String input) {
        String[] word = input.split(" ");
        String cmd = word[0];
//        boolean runManageStudent = true;
        try {
//            while (runManageStudent) {
                new CliView().manageStudentsHeading();
                switch (cmd) {
                    case "list":
                        students.listAllStudents();
                        break;
                    case "add":
                        addCommand();
                        break;
                    case "delete":
                        students.deleteStudent(Integer.parseInt(word[1]));
                        break;
                    case "find":
                        final int limit = 4;
                        String name = cmd.substring(limit);
                        ArrayList<Student> search = new ArrayList<Student>();
                        for (Student i : students.getStudentList()) {
                            if (i.getName().contains(name)) {
                                search.add(i);
                            }
                        }
                        if (search.size() >= 1) {
                            System.out.println(
                                    "Here are the matching "
                                            + "names in your list:");
                            int index = 1;
                            for (int i = 0; i < search.size(); i++) {
                                System.out.println(index++ + ". "
                                        + search.get(i));
                            }
                        } else {
                            System.out.println("Sorry, there are"
                                    + " no names matching your search");
                        }
                        break;
                    case "details":
                        System.out.println("Details for: ");
                        if (sc.equals("add details")) {
                            System.out.println("Details for: ");

                        }
                        String studentName = sc.nextLine();
                        //students.findName(studentName);
                        //add student details
                        break;

                    case "edit":
                        System.out.print("What do you want to edit for ");
                        students.getStudent(Integer.parseInt(word[1]));
                        System.out.println("?");
                        // editStudentDetails(detail)
                        break;

                    case "view":
                        System.out.print("Viewing "
                                + students.getStudentName(
                                        Integer.parseInt(word[1]))
                                + " details:\n");
                        students.getStudent(Integer.parseInt(word[1]));
                        break;

                    case "progress":
                        studentProgressParser();
                        //Add student progress
                        break;

                    case "back":
//                        runManageStudent = false;
                        break;

                    default:
                        System.out.println("Please enter the correct command.");
                }
//            }
        } catch (InputMismatchException e) {
            new CliView().showCorrectCommand();
        }
        save.updateStudentList(students.getStudentList());
    }

    /**
     * Method to parse add command.
     */
    public void addCommand() {
        new CliView().addStudentFormat();
        String newStudent = sc.nextLine();
        String[] splitByComma = newStudent.split(",");
        String name = splitByComma[0];
        String age = splitByComma[1];
        String address = splitByComma[2];
        Student myNewStudent = new Student(
                name, age, address);
        students.addStudent(myNewStudent);
    }

    /**
     * Method to parse student progress commands.
     */
    public void studentProgressParser() {
        boolean runProgress = true;
        while (runProgress) {
            new CliView().studentProgressHeading();
            String input = sc.nextLine();
            String[] word = input.split(" ");
            String cmd = word[0];
            switch (cmd) {
                case "list":
                    students.listAllStudents();
                    break;
                case "add":
                    break;
                case "delete":
                    break;
                case "view":
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
