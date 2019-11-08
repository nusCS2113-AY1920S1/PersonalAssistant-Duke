package sgtravel.logic.commands;

import sgtravel.model.lists.EventList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class CommandTest {

    @Test
    void execute() {
        assertTrue(new HelpCommand() instanceof Command);
        assertTrue(new ListCommand() instanceof Command);
        assertTrue(new ExitCommand() instanceof Command);
        assertTrue(new EditorCommand() instanceof Command);
        assertTrue(new EditCommand(true, new EventList()) instanceof Command);
        assertTrue(new EditCommand(false, new EventList()) instanceof Command);
        assertTrue(new PromptCommand("hi") instanceof Command);
        assertTrue(new ViewScheduleCommand() instanceof Command);
    }
}
