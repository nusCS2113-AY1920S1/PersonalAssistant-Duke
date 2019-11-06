import javacake.Logic;
import javacake.exceptions.CakeException;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LogicTest {

    private Logic logic = Logic.getInstance();

    @BeforeAll
    static void init() {
        //File tempFile = File.createTempFile("tempFile", ".txt", "")


    }

    @AfterAll
    static void delete() {

    }

    @Test
    void testGoToParentFileMethod() throws CakeException {

        String startingPath;
        String expectedOutput;
        String actualOutput;

        assertThrows(CakeException.class, () -> {
            logic.gotoParentFilePath("/");
        });

        assertThrows(CakeException.class, () -> {
            logic.gotoParentFilePath("///");
        });

        assertThrows(CakeException.class, () -> {
            logic.gotoParentFilePath("startingPath/");
        });

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
