package duke.logic.parsers;

import duke.commands.AddCommand;
import duke.commands.ExitCommand;
import duke.commands.ListCommand;
import duke.commands.MarkDoneCommand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ParserTest {

    @Test
    void parse() throws Exception {
        assertTrue(Parser.parseSingleCommand("bye") instanceof ExitCommand);
        assertTrue(Parser.parseSingleCommand("todo homework") instanceof AddCommand);
        assertTrue(Parser.parseSingleCommand("deadline homework by tomorrow") instanceof AddCommand);
        assertTrue(Parser.parseSingleCommand("within jogging between 1200 and 1300") instanceof AddCommand);
        assertTrue(Parser.parseSingleCommand("list") instanceof ListCommand);
        assertTrue(Parser.parseSingleCommand("done 1") instanceof MarkDoneCommand);
    }
}
