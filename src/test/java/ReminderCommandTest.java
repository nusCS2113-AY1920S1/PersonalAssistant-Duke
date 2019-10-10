import duke.DateTime;
import duke.Storage;
import duke.lists.TaskList;
import duke.Ui;
import duke.commands.ReminderCommand;
import duke.exceptions.DukeException;
import duke.items.tasks.Deadline;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import java.util.Date;

public class ReminderCommandTest {

    @Test
    public void reminderCommand_showReminders_ShouldShowPastRemindersWithOverdueTag() throws DukeException {
        TaskList testTaskList = new TaskList();
        long overdueDate = 656740800000L;

        testTaskList.add(new Deadline("overdue", new DateTime(new Date(overdueDate))));

        Ui testUi = new Ui();
        Storage testStorage = new Storage("data/dukeTest.txt");
        ReminderCommand command = new ReminderCommand();
        assertEquals(command.execute(testTaskList, testStorage, testUi).contains("(Overdue)"), true);
    }

    @Test
    public void reminderCommand_CompleteTaskAndShowReminders_ShouldNotShowDoneReminders() throws DukeException {
        TaskList testTaskList = new TaskList();
        long overdueDate = 656740800000L;

        testTaskList.add(new Deadline("overdue", new DateTime(new Date(overdueDate))));
        testTaskList.mark(0);

        Ui testUi = new Ui();
        Storage testStorage = new Storage("data/dukeTest.txt");
        ReminderCommand command = new ReminderCommand();
        assertNotEquals(command.execute(testTaskList, testStorage, testUi).contains("(Overdue)"),
                true);
    }

    @Test
    public void reminderCommand_showReminders_ShouldShowDistantFutureRemindersWithoutTag() throws DukeException {
        TaskList testTaskList = new TaskList();
        long distantFutureDate = 1893427200000L;

        testTaskList.add(new Deadline("future", new DateTime(new Date(distantFutureDate))));

        Ui testUi = new Ui();
        Storage testStorage = new Storage("data/dukeTest.txt");
        ReminderCommand command = new ReminderCommand();
        assertNotEquals(command.execute(testTaskList, testStorage, testUi).contains("(Overdue)"),
                true);
    }
}
