package duke.logic.parser;

import duke.logic.command.Command;
import duke.logic.command.CommandResult;
import duke.logic.command.exceptions.CommandException;
import duke.logic.parser.commons.AutoCompleter;
import duke.logic.parser.commons.Prefix;
import duke.logic.parser.exceptions.ParseException;
import duke.model.Model;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * A test class for AutoCompleter.
 */
public class AutoCompleterTest {
    private AutoCompleter completer = new AutoCompleter();

    @Test
    public void addCommand_commandStub_success() {
        Assertions.assertAll(() -> completer.addCommandClass(CommandStub.class));
    }

    @Test
    public void isCompletable_unknownInput_returnsFalse() {
        Assertions.assertAll(() -> completer.addCommandClass(CommandStub.class));

        Assertions.assertEquals(
            false,
            completer.isAutoCompletable(new AutoCompleter.UserInputState("hello", 0))
        );
    }

    @Test
    public void isCompletable_stubCommandWord_returnsTrue() {
        completer.addCommandClass(CommandStub.class);
        Assertions.assertEquals(
            true,
            completer.isAutoCompletable(new AutoCompleter.UserInputState("St", 0))
        );
    }

    @Test
    public void isAutoCompletable_blankInput_returnsFalse() {
        completer.addCommandClass(CommandStub.class);
        Assertions.assertEquals(
            false,
            completer.isAutoCompletable(new AutoCompleter.UserInputState("", 0))
        );
    }

    @Test
    public void complete_existingCommandWord_success() {
        completer.addCommandClass(CommandStub.class);
        Assertions.assertEquals(
            true,
            completer.isAutoCompletable(new AutoCompleter.UserInputState("St", 1))
        );

        Assertions.assertEquals(
            "Stub",
            completer.complete().userInputString
        );
    }

    /**
     * Tests an input that has only one available suggestion.
     */
    @Test
    public void complete_singleSuggestion_success() {
        completer.addCommandClass(CommandStub.class);
        Assertions.assertEquals(
            true,
            completer.isAutoCompletable(new AutoCompleter.UserInputState("Stub stub -b", 10))
        );

        Assertions.assertEquals("Stub stub -bb", completer.complete().userInputString);

    }

    /**
     * Tests an input that has more than one available suggestion.
     */
    @Test
    public void complete_multipleSuggestions_success() {
        completer.addCommandClass(CommandStub.class);
        Assertions.assertEquals(
            true,
            completer.isAutoCompletable(new AutoCompleter.UserInputState("Stub stub -", 11))
        );

        Assertions.assertEquals("Stub stub -bb", completer.complete().userInputString);
        Assertions.assertEquals("Stub stub -cc", completer.complete().userInputString);
        Assertions.assertEquals("Stub stub -aa", completer.complete().userInputString);

        //Cyclic
        Assertions.assertEquals("Stub stub -bb", completer.complete().userInputString);

    }

    @Test
    public void complete_notCompletableCommand_failure() {
        Assertions.assertThrows(ParseException.class, completer::complete);
    }

    /**
     * Clears the commands added after each run.
     */
    @AfterEach
    public void clean() {
        completer.clearCommandClasses();
    }


    //============ UserInputState class test =============
    /*
     * Equivalent partitions (EP) for the constructor method:
     * EP for userInput string:
     * - null string (invalid)
     * - not null string (valid)
     * EP for caretPosition:
     * - negative numbers (invalid, below range)
     * - within 0 and userInput.length() (valid)
     * - larger than userInput.length() (invalid, above range)
     *
     * Applying Heuristic, we get four test cases:
     *      userInput  |   caretPosition
     * 1.   not null   |   within 0 and userInput.length()
     * 2.   null       |   within 0 and userInput.length()
     * 3.   not null   |   negative number
     * 4.   not null   |   larger than userInput.length()
     *
     * Applying boundary value analysis, we have 4 more:
     * 5.   not null   |   0
     * 6.   not null   |   -1
     * 7.   length n   |   n
     * 8.   length n   |   n+1
     */

    @Test
    public void newState_validInputAndValidCaretPosition_success() {
        Assertions.assertAll(() -> new AutoCompleter.UserInputState("hello", 2));
    }

    @Test
    public void newState_nullInput_throwsNullPointerException() {
        Assertions.assertThrows(NullPointerException.class,
            () -> new AutoCompleter.UserInputState(null, 0));
    }

    @Test
    public void newState_negativeCaretPosition_throwsNullPointerException() {
        Assertions.assertThrows(ParseException.class,
            () -> new AutoCompleter.UserInputState("order", -10));
    }

    @Test
    public void newState_invalidCaretPosition_throwsNullPointerException() {
        Assertions.assertThrows(ParseException.class,
            () -> new AutoCompleter.UserInputState("order", 10));
    }

    @Test
    public void newState_zeroCaretPosition_success() {
        Assertions.assertAll(() -> new AutoCompleter.UserInputState("hello", 0));
    }

    @Test
    public void newState_minusOneCaretPosition_throwsParseException() {
        Assertions.assertThrows(ParseException.class,
            () -> new AutoCompleter.UserInputState("order", -1));
    }

    @Test
    public void newState_upperBoundaryCaretPosition_success() {
        Assertions.assertAll(() -> new AutoCompleter.UserInputState("hello", 5));

    }

    @Test
    public void newState_upperBoundaryCaretPosition_throwsParseException() {
        Assertions.assertThrows(ParseException.class,
            () -> new AutoCompleter.UserInputState("order", 6));
    }

    /**
     * A stub for {@code Command}.
     */
    public static class CommandStub extends Command {
        public static final String COMMAND_WORD = "Stub";
        public static final String AUTO_COMPLETE_INDICATOR = "Stub stub";
        public static final Prefix[] AUTO_COMPLETE_PARAMETERS = {
            new Prefix("-aa"),
            new Prefix("-bb"),
            new Prefix("-cc")
        };

        @Override
        public CommandResult execute(Model model) throws CommandException {
            return null;
        }
    }
}
