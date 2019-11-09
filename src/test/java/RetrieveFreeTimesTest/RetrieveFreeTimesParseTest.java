package RetrieveFreeTimesTest;



import Commands.Command;
import DukeExceptions.DukeInvalidFormatException;
import DukeExceptions.DukeNoValidDataException;
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
    private final String invalidOption = "Invalid option. Please enter the command as follows. \n" +
            "retrieve/time 'x', where 'x' is a digit between 1 - 5";
    private final String invalidEmptyOption = "Invalid input.\n" +
            "Option cannot be blank.\nPlease enter the command as follows.\n"
            + "retrieve/time 'x', where 'x' is a digit between 1 - 5";

    private static String validUserInputWithOption;


    private static String userInputWithOptionZero;
    private static String userInputWithOptionSix;
    private static String userInputWithRandomStringOption;
    private static String userInputWithOptionInDecimal;

    private static String userInputWithoutOption;

    @BeforeAll
    public static void setAllVariables() {
        validUserInputWithOption = "retrieve/time 5";

        userInputWithOptionZero = "retrieve/time 0";
        userInputWithOptionSix = "retrieve/time 6";
        userInputWithRandomStringOption = "retrieve/time a0b1c2";
        userInputWithOptionInDecimal = "retrieve/time 1.1";

        userInputWithoutOption = "retrieve/time ";
    }

    @Test
    public void retrieveFreeTimesWithOptionZero() {
        String expected = invalidOption;
        String actual = null;
        Command command = null;
        try {
            command = new RetrieveFreeTimesParse(userInputWithOptionZero).parse();
        } catch (DukeInvalidFormatException | DukeNoValidDataException e) {
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
        } catch (DukeInvalidFormatException | DukeNoValidDataException e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }

    @Test
    public void retrieveFreeTimesWithRandomStringOption() {
        String expected = invalidOption;
        String actual = null;
        Command command = null;
        try {
            command = new RetrieveFreeTimesParse(userInputWithRandomStringOption).parse();
        } catch (DukeInvalidFormatException | DukeNoValidDataException e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }

    @Test
    public void retrieveFreeTimesWithOptionInDecimal() {
        String expected = invalidOption;
        String actual = null;
        Command command = null;
        try {
            command = new RetrieveFreeTimesParse(userInputWithOptionInDecimal).parse();
        } catch (DukeInvalidFormatException | DukeNoValidDataException e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }

    @Test
    public void retrieveFreeTimesWithoutOption() {
        String expected = invalidEmptyOption;
        String actual = null;
        Command command = null;
        try {
            command = new RetrieveFreeTimesParse(userInputWithoutOption).parse();
        } catch (DukeInvalidFormatException | DukeNoValidDataException e) {
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
        } catch (DukeInvalidFormatException | DukeNoValidDataException e) {
            actual = e.getMessage();
        }
        assertNotNull(command, actual);
    }
}
