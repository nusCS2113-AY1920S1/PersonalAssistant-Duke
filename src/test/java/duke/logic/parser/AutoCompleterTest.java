package duke.logic.parser;

import duke.logic.command.Command;
import duke.logic.command.CommandResult;
import duke.logic.command.exceptions.CommandException;
import duke.logic.command.order.AddOrderCommand;
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
        Assertions.assertAll(() -> completer.addCommandClass(AddOrderCommandStub.class));
    }

    @Test
    public void addCommandClass_null_throwsNullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () -> completer.addCommandClass(null));
    }

    @Test
    public void addCommandClass_duplicate_throwsParseException() {
        completer.addCommandClass(AddOrderCommandStub.class);
        Assertions.assertThrows(ParseException.class, () -> completer.addCommandClass(AddOrderCommandStub.class));
    }

    //---------------- Tests for isAutoCompletable(Input) -------------------------------

    /**
     * Equivalence partitions for input:
     * - null
     * - not null and not completable
     * - not null and completable
     */

    @Test
    public void isAutoCompletable_null_throwsNullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () -> completer.isAutoCompletable(null));
    }

    @Test
    public void isAutoCompletable_notCompletable_returnsFalse() {
        Assertions.assertAll(() -> completer.addCommandClass(AddOrderCommandStub.class));

        //Unknown input
        Assertions.assertEquals(false,
            completer.isAutoCompletable(new AutoCompleter.Input("unknown", 0))
        );

        //Blank input
        Assertions.assertEquals(false,
            completer.isAutoCompletable(new AutoCompleter.Input("  ", 0))
        );

        //Empty input
        Assertions.assertEquals(false,
            completer.isAutoCompletable(new AutoCompleter.Input("", 0))
        );

        //input is the same as the command word
        Assertions.assertEquals(
            false,
            completer.isAutoCompletable(new AutoCompleter.Input(AddOrderCommand.COMMAND_WORD, 0))
        );
    }

    @Test
    public void isAutoCompletable_completable_returnsTrue() {
        Assertions.assertAll(() -> completer.addCommandClass(AddOrderCommandStub.class));

        //input contains the first character of command word
        Assertions.assertEquals(
            true,
            completer.isAutoCompletable(new AutoCompleter.Input(AddOrderCommand.COMMAND_WORD.substring(0, 1), 0))
        );

        //input contains the first two characters of command word
        Assertions.assertEquals(
            true,
            completer.isAutoCompletable(new AutoCompleter.Input(AddOrderCommand.COMMAND_WORD.substring(0, 2), 0))
        );
    }

    //---------------- Tests for complete() method ------------------------
    @Test
    public void complete_notCompletable_throwsParseException() {
        Assertions.assertThrows(ParseException.class, completer::complete);
    }

    @Test
    public void complete_completableCommandWord_success() {
        completer.addCommandClass(AddOrderCommandStub.class);
        Assertions.assertEquals(
            true,
            completer.isAutoCompletable(new AutoCompleter.Input("order a", 7))
        );

        Assertions.assertEquals(
            AddOrderCommandStub.AUTO_COMPLETE_INDICATOR,
            completer.complete().text
        );
    }

    @Test
    public void complete_completablePrefix_success() {
        completer.addCommandClass(AddOrderCommandStub.class);

        //input only has one prefix suggestion
        Assertions.assertEquals(
            true,
            completer.isAutoCompletable(new AutoCompleter.Input("order add -s", 12))
        );
        Assertions.assertEquals("order add -status", completer.complete().text);

        //input has multiple prefix suggestions.
        Assertions.assertEquals(
            true,
            completer.isAutoCompletable(new AutoCompleter.Input("order add -", 11))
        );

        Assertions.assertEquals("order add -contact", completer.complete().text);
        Assertions.assertEquals("order add -by", completer.complete().text);
        Assertions.assertEquals("order add -status", completer.complete().text);
        Assertions.assertEquals("order add -item", completer.complete().text);
        Assertions.assertEquals("order add -rmk", completer.complete().text);
        Assertions.assertEquals("order add -total", completer.complete().text);
        Assertions.assertEquals("order add -name", completer.complete().text);
        Assertions.assertEquals("order add -contact", completer.complete().text); //go cyclic to the first suggestion


    }

    /**
     * Clears the commands added after each run.
     */
    @AfterEach
    public void clean() {
        completer.clearCommandClasses();
    }


    //--------------Tests for Input constructor ----------------

    /*
     * EP for text:
     * - null string
     * - empty string
     * - non-empty string
     *
     * EP for caretPosition:
     * - negative numbers
     * - between 0 and text.length()
     * - larger than text.length()
     *
     * The following two test methods test all of the above with a reasonably low number of test cases
     * by applying the Heuristic principle.
     */

    @Test
    public void constructor_validInput_success() {
        //Non-empty text and caret position between 0 and text.length
        Assertions.assertAll(() -> new AutoCompleter.Input("order", 2));

        //empty text and caret position between 0 and text.length
        Assertions.assertAll(() -> new AutoCompleter.Input("", 0));

        //Boundary cases
        Assertions.assertAll(() -> new AutoCompleter.Input("order", 0));
        Assertions.assertAll(() -> new AutoCompleter.Input("order", 5));

    }

    @Test
    public void constructor_invalidInput_failure() {
        //Non-empty text and negative caret position, throws ParseException
        Assertions.assertThrows(ParseException.class, () -> new AutoCompleter.Input("test", -6));
        Assertions.assertThrows(ParseException.class, () -> new AutoCompleter.Input("test", -1)); //Boundary case

        //Non-empty text and caret position larger than text.length(), throws ParseException
        Assertions.assertThrows(ParseException.class, () -> new AutoCompleter.Input("abcd", 10));
        Assertions.assertThrows(ParseException.class, () -> new AutoCompleter.Input("abcd", 5)); //Boundary case

        //Null text, throws NullPointerException
        Assertions.assertThrows(NullPointerException.class, () -> new AutoCompleter.Input(null, 0));
    }


    /**
     * A stub for {@code AddOrderCommand}.
     *
     * The class is declared as public so that its fields can be fetched by AutoCompleter
     * using Reflection API.
     */
    public static class AddOrderCommandStub extends Command {
        public static final String COMMAND_WORD = "add";

        public static final String AUTO_COMPLETE_INDICATOR = "order add";
        public static final Prefix[] AUTO_COMPLETE_PARAMETERS = {
            new Prefix("-name"),
            new Prefix("-contact"),
            new Prefix("-by"),
            new Prefix("-status"),
            new Prefix("-item"),
            new Prefix("-rmk"),
            new Prefix("-total")
        };

        @Override
        public CommandResult execute(Model model) throws CommandException {
            throw new CommandException("This method should not be called in testing");
        }
    }


}
