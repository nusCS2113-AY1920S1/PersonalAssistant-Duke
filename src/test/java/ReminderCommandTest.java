import spinbox.DateTime;
import spinbox.Storage;
import spinbox.lists.TaskList;
import spinbox.Ui;
import spinbox.commands.ReminderCommand;
import spinbox.exceptions.SpinBoxException;
import spinbox.items.tasks.Deadline;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import java.util.Date;

public class ReminderCommandTest {
//
//    @Test
//    public void reminderCommand_showReminders_ShouldShowPastRemindersWithOverdueTag() throws SpinBoxException {
//        TaskList testTaskList = new TaskList("CS1010");
//        long overdueDate = 656740800000L;
//
//        testTaskList.add(new Deadline("overdue", new DateTime(new Date(overdueDate))));
//
//        Ui testUi = new Ui();
//        Storage testStorage = new Storage("data/dukeTest.txt");
//        ReminderCommand command = new ReminderCommand();
//        assertEquals(command.execute(testTaskList, testStorage, testUi).contains("(Overdue)"), true);
//    }
//
//    @Test
//    public void reminderCommand_CompleteTaskAndShowReminders_ShouldNotShowDoneReminders() throws SpinBoxException {
//        TaskList testTaskList = new TaskList("CS1010");
//        long overdueDate = 656740800000L;
//
//        testTaskList.add(new Deadline("overdue", new DateTime(new Date(overdueDate))));
//        testTaskList.mark(0);
//
//        Ui testUi = new Ui();
//        Storage testStorage = new Storage("data/dukeTest.txt");
//        ReminderCommand command = new ReminderCommand();
//        assertNotEquals(command.execute(testTaskList, testStorage, testUi).contains("(Overdue)"),
//                true);
//    }
//
//    @Test
//    public void reminderCommand_showReminders_ShouldShowDistantFutureRemindersWithoutTag() throws SpinBoxException {
//        TaskList testTaskList = new TaskList("CS1010");
//        long distantFutureDate = 1893427200000L;
//
//        testTaskList.add(new Deadline("future", new DateTime(new Date(distantFutureDate))));
//
//        Ui testUi = new Ui();
//        Storage testStorage = new Storage("data/dukeTest.txt");
//        ReminderCommand command = new ReminderCommand();
//        assertNotEquals(command.execute(testTaskList, testStorage, testUi).contains("(Overdue)"),
//                true);
//    }
}
