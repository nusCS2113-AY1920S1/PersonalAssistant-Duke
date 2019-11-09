import cube.logic.command.ProfitCommand;
import cube.logic.parser.ParserUtil;
import cube.logic.parser.exception.ParserException;
import junit.framework.*;

import java.util.Date;

import static org.junit.jupiter.api.AssertEquals.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

package cube.logic.command;

public class ProfitCommandTest extends ProfitCommand {
    /*public ProfitCommand(Date date_i, Date date_f, String param) {
        this.date_i = date_i;
        this.date_f = date_f;
        this.param = ProfitCommand.ProfitBy.valueOf(param);
    }*/
    public void testConstructor() throws ParserException {
        Date date_i = ParserUtil.parseStringToDate("23/03/1998");
        Date date_j = ParserUtil.parseStringToDate("23/03/2028");

        ProfitCommand profit1 = new ProfitCommand(date_i, date_j, "ALL");
        assertEquals("student name wrong", date_i, profit1.);
        assertTrue("student no. wrong", stu.getStuNumber().equals(student_no));

        // create some illegal inputs - Note 6
        try {
            Student s = new Student("Jimmy", null);
            fail("Constructor allows null student number");
        } catch (RuntimeException e) {}

        try {
            Student s = new Student(null, "980921C");
            fail("Constructor allows null student name");
        } catch (RuntimeException e) {}
    }

    // method to test the assigning and retrieval of grades
    public void testAssignAndRetrieveGrades() {
        // create a student
        Student stu = new Student("Jimmy", "946302B");

        // assign a few grades to this student
        stu.assignGrade("cs2102", 60);
        stu.assignGrade("cs2103", 70);
        stu.assignGrade("cs3214s", 80);

        // verify that the assignment is correct
        assertTrue("fail to assign cs2102 grade", stu.getGrade("cs2102") == 60);
        assertTrue("fail to assign cs2103 grade", stu.getGrade("cs2103") == 70);

        // attempt to retrieve a course that does not exist
        try {
            stu.getGrade("cs21002");
            fail("fail to catch non-existent course name");
        } catch (RuntimeException e) { }
    }

    // method create a test suite - Note 7
    public static Test suite() {
        return new TestSuite(StudentTest.class);
    }

    // the main method - Note 8
    public static void main(String args[]) {
        junit.textui.TestRunner.run(suite());
    }
}