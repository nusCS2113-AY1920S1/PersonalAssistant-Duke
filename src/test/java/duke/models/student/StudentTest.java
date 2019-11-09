package duke.models.student;

import duke.exceptions.DukeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StudentTest {

    private Email sampleEmail;
    private Major sampleMajor;
    private Name sampleName;
    private MatricNumber sampleMatricNumber;
    private Student sampleStudent;

    private StudentTest() throws DukeException {
        sampleEmail = new Email("sampleStudent@example.com");
        sampleMajor = new Major("computer science");
        sampleName = new Name("sponge bob");
        sampleMatricNumber = new MatricNumber("A0123456Z");
        sampleStudent = new Student(sampleName,sampleMatricNumber,sampleEmail,sampleMajor);
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Student(null,null,null,null));
        assertThrows(NullPointerException.class, () -> new Student(null,sampleMatricNumber,null,null));
        assertThrows(NullPointerException.class, () -> new Student(null,null,sampleEmail,sampleMajor));
    }

    @Test
    public void equals() throws DukeException {

        //check null
        assertFalse(sampleStudent.equals(null));

        Student otherStudent = new Student(sampleName, sampleMatricNumber, sampleEmail,
                new Major("computer engineering"));

        //check for equality with major changed
        assertFalse(sampleStudent.equals(otherStudent));

        otherStudent = new Student(new Name("change name"), sampleMatricNumber, sampleEmail, sampleMajor);
        //check for equality with name changed
        assertFalse(sampleStudent.equals(otherStudent));

        //check for equality with student id changed
        otherStudent = new Student(sampleName,new MatricNumber("A0123456B"), sampleEmail, sampleMajor);
        assertFalse(sampleStudent.equals(otherStudent));

        //check for equality with email changed
        otherStudent = new Student(sampleName, sampleMatricNumber, new Email("abc@d.com"), sampleMajor);
        assertFalse(sampleStudent.equals(otherStudent));

        //check for valid output
        otherStudent = new Student(sampleName,sampleMatricNumber,sampleEmail,sampleMajor);
        assertTrue(sampleStudent.equals(otherStudent));

    }
}
