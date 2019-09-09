package duke.parsers;

import duke.commands.AddCommand;
import duke.commands.ExitCommand;
import duke.commands.ListCommand;
import duke.commands.MarkDoneCommand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ParserTest {

    @Test
    void parse() {
        try {
            assertTrue(Parser.parse("bye") instanceof ExitCommand);
            assertTrue(Parser.parse("todo homework") instanceof AddCommand);
            assertTrue(Parser.parse("deadline homework /by tomorrow") instanceof AddCommand);
            assertTrue(Parser.parse("event exam /at classroom") instanceof AddCommand);
            assertTrue(Parser.parse("list") instanceof ListCommand);
            assertTrue(Parser.parse("done 1") instanceof MarkDoneCommand);
        } catch (Exception e) {
            System.out.println("Fail");
        }
    }
}