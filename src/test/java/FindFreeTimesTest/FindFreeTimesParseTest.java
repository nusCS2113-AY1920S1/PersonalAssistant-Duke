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
    private static final String INVALID_INPUT = "Invalid input. Please enter the command as follows. \n"
            + "find/time <x> hours , where x is a digit between 1 - 16, inclusive";
    private static final String INVALID_DURATION = "Invalid duration. Please enter the command as follows. \n"
            + "find/time <x> hours , where x is a digit between 1 - 16, inclusive";
    private static final String INVALID_EMPTY_DURATION = "Invalid input."
            + "\nDuration cannot be blank.\nPlease enter the command as follows.\n"
            + "find/time <x> hours , where x is a digit between 1 - 16, inclusive";

    private static String validUserInputWithDuration;
    private static String userInputWithZeroDuration;
    private static String userInputWithSeventeenDuration;
    private static String userInputWithInvalidDuration;
    private static String userInputWithDurationInDecimal;

    private static String userInputWithoutPostFix;
    private static String userInputWithoutDuration;


    @BeforeAll
    public static void setAllVariables() {
        validUserInputWithDuration = "find/time 5 hours";

        userInputWithZeroDuration = "find/time 0 hours";
        userInputWithSeventeenDuration = "find/time 17 hours";
        userInputWithInvalidDuration = "find/time abc hours";
        userInputWithDurationInDecimal = "find/time 5.6 hours";

        userInputWithoutPostFix = "find/time 5";
        userInputWithoutDuration = "find/time hours";
    }

    @Test
    public void findFreeTimesWithZeroDuration() {
        String expected = INVALID_DURATION;
        String actual = null;
        Command command = null;
        try {
            command = new FindFreeTimesParse(userInputWithZeroDuration).parse();
        } catch (DukeInvalidFormatException e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }

    @Test
    public void findFreeTimesWithSeventeenDuration() {
        String expected = INVALID_DURATION;
        String actual = null;
        Command command = null;
        try {
            command = new FindFreeTimesParse(userInputWithSeventeenDuration).parse();
        } catch (DukeInvalidFormatException e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }

    @Test
    public void findFreeTimesWithInvalidDuration() {
        String expected = INVALID_DURATION;
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
        String expected = INVALID_DURATION;
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
        String expected = INVALID_INPUT;
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
        String expected = INVALID_EMPTY_DURATION;
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
