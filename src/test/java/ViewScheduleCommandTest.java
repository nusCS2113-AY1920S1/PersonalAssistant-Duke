import duke.command.ViewScheduleCommand;
import duke.dukeexception.DukeException;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ViewScheduleCommandTest {
    @Test
    void viewScheduleCommand_invalidDateTime_dukeExceptionThrown() {
        List<String> invalidSchedule = Arrays.asList("schedule", "/from", "16/9/2019");
        assertThrows(DukeException.class, () -> new ViewScheduleCommand(invalidSchedule));
    }
}
