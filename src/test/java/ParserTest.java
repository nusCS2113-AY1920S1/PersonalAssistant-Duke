import interpreter.Parser;
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

    @Test
    void parseStoredTask() {
        String[] expectedResult = {"TODO", "yoda things/ tmrw at last", "false"};
        assertEquals(expectedResult[0], Parser.parseStoredTaskDetails("TODO yoda things/ tmrw at last####false\n")[0],
                "First Object");
        assertEquals(expectedResult[1], Parser.parseStoredTaskDetails("TODO yoda things/ tmrw at last####false\n")[1],
                "Second Object");
        assertEquals(expectedResult[2], Parser.parseStoredTaskDetails("TODO yoda things/ tmrw at last####false\n")[2],
                "Third Object");
    }
}
