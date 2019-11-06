package FindFreeTimesTest;

import Commands.Command;
import DukeExceptions.DukeInvalidFormatException;
import Parser.FindFreeTimesParse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

//@@author hwbjerry
/**
 * This class tests FindFreeTimesParse.
 */
public class FindFreeTimesParseTest {
    private final String invalidInput = "Invalid input. Please enter the command as follows. \n" +
            "Find 'x' hours , where 'x' is a digit between 1 - 16";
    private final String invalidDuration = "Invalid duration. Please enter the command as follows. \n" +
            "Find 'x' hours , where 'x' is a digit between 1 - 16";

    private static String validUserInputWithDuration;
    //TODO: ask if should take 0 and 17 instead of negative
    private static String userInputWithNegativeDuration;
    private static String userInputWithInvalidDuration;
    private static String userInputWithDurationInDecimal;

    private static String userInputWithoutPostFix;
    private static String userInputWithoutDuration;


    @BeforeAll
    public static void setAllVariables() {
        validUserInputWithDuration = "find/ft 5 hours";

        userInputWithNegativeDuration = "find/ft -100 hours";
        userInputWithInvalidDuration = "find/ft abc hours";
        userInputWithDurationInDecimal = "find/ft 5.6 hours";

        userInputWithoutPostFix = "find/ft 5";
        userInputWithoutDuration = "find/ft hours";
    }

    @Test
    public void findFreeTimesWithNegativeDuration() {
        String expected = invalidDuration;
        String actual = null;
        Command command = null;
        try {
            command = new FindFreeTimesParse(userInputWithNegativeDuration).parse();
        } catch (DukeInvalidFormatException e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }

    @Test
    public void findFreeTimesWithInvalidDuration() {
        String expected = invalidDuration;
        String actual = null;
        Command command = null;
        try {
            command = new FindFreeTimesParse(userInputWithInvalidDuration).parse();
        } catch (DukeInvalidFormatException e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }

    @Test
    public void findFreeTimesWithDurationInDecimal() {
        String expected = invalidDuration;
        String actual = null;
        Command command = null;
        try {
            command = new FindFreeTimesParse(userInputWithDurationInDecimal).parse();
        } catch (DukeInvalidFormatException e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }

    @Test
    public void findFreeTimesWithoutPostFix() {
        String expected = invalidInput;
        String actual = null;
        Command command = null;
        try {
            command = new FindFreeTimesParse(userInputWithoutPostFix).parse();
        } catch (DukeInvalidFormatException e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }

    @Test
    public void findFreeTimesWithoutDuration() {
        String expected = invalidInput;
        String actual = null;
        Command command = null;
        try {
            command = new FindFreeTimesParse(userInputWithoutDuration).parse();
        } catch (DukeInvalidFormatException e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }

    @Test
    public void findFreeTimesWithValidUserInput() {
        String actual = "No error";
        Command command = null;
        try {
            command = new FindFreeTimesParse(validUserInputWithDuration).parse();
        } catch (DukeInvalidFormatException e) {
            actual = e.getMessage();
        }
        assertNotNull(command, actual);
    }
}
