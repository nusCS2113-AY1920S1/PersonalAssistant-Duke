package duke.schedule;

import java.util.ArrayList;

import duke.tasks.Schedule;
import duke.exceptions.DukeException;
import duke.tasks.Food;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ScheduleTest {
    //  Ui ui;
    private Schedule test = new Schedule();

    @Test
    void schedule() {
        try {
            assertTrue(test.remindMe(4) instanceof ArrayList);
            assertTrue(test.confirm() instanceof Food);
        } catch (DukeException e) {
            System.out.println("schedule failure");
        }
    }
}
