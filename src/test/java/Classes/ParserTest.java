import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParserTest {
    @Test
    public void TestParser() {
        try {
            assertTrue(Parser.parse("bye") instanceof ExitCommand);
            assertTrue(Parser.parse("list") instanceof ListTaskCommand);
            assertTrue(Parser.parse("todo asdasd") instanceof AddToDoCommand);
            assertTrue(Parser.parse("event to do work /at 02/12/2019 1830") instanceof AddEventCommand);
            assertTrue(Parser.parse("deadline to do stuff /by 12/12/2020 1830") instanceof AddDeadLineCommand);
            assertTrue(Parser.parse("done 1") instanceof MarkTaskAsDoneCommand);
            assertTrue(Parser.parse("delete 1") instanceof DeleteTaskCommand);
            assertTrue(Parser.parse("find hello") instanceof FindTaskCommand);

            Parser.parse("asdasd");
        } catch (DukeException e) {
            assertEquals("DukeException: OOPS!!! I'm sorry, but I don't know what that means :-(", e.toString());
        }
    }
}