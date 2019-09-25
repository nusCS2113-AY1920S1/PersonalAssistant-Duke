import duchess.model.Schedule;
import duchess.logic.commands.ViewScheduleCommand;
import duchess.logic.commands.exceptions.DukeException;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ViewScheduleCommandTest {

    @Test
    void getStartString_returnsCorrectly() throws ParseException {
        String dateString = "12/12/2019 1400";
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HHmm");
        Date time = formatter.parse(dateString);
        String toString = "[D][✘] test";
        assertEquals("1400", new Schedule(time, toString).getStartString());
    }

    @Test
    void getTask_returnsCorrectly() throws ParseException {
        String dateString = "12/12/2019 1400";
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HHmm");
        Date time = formatter.parse(dateString);
        String toString = "[D][✘] test";
        assertEquals("[D][✘] test", new Schedule(time, toString).getTask());
    }

    @Test
    void viewScheduleCommand_emptySchedule_dukeExceptionThrown() {
        List<String> invalidSchedule = Arrays.asList("schedule", "/for", "16/9/");
        assertThrows(DukeException.class, () -> new ViewScheduleCommand(invalidSchedule));
    }
}
