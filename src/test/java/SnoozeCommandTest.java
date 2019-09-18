import duke.DukeContext;
import duke.command.SnoozeCommand;
import duke.command.Ui;
import duke.exception.DukeException;
import duke.exception.DukeFatalException;
import duke.task.DeadlineTask;
import duke.task.EventTask;
import duke.task.Storage;
import duke.task.TaskList;
import duke.task.TimedTask;
import duke.task.ToDoTask;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
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
        ctx.taskList = new TaskList();
        ToDoTask todo = new ToDoTask("JUnit tests");
        LocalDateTime t = LocalDateTime.parse("12/09/2019 1400", TimedTask.getDataFormatter());
        EventTask event = new EventTask("tutorial", t);
        DeadlineTask deadline = new DeadlineTask("submission", t);
        ctx.taskList.addTask(todo);
        ctx.taskList.addTask(event);
        ctx.taskList.addTask(deadline);
    }

    @Test
    public void snooze_validCommand_correctDateTimeSet() {
        SnoozeCommand uut = new SnoozeCommand();
        try {
            uut.parse("3 19/09/2019 1400");
            uut.execute(ctx);
        } catch (DukeException excp) {
            System.out.println(excp.getMessage());
            fail("Exception thrown on valid command!");
        }
    }
}
