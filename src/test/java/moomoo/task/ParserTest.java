package moomoo.task;

import moomoo.command.ExitCommand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParserTest {
    @Test
    public void testParser() {
        try {
            assertTrue(Parser.parse("bye") instanceof ExitCommand);

            Parser.parse("invalid input");
        } catch (MooMooException e) {
            assertEquals("moomoo.task.MooMooException: OOPS!!! I'm sorry, but I don't know what that means :-(",
                    e.toString());
        }
    }
}