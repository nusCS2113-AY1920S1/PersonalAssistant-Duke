package junittesting.deadlinetest;

import javacake.JavaCake;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeadlinePositiveTest {
    private JavaCake javaCake;
    private String actualOut = "";
    private String expOut = "";

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
        actualOut = javaCake.getResponse("deadline a /by 02/01/2019");
        expOut = "Got it. I've added this task:\n"
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
        actualOut = javaCake.getResponse("deadline a /by 01/02/2019");
        expOut = "Got it. I've added this task:\n"
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
        actualOut = javaCake.getResponse("deadline a b /by 03-01-2019");
        expOut = "Got it. I've added this task:\n"
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
        actualOut = javaCake.getResponse("deadline a /by 02/01/2019");
        actualOut = javaCake.getResponse("deadline a /by 01/02/2019");
        actualOut = javaCake.getResponse("deadline a b /by 03-01-2019");
        actualOut = javaCake.getResponse("reminder");
        expOut = "~~Upcoming Deadlines!~~\n"
                + "1.[✗] a\n(by: 02/01/2019)\n"
                + "2.[✗] a b\n(by: 03-01-2019)\n"
                + "3.[✗] a\n(by: 01/02/2019)\n";
        assertEquals(expOut, actualOut);
    }

    @Test
    public void testTime() {
        actualOut = javaCake.getResponse("deadline a /by 01/01/2019 23:59");
        actualOut = javaCake.getResponse("deadline a /by 01/01/2019");
        actualOut = javaCake.getResponse("deadline a /by 01/01/2019 0001");
        actualOut = javaCake.getResponse("reminder");
        expOut = "~~Upcoming Deadlines!~~\n"
                + "1.[✗] a\n(by: 01/01/2019)\n"
                + "2.[✗] a\n(by: 01/01/2019 0001)\n"
                + "3.[✗] a\n(by: 01/01/2019 23:59)\n";
        assertEquals(expOut, actualOut);
    }
}
