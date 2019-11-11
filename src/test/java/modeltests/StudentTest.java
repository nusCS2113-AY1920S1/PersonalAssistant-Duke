package modeltests;

import duke.models.students.Student;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@@ danisheddie

class StudentTest {
    String name = "danish";
    String age = "22";
    String address = "woodlands";
    Student student = new Student(name, age, address);

    @Test
    void getIndexNumber() {
    }

    @Test
    void getName() {
        assertEquals(name,student.getName());
    }

    @Test
    void getAge() {
        assertEquals(age,student.getAge());
    }

    @Test
    void getAddress() {
        assertEquals(address,student.getAddress());
    }

    @Test
    void getDetails() {
        assertEquals("Name: danish\nAge: 22\nAddress: woodlands",student.getDetails());
    }

    @Test
    void testToString() {
        assertEquals("Name: danish\nAge: 22\nAddress: woodlands",student.toString());

    }

    @Test
    void getFormat() {
        assertEquals("danish,22,woodlands",student.getFormat());

    }

    @Test
    void addStudentProgress() {
    }

    @Test
    void getStudentProgress() {
    }
}