import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests the Parser class by feeding it an input String and comparing it against the
 * output we desire, which should be an ArrayList of Strings to be fed to the Ai.
 */

public class ParserTest {
    protected ArrayList<String> testOutput = new ArrayList<String>();
    protected Parser parser;

    /**
     * Constructor of this test. Initializes the Parser.
     */
    public ParserTest() {
        parser = new Parser();
    }

    /**
     * Tests the Parser by feeding it a String input to create a Deadline, and comparing it
     * against the ArrayList of String commands that we should obtain.
     */
    @Test
    public void testDeadline() {
        try {
            testOutput.add("deadline");
            testOutput.add("cry");
            testOutput.add("23/01/2019 1800");
            assertEquals(testOutput, parser.parseInput("deadline cry /by 23/01/2019 1800"));
        }
        catch (DukeException e) {
            e.showError();
        }
    }

    /**
     * Tests the Parser by feeding it a String input to create an Event, and comparing it
     * against the ArrayList of String commands that we should obtain.
     */
    @Test
    public void testEvent() {
        try {
            testOutput.add("event");
            testOutput.add("cry");
            testOutput.add("23/01/2019 1800");
            assertEquals(testOutput, parser.parseInput("event cry /at 23/01/2019 1800"));
        }
        catch (DukeException e) {
            e.showError();
        }
    }

    /**
     * Tests the Parser by feeding it a String input to create a Todo, and comparing it
     * against the ArrayList of String commands that we should obtain.
     */
    @Test
    public void testTodo() {
        try {
            testOutput.add("todo");
            testOutput.add("cry");
            assertEquals(testOutput, parser.parseInput("todo cry"));
        }
        catch (DukeException e) {
            e.showError();
        }
    }
}