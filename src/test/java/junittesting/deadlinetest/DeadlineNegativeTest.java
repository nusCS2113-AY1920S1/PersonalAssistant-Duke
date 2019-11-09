package junittesting.deadlinetest;

import javacake.JavaCake;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeadlineNegativeTest {
    private JavaCake javaCake;

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
        String actualOut = javaCake.getResponse("deadline a /by 32/01/2019");
        String expOut = "[!] Date cannot be parsed: 32/01/2019";
        assertEquals(expOut, actualOut);
    }

    @Test
    public void test2() {
        String actualOut = javaCake.getResponse("deadline a/by 31/01/2019");
        String expOut = "[!] Improper format\n"
                + "Please input:\n"
                + "'deadline TASK /by TASK_DATE'";
        assertEquals(expOut, actualOut);
    }

    @Test
    public void test3() {
        String actualOut = javaCake.getResponse("deadline a /by 29/02/2019");
        String expOut = "[!] Date cannot be parsed: 29/02/2019";
        assertEquals(expOut, actualOut);
    }

    @Test
    public void test4() {
        String actualOut = javaCake.getResponse("deadline a /by31/01/2019");
        String expOut = "[!] Improper format\n"
                + "Please input:\n"
                + "'deadline TASK /by TASK_DATE'";
        assertEquals(expOut, actualOut);
    }

    @Test
    public void test5() {
        String actualOut = javaCake.getResponse("deadline a by 31/01/2019");
        String expOut = "[!] Improper format\n"
                + "Please input:\n"
                + "'deadline TASK /by TASK_DATE'";
        assertEquals(expOut, actualOut);
    }

}
