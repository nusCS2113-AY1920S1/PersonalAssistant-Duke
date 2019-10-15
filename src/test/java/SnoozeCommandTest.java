import duke.command.SnoozeCommand;
import duke.exception.DukeException;
import duke.task.DeadlineTask;
import duke.task.EventTask;
import duke.task.TaskList;
import duke.task.TimedTask;
import duke.task.ToDoTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public class SnoozeCommandTest extends CommandTest {

    /**
     * Sets up task list so we have tasks to snooze before each test.
     */
    @BeforeEach
    public void setupTaskList() {
        core.taskList = new TaskList();
        ToDoTask todo = new ToDoTask("JUnit tests");
        LocalDateTime t = LocalDateTime.parse("12/09/2019 1400", TimedTask.getPatDatetime());
        EventTask event = new EventTask("tutorial", t, t);
        DeadlineTask deadline = new DeadlineTask("submission", t);
        core.taskList.addTask(todo);
        core.taskList.addTask(event);
        core.taskList.addTask(deadline);
    }

    //    @Test
    //    public void snooze_validCommand_correctDateTimeSet() {
    //        SnoozeCommand uut = new SnoozeCommand();
    //        String taskListStr = null;
    //        try {
    //            uut.parse("3 19/09/2019 1400");
    //            uut.execute(core);
    //            taskListStr = core.taskList.listTasks();
    //        } catch (DukeException excp) {
    //            System.out.println(excp.getMessage());
    //            fail("Exception thrown on valid command!");
    //        }
    //        assertEquals(taskListStr, System.lineSeparator() + "1.[T][N] JUnit tests"
    //                + System.lineSeparator() + "2.[E][N] tutorial (at: Thu, 12 Sep 2019 2:00 PM - "
    //                + "Thu, 12 Sep 2019 2:00 PM)"
    //                + System.lineSeparator() + "3.[D][N] submission (by: Thu, 19 Sep 2019 2:00 PM)");
    //    }

    @Test
    public void snooze_invalidTarget_exceptionThrown() {
        SnoozeCommand uut = new SnoozeCommand();
        try {
            uut.parse("4 19/09/2019 1400");
            assertThrows(DukeException.class, () -> {
                uut.execute(core);
            });
            uut.parse("1 19/09/2019 1400");
            assertThrows(DukeException.class, () -> {
                uut.execute(core);
            });
        } catch (AssertionError excp) {
            fail("Failed to catch command snoozing invalid targets!");
        } catch (DukeException excp) {
            fail("Failed to parse command with valid delimiter!");
        }
    }
}
