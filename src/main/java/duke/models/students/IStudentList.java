package duke.models.students;

import java.util.ArrayList;

public interface IStudentList {
    void addStudent(Student newStudent);

    void deleteStudent(int index);

    ArrayList<String> getAllStudent();

    Student getStudent(int i);

    int getStudentListSize();
}
