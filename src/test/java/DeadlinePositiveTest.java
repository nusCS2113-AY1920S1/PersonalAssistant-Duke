import javacake.JavaCake;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeadlinePositiveTest {
    JavaCake javaCake;

    /**
     * Initialise test files.
     */
    @BeforeEach
    public void init() {
        try {
            FileUtils.deleteDirectory(new File("testPath"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        javaCake = new JavaCake("testPath");
    }

    /**
     * Deletes test files.
     */
    @AfterEach
    public void delete() {
        try {
            FileUtils.deleteDirectory(new File("testPath"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void test1() {
        String actualOut = javaCake.getResponse("deadline a /by 02/01/2019");
        String expOut = "Got it. I've added this task:\n"
                + "[✗] a\n(by: 02/01/2019)\n"
                + "Now you have 1 tasks in the list.";
        assertEquals(expOut, actualOut);
        actualOut = javaCake.getResponse("reminder");
        expOut = "~~Upcoming Deadlines!~~\n"
                + "1.[✗] a\n(by: 02/01/2019)\n";
        assertEquals(expOut, actualOut);
    }

    @Test
    public void test2() {
        String actualOut = javaCake.getResponse("deadline a /by 01/02/2019");
        String expOut = "Got it. I've added this task:\n"
                + "[✗] a\n(by: 01/02/2019)\n"
                + "Now you have 1 tasks in the list.";
        assertEquals(expOut, actualOut);
        actualOut = javaCake.getResponse("reminder");
        expOut = "~~Upcoming Deadlines!~~\n"
                + "1.[✗] a\n(by: 01/02/2019)\n";
        assertEquals(expOut, actualOut);
    }

    @Test
    public void test3() {
        String actualOut = javaCake.getResponse("deadline a b /by 03-01-2019");
        String expOut = "Got it. I've added this task:\n"
                + "[✗] a b\n(by: 03-01-2019)\n"
                + "Now you have 1 tasks in the list.";
        assertEquals(expOut, actualOut);
        actualOut = javaCake.getResponse("reminder");
        expOut = "~~Upcoming Deadlines!~~\n"
                + "1.[✗] a b\n(by: 03-01-2019)\n";
        assertEquals(expOut, actualOut);
    }

    @Test
    public void reminderSortTest() {
        String actualOut = javaCake.getResponse("deadline a /by 02/01/2019");
        actualOut = javaCake.getResponse("deadline a /by 01/02/2019");
        actualOut = javaCake.getResponse("deadline a b /by 03-01-2019");
        actualOut = javaCake.getResponse("reminder");
        String expOut = "~~Upcoming Deadlines!~~\n"
                + "1.[✗] a\n(by: 02/01/2019)\n"
                + "2.[✗] a b\n(by: 03-01-2019)\n"
                + "3.[✗] a\n(by: 01/02/2019)\n";
        assertEquals(expOut, actualOut);
    }
}
