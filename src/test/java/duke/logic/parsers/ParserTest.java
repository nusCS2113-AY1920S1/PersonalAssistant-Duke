package duke.logic.parsers;

import duke.logic.commands.ExitCommand;
import duke.logic.commands.ListCommand;
import duke.logic.commands.MarkDoneCommand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ParserTest {

    @Test
    void parse() throws Exception {
        assertTrue(Parser.parseComplexCommand("bye") instanceof ExitCommand);
        assertTrue(Parser.parseComplexCommand("list") instanceof ListCommand);
        assertTrue(Parser.parseComplexCommand("done 1") instanceof MarkDoneCommand);
    }
}
