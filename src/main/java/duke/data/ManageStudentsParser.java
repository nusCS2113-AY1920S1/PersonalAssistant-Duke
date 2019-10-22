package duke.data;

import duke.sports.ManageStudents;
import duke.sports.MyStudent;

import java.util.Scanner;

public class ManageStudentParser implements IParser {
    String io;
    public ManageStudents students = new ManageStudents();
    @Override
    public void parseCommand(String input) {

        this.io = input;
        int index = 1;
        String[] word = io.split(" ");
        String cmd = word[0];
        switch(cmd) {
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
                index = Integer.parseInt(word[2]);
                students.deleteStudent(index);
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
                index = Integer.parseInt(word[2]);
                System.out.print("What do you want to edit for ");
                students.getStudentName(index);
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
                index = Integer.parseInt(word[2]);
                System.out.print("You have selected: ");
                students.getStudentName(index);
                break;

            case "particulars":
                // Edit particulars of the student
                break;

            case "progress":
                //Add student progress
                break;

            default:
                System.out.println("(Add statement here?)");


        }
    }
}
