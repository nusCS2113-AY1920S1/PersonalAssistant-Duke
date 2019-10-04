import duchess.logic.commands.ViewScheduleCommand;
import duchess.exceptions.DukeException;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ViewScheduleCommandTest {
    @Test
    void viewScheduleCommand_emptySchedule_dukeExceptionThrown() {
        List<String> invalidSchedule = Arrays.asList("schedule", "/for", "16/9/");
        assertThrows(DukeException.class, () -> new ViewScheduleCommand(invalidSchedule));
    }
}
