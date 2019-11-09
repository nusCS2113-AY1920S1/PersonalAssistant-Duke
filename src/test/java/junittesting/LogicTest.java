package junittesting;

import javacake.Logic;
import javacake.exceptions.CakeException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LogicTest {

    private Logic logic = Logic.getInstance();

    @BeforeAll
    static void init() {


    }

    @AfterAll
    static void delete() {

    }

    @Test
    void testGoToParentFileMethod() throws CakeException {

        assertThrows(CakeException.class, () -> {
            logic.gotoParentFilePath("/");
        });

        assertThrows(CakeException.class, () -> {
            logic.gotoParentFilePath("///");
        });

        assertThrows(CakeException.class, () -> {
            logic.gotoParentFilePath("startingPath/");
        });

        String startingPath;
        String expectedOutput;
        String actualOutput;

        startingPath = "data/resources/hi";
        expectedOutput = "data/resources";
        actualOutput = logic.gotoParentFilePath(startingPath);
        assertEquals(expectedOutput, actualOutput);

        startingPath = "data/resources/hi.txt";
        expectedOutput = "data/resources";
        actualOutput = logic.gotoParentFilePath(startingPath);
        assertEquals(expectedOutput, actualOutput);

    }
}
