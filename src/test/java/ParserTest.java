import executor.command.Command;
import executor.command.CommandType;
import interpreter.Parser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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

    @Test
    void parseForPrimaryInputTest() {
        assertEquals("5", Parser.parseForPrimaryInput(CommandType.TODO,
                "todo5"));
        assertEquals("holiday", Parser.parseForPrimaryInput(CommandType.RECUR,
                "Recur holiday /repeat always"));
        assertEquals("Death as a respite from CS2113T", Parser.parseForPrimaryInput(CommandType.EVENT,
                "EventDeath as a respite from CS2113T /when anytime /where anywhere"));
    }

    @Test
    void parseForFlagsTest() {
        assertNull(Parser.parseForFlags("when",
                "Delete2"));
        assertEquals("23", Parser.parseForFlags("times",
                "Recur this /times 23"));
        assertEquals("somewhere somewhen", Parser.parseForFlags("details",
                "Event there /details somewhere somewhen"));
        assertEquals("Over the moon", Parser.parseForFlags("feeling",
                "Deadline cry /feeling Over the moon /due 2359, tonight"));
        assertEquals("bring presents", Parser.parseForFlags("todo",
                "Event Birthday Party /when 23-09-2019 /at Mark Zuckerberg's /todo bring presents /dress-code blue"));
    }
}
