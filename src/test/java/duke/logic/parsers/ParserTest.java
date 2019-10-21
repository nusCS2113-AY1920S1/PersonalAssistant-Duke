package duke.logic.parsers;

import duke.logic.commands.AddCommand;
import duke.logic.commands.ExitCommand;
import duke.logic.commands.ListCommand;
import duke.logic.commands.MarkDoneCommand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ParserTest {

    @Test
    void parse() throws Exception {
        assertTrue(Parser.parseSingleCommand("bye") instanceof ExitCommand);
        assertTrue(Parser.parseSingleCommand("todo homework") instanceof AddCommand);
        assertTrue(Parser.parseSingleCommand("list") instanceof ListCommand);
        assertTrue(Parser.parseSingleCommand("done 1") instanceof MarkDoneCommand);
    }
}
