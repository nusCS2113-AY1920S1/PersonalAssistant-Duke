package duke.data;

import duke.models.students.Student;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

//@@ danisheddie
public class StudentStorage {
    private ArrayList<Student> saveText;
    private String userDir = System.getProperty("user.dir");
    private String filePath = userDir + "/studentList.txt";

    /**
     * Method does something.
     *
     * @param txt array of text
     */
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
            FileWriter fileWriter =
                    new FileWriter(filePath);
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
            FileReader file = new FileReader(filePath);
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