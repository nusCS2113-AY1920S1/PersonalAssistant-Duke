package moomoo.task;

import moomoo.command.BudgetCommand;
import moomoo.command.ExitCommand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParserTest {
    @Test
    public void testParser() {
        try {
            Ui newUi = new Ui();

            assertTrue(Parser.parse("bye", newUi) instanceof ExitCommand);
            assertTrue(Parser.parse("budget", newUi) instanceof BudgetCommand);
        } catch (MooMooException e) {
            assertEquals("moomoo.task.MooMooException: OOPS!!! I'm sorry, but I don't know what that means :-(",
                    e.toString());
        }
    }
}