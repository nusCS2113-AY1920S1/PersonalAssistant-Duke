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

    //---------------- Tests for addCommandClass -------------------------------
    @Test
    public void addCommandClass_commandClass_success() {
        Assertions.assertAll(() -> completer.addCommandClass(CommandStub.class));
    }

    @Test
    public void addCommandClass_null_throwsNullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () -> completer.addCommandClass(null));
    }

    //---------------- Tests for isAutoCompletable -------------------------------

    /**
     * Equivalence partitions for input:
     * - null
     * - not null, cannot find a match
     * - not null, matched
     * - blank
     * - empty
     */

    @Test
    public void isAutoCompletable_null_throwsNullPointerException() {
        Assertions.assertAll(() -> completer.addCommandClass(CommandStub.class));

        Assertions.assertThrows(NullPointerException.class,
            () -> completer.isAutoCompletable(null));
    }

    @Test
    public void isAutoCompletable_noMatchInput_returnsFalse() {
        Assertions.assertAll(() -> completer.addCommandClass(CommandStub.class));

        Assertions.assertEquals(
            false,
            completer.isAutoCompletable(new AutoCompleter.Input("unknown", 0))
        );
    }

    @Test
    public void isAutoCompletable_matchedInput_returnsTrue() {
        completer.addCommandClass(CommandStub.class);

        Assertions.assertEquals(
            true,
            completer.isAutoCompletable(new AutoCompleter.Input("St", 0))
        );
    }

    @Test
    public void isAutoCompletable_blankInput_returnsFalse() {
        completer.addCommandClass(CommandStub.class);

        Assertions.assertEquals(
            false,
            completer.isAutoCompletable(new AutoCompleter.Input("  ", 0))
        );
    }

    @Test
    public void isAutoCompletable_emptyInput_returnsFalse() {
        completer.addCommandClass(CommandStub.class);

        Assertions.assertEquals(
            false,
            completer.isAutoCompletable(new AutoCompleter.Input("", 0))
        );
    }

    //---------------- Tests for complete ------------------------

    @Test
    public void complete_matchedCommandWord_success() {
        completer.addCommandClass(CommandStub.class);
        Assertions.assertEquals(
            true,
            completer.isAutoCompletable(new AutoCompleter.Input("St", 1))
        );

        Assertions.assertEquals(
            "Stub",
            completer.complete().text
        );
    }

    @Test
    public void complete_noMatchedCommand_throwsParseException() {
        Assertions.assertThrows(ParseException.class, completer::complete);
    }

    /**
     * Tests an input that has only one available suggestion.
     */
    @Test
    public void complete_singleSuggestion_success() {
        completer.addCommandClass(CommandStub.class);

        Assertions.assertEquals(
            true,
            completer.isAutoCompletable(new AutoCompleter.Input("Stub stub -b", 10))
        );

        Assertions.assertEquals("Stub stub -bb", completer.complete().text);

    }

    /**
     * Tests an input that has more than one available suggestion.
     */
    @Test
    public void complete_multipleSuggestions_success() {
        completer.addCommandClass(CommandStub.class);

        Assertions.assertEquals(
            true,
            completer.isAutoCompletable(new AutoCompleter.Input("Stub stub -", 11))
        );

        Assertions.assertEquals("Stub stub -bb", completer.complete().text);
        Assertions.assertEquals("Stub stub -cc", completer.complete().text);
        Assertions.assertEquals("Stub stub -aa", completer.complete().text);

        //Should go cyclic to the first suggestion
        Assertions.assertEquals("Stub stub -bb", completer.complete().text);

    }

    /**
     * Clears the commands added after each run.
     */
    @AfterEach
    public void clean() {
        completer.clearCommandClasses();
    }


    //--------------Tests for  UserInputState constructor ----------------

    /*
     * EP for userInput string:
     * - null string (invalid)
     * - non-null string (valid)
     *
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
     * Applying boundary value analysis (BVA), we have 6 more:
     * 5.   not null   |   -1
     * 6.   not null   |   0
     * 7.   not null   |   1
     * 8    length n   |   n-1
     * 9.   length n   |   n
     * 10.   length n   |   n+1
     */

    @Test
    public void constructor_validInputAndValidCaretPosition_success() {
        Assertions.assertAll(() -> new AutoCompleter.Input("hello", 2));
    }

    @Test
    public void constructor_nullInput_throwsNullPointerException() {
        Assertions.assertThrows(NullPointerException.class,
            () -> new AutoCompleter.Input(null, 0));
    }

    @Test
    public void constructor_negativeCaretPosition_throwsNullPointerException() {
        Assertions.assertThrows(ParseException.class,
            () -> new AutoCompleter.Input("order", -10));
    }

    @Test
    public void constructor_invalidCaretPosition_throwsNullPointerException() {
        Assertions.assertThrows(ParseException.class,
            () -> new AutoCompleter.Input("order", 10));
    }

    @Test
    public void constructor_validBoundaryCaretPositions_success() {
        //Boundaries near zero (BVA cases 6, 7)
        Assertions.assertAll(() -> new AutoCompleter.Input("hello", 1));
        Assertions.assertAll(() -> new AutoCompleter.Input("hello", 0));

        //Boundaries near string.length(BVA cases 8, 9)
        Assertions.assertAll(() -> new AutoCompleter.Input("hello", 4));
        Assertions.assertAll(() -> new AutoCompleter.Input("hello", 5));

    }

    @Test
    public void constructor_inValidBoundaryCaretPositions_throwsParseException() {
        //negative position (BVA case 5)
        Assertions.assertThrows(ParseException.class,
            () -> new AutoCompleter.Input("order", -1));

        //More than string.length() (BVA case 10)
        Assertions.assertThrows(ParseException.class,
            () -> new AutoCompleter.Input("order", 6));

    }

    /**
     * A stub for {@code Command}.
     *
     * The class is declared as public so that its fields can be fetched by AutoCompleter
     * using Reflection API.
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
            throw new CommandException("This method should not be called in testing");
        }
    }
}
