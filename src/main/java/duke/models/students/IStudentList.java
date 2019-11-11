package duke.models.students;

import java.util.ArrayList;
//@@ danisheddie

public interface IStudentList {
    void addStudent(Student newStudent);

    void deleteStudent(int index);

    ArrayList<Student> getStudentList();

    Student getStudent(int i);

    int getStudentListSize();

}
