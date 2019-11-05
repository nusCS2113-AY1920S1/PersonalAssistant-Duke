package RetrieveFreeTimesTest;



import Commands.Command;
import DukeExceptions.DukeInvalidFormatException;
import Parser.RetrieveFreeTimesParse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

//@@author hwbjerry
/**
 * This class tests RetrieveFreeTimesParse.
 */
public class RetrieveFreeTimesParseTest {
    private final String invalidInput = "Invalid input. Please enter the command as follows. \n" +
            "retrieve/ft 'x', where 'x' is a digit between 1 - 5";
    private final String invalidOption = "Invalid option. Please enter the command as follows. \n" +
            "retrieve/ft 'x', where 'x' is a digit between 1 - 5";

    private static String validUserInputWithOption;


    private static String userInputWithOptionZero;
    private static String userInputWithOptionSix;
    private static String userInputWithRandomStringOption;
    private static String userInputWithOptionInDecimal;

    private static String userInputWithoutOption;

    @BeforeAll
    public static void setAllVariables() {
        validUserInputWithOption = "retrieve/ft 5";

        userInputWithOptionZero = "retrieve/ft 0";
        userInputWithOptionSix = "retrieve/ft 6";
        userInputWithRandomStringOption = "retrieve/ft a0b1c2";
        userInputWithOptionInDecimal = "retrieve/ft 1.1";

        userInputWithoutOption = "retrieve/ft ";
    }

    @Test
    public void retrieveFreeTimesWithOptionZero() {
        String expected = invalidOption;
        String actual = null;
        Command command = null;
        try {
            command = new RetrieveFreeTimesParse(userInputWithOptionZero).parse();
        } catch (DukeInvalidFormatException e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }

    @Test
    public void retrieveFreeTimesWithOptionSix() {
        String expected = invalidOption;
        String actual = null;
        Command command = null;
        try {
            command = new RetrieveFreeTimesParse(userInputWithOptionSix).parse();
        } catch (DukeInvalidFormatException e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }

    @Test
    public void retrieveFreeTimesWithRandomStringOption() {
        String expected = invalidInput;
        String actual = null;
        Command command = null;
        try {
            command = new RetrieveFreeTimesParse(userInputWithRandomStringOption).parse();
        } catch (DukeInvalidFormatException e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }

    @Test
    public void retrieveFreeTimesWithOptionInDecimal() {
        String expected = invalidInput;
        String actual = null;
        Command command = null;
        try {
            command = new RetrieveFreeTimesParse(userInputWithOptionInDecimal).parse();
        } catch (DukeInvalidFormatException e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }

    @Test
    public void retrieveFreeTimesWithoutOption() {
        String expected = invalidInput;
        String actual = null;
        Command command = null;
        try {
            command = new RetrieveFreeTimesParse(userInputWithoutOption).parse();
        } catch (DukeInvalidFormatException e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }

    @Test
    public void retrieveFreeTimesValidUserInputWithOption() {
        String actual = "No error";
        Command command = null;
        try {
            command = new RetrieveFreeTimesParse(validUserInputWithOption).parse();
        } catch (DukeInvalidFormatException e) {
            actual = e.getMessage();
        }
        assertNotNull(command, actual);
    }
}
