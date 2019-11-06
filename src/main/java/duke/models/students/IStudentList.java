package duke.models.students;

import java.util.ArrayList;

public interface IStudentList {
    void addStudent(Student newStudent);

    void deleteStudent(int index);

    ArrayList<Student> getStudentList();

    void getStudent(int i);

    int getStudentListSize();
}
