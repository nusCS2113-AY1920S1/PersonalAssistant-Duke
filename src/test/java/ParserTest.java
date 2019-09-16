import duke.worker.Parser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ParserTest {

    @Test
    void removeStr() {
        assertEquals("Something", Parser.removeStr("TODO", "TodoSomething"),
                "Should remove 'Todo'");
        assertEquals("event /by tmrw", Parser.removeStr("DEADLINE", "deadline event /by tmrw"),
                "Should remove 'deadline'");
        assertEquals("something whitespace", Parser.removeStr("EVENT", " Event something whitespace "),
                "Should remove 'Event");
    }
}
