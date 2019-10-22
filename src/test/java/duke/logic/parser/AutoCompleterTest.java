package duke.logic.parser;

import duke.logic.command.order.OrderCommand;
import duke.logic.parser.commons.AutoCompleter;
import duke.logic.parser.exceptions.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AutoCompleterTest {
    private AutoCompleter completer = new AutoCompleter();

    @Test
    public void addCommand_orderCommand_success() {
        Assertions.assertAll(() -> {
            completer.addCommand(OrderCommand.class);
        });
    }

    @Test
    public void isCompletable_unknownInput_false() {
        addCommand_orderCommand_success();
        Assertions.assertEquals(
            false,
            completer.isAutoCompletable(new AutoCompleter.UserInputState("hello", 0))
        );
    }

    @Test
    public void isCompletable_orderCommandWord_true() {
        addCommand_orderCommand_success();
        Assertions.assertEquals(
            true,
            completer.isAutoCompletable(new AutoCompleter.UserInputState("or", 0))
        );
    }

    @Test
    public void complete_orderCommand_success() {
        addCommand_orderCommand_success();
        isCompletable_orderCommandWord_true();
        Assertions.assertEquals(
            OrderCommand.COMMAND_WORD,
            completer.complete().userInputString
            );
    }

    @Test
    public void complete_orderCommand_failure() {
        AutoCompleter emptyCompleter = new AutoCompleter();
        Assertions.assertThrows(ParseException.class, emptyCompleter::complete);
    }
}
