package duke.logic.parser;

import duke.logic.command.Command;
import duke.logic.command.order.AddOrderCommand;
import duke.logic.parser.commons.BakingHomeParser;
import duke.logic.parser.exceptions.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static duke.commons.core.Message.MESSAGE_INVALID_COMMAND_FORMAT;
import static duke.commons.core.Message.MESSAGE_UNKNOWN_COMMAND;

public class BakingHomeParserTest {
    private BakingHomeParser parser = new BakingHomeParser();

    @Test
    public void parseCommand_addOrderCommand_success() {
        Command command = parser.parseCommand("order add");
        Assertions.assertEquals(AddOrderCommand.class, command.getClass());
    }

    @Test
    public void parseCommand_emptyInput_throwsParseException() {
        Throwable throwable = Assertions.assertThrows(ParseException.class, () -> {
            parser.parseCommand("");
        });
        Assertions.assertEquals(MESSAGE_INVALID_COMMAND_FORMAT, throwable.getMessage());
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        Throwable throwable = Assertions.assertThrows(ParseException.class, () -> {
            parser.parseCommand("unknown");
        });
        Assertions.assertEquals(MESSAGE_UNKNOWN_COMMAND, throwable.getMessage());
    }
}
