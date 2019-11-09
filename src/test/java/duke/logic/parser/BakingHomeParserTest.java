package duke.logic.parser;

import duke.logic.command.order.AddOrderCommand;
import duke.logic.parser.commons.BakingHomeParser;
import duke.logic.parser.exceptions.ParseException;
import duke.testutil.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static duke.commons.core.Message.MESSAGE_INVALID_COMMAND_FORMAT;
import static duke.commons.core.Message.MESSAGE_UNKNOWN_COMMAND;

public class BakingHomeParserTest {
    private BakingHomeParser parser = new BakingHomeParser();

    @Test
    public void parse_knownCommand_success() {
        Assertions.assertEquals(AddOrderCommand.class,
            parser.parseCommand("order add").getClass());

        // with trailing spaces
        Assertions.assertEquals(AddOrderCommand.class,
            parser.parseCommand("  order add   ").getClass());
    }

    @Test
    public void parse_invalidCommand_throwsParseException() {
        //Empty input
        Assert.assertThrows(ParseException.class, MESSAGE_INVALID_COMMAND_FORMAT, () -> {
            parser.parseCommand("");
        });

        //Blank input
        Assert.assertThrows(ParseException.class, MESSAGE_INVALID_COMMAND_FORMAT, () -> {
            parser.parseCommand("\n \t  \r");
        });

        //Unknown command
        Assert.assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> {
            parser.parseCommand("unknown");
        });
    }

}
