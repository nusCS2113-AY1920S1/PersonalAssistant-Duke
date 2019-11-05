package duke.data;

import duke.models.students.Student;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class StudentStorage {
    private ArrayList<Student> saveText;

    public StudentStorage(ArrayList<Student> txt) {
        this.saveText = txt;
    }

    /**
     * Update the student list file.
     *
     * @param student The list of students to be changed
     */
    public void updateStudentList(final ArrayList<Student> student) {
        try {
            FileWriter fileWriter = new FileWriter(".\\src\\main\\java\\duke\\data\\studentList.txt");
            for (Student x : student) {
                fileWriter.write(x.getFormat() + "\n");
            }
            fileWriter.close();
        } catch (IOException io) {
            System.out.println("File not found: " + io.getMessage());
        }
    }

    /**
     * Load student list.
     * @param student list.
     */
    public void loadStudentListFile(final ArrayList<Student> student) {
        try {
            File file = new File(".\\src\\main\\java\\duke\\data\\studentList.txt");
            Scanner scanner = new Scanner(file);
            Student newStudent;
            while (scanner.hasNextLine()) {
                String loadInput = scanner.nextLine();
                if (loadInput.equals("")) {
                    break;
                }
                String[] splitter = loadInput.split(",");
                newStudent = new Student(splitter[0], splitter[1], splitter[2]);
                student.add(newStudent);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}