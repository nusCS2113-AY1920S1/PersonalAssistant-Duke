import duchess.logic.commands.ViewScheduleCommand;
import duchess.exceptions.DuchessException;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ViewScheduleCommandTest {
    @Test
    void viewScheduleCommand_emptySchedule_dukeExceptionThrown() {
        List<String> invalidSchedule = Arrays.asList("schedule", "/for", "16/9/");
        assertThrows(DuchessException.class, () -> new ViewScheduleCommand(invalidSchedule));
    }
}
