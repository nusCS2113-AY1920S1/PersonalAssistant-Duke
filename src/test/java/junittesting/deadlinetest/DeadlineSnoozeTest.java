package junittesting.deadlinetest;

import javacake.JavaCake;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeadlineSnoozeTest {
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
    public void free() {
        try {
            FileUtils.deleteDirectory(new File("testPath"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void snoozeTest1() {
        actualOut = javaCake.getResponse("deadline a /by 01/01/2019");
        actualOut = javaCake.getResponse("snooze 1 /by 02/01/2019");
        actualOut = javaCake.getResponse("reminder");
        expOut = "~~Upcoming Deadlines!~~\n"
                + "1.[✗] a\n(by: 02/01/2019)\n";
        assertEquals(expOut, actualOut);
    }

    @Test
    public void snoozeTest2() {
        actualOut = javaCake.getResponse("deadline a /by 01/01/2019 0001");
        actualOut = javaCake.getResponse("deadline b /by 01/01/2019");
        //this will sort the deadlines first
        actualOut = javaCake.getResponse("reminder");
        actualOut = javaCake.getResponse("snooze 1 /by 01/01/2019 0002");
        actualOut = javaCake.getResponse("reminder");
        expOut = "~~Upcoming Deadlines!~~\n"
                + "1.[✗] a\n(by: 01/01/2019 0001)\n"
                + "2.[✗] b\n(by: 01/01/2019 0002)\n";
        assertEquals(expOut, actualOut);
    }
}
