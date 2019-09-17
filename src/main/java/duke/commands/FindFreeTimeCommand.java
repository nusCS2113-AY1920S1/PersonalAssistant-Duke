package duke.commands;
import duke.tasks.Schedule;
import duke.tasks.Task;
import duke.tasks.TaskList;
import duke.ui.Ui;
import duke.storage.Storage;
import java.util.ArrayList;

/**
 * FindFreeTimeCommand is a public class that inherits from abstract class Command
 * @author Foo Chi Hen
 */

public class FindFreeTimeCommand extends Command{
    private int hour;
    /**
     * The object will execute the "findFreeTime" command
     * @param tasks the TaskList object in which the task(s) is supposed to be listed
     * @param ui the ui object to display the user interface of an "list" command
     * @param storage the storage object that stores the list of tasks
     * @param schedule the schedule object that stores task in order
     */
    public FindFreeTimeCommand(int hour) { this.hour = hour;}
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage, Schedule schedule) {
        schedule.findFreeTime(this.hour);
    }
}
