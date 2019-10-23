package duke.Parser;

import duke.sports.ManageStudents;
import duke.sports.MyStudent;

import java.util.Scanner;

public class ParserManageStudents implements IParser {

    /**
     * Declaring the type.
     */
    private ManageStudents students = new ManageStudents();

    /**
     * To parse ManageStudents commands.
     * @param input command.
     */
    @Override
    public void parseCommand(final String input) {
        String[] word = input.split(" ");
        String cmd = word[0];
        switch (cmd) {
            case "add":
                System.out.println("Insert Name, Age, Address:\n");
                Scanner sc = new Scanner(System.in);
                String newStudent = sc.nextLine();
                String[] splitByComma = newStudent.split(",");
                String name = splitByComma[0];
                String age = splitByComma[1];
                String address = splitByComma[2];
                MyStudent myNewStudent = new MyStudent(
                        name, age, address);
                students.addStudent(myNewStudent);
                break;

            // Format: student delete [index]
            case "delete":
                students.deleteStudent(Integer.parseInt(word[1]));
                break;

            case "details":
                System.out.println("Details for: ");
                Scanner scan = new Scanner(System.in);
                if (scan.equals("add details")) {
                    System.out.println("Details for: ");

                }
                String studentName = scan.nextLine();
                students.findName(studentName);
                //add student details
                break;

            case "edit":
                System.out.print("What do you want to edit for ");
                students.getStudentName(Integer.parseInt(word[1]));
                System.out.println("?");
                // editStudentDetails(detail)
                break;

            case "list":
                students.listAllStudents();
                break;

            case "search":
                final int limit = 15;
                String searchName = input.substring(limit);
                students.findName(searchName);
                break;

            case "select":
                System.out.print("You have selected: ");
                students.getStudentName(Integer.parseInt(word[1]));
                break;

            case "particulars":
                // Edit particulars of the student
                break;

            case "progress":
                //Add student progress
                break;

            case "back":
                ParserCommand parserCommand = new ParserCommand();
                parserCommand.parseCommand(cmd);
                break;

            default:
                System.out.println("Incorrect Command.");
        }
//        Storage storage = new Storage();
//        storage.updateStudentList(students.getStudentList());
//        break;
    }
}
