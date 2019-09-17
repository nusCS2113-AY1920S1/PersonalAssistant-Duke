package duke.Schedule;

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
    void Schedule() {
        try {
            assertTrue(test.remindMe(4) instanceof ArrayList);
        }
        catch (DukeException e){
        }
    }
}
