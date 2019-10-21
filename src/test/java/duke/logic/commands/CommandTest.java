package duke.logic.commands;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class CommandTest {

    @Test
    void execute() {
        assertTrue(new HelpCommand() instanceof Command);
        assertTrue(new ListCommand() instanceof Command);
        assertTrue(new ExitCommand() instanceof Command);
    }
}
