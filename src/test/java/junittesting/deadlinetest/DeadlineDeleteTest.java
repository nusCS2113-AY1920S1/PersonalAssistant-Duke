package junittesting.deadlinetest;

import javacake.JavaCake;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeadlineDeleteTest {
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
    public void deleteTest0() {
        actualOut = javaCake.getResponse("deadline a /by 01/01/2019");
        actualOut = javaCake.getResponse("delete 1");
        actualOut = javaCake.getResponse("reminder");
        expOut = "You have no deadlines as of now.\n";
        assertEquals(expOut, actualOut);
    }

    @Test
    public void deleteTest1() {
        actualOut = javaCake.getResponse("deadline a /by 01/01/2019");
        actualOut = javaCake.getResponse("deadline b /by 01/01/2019");
        actualOut = javaCake.getResponse("delete 1");
        actualOut = javaCake.getResponse("reminder");
        expOut = "~~Upcoming Deadlines!~~\n"
                + "1.[✗] b\n(by: 01/01/2019)\n";
        assertEquals(expOut, actualOut);
    }
}
