package duke.schedule;

import java.util.ArrayList;
import duke.commands.*;
import duke.tasks.Schedule;
import duke.exceptions.DukeException;
import duke.tasks.Task;
import duke.ui.Ui;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ScheduleTest {
    //  Ui ui;
    private Schedule test = new Schedule();

    @Test
    void schedule() {
        try {
            assertTrue(test.remindMe(4) instanceof ArrayList);
            assertTrue(test.confirm() instanceof Task);
        } catch (DukeException e) {
            System.out.println("schedule failure");
        }
    }
}
