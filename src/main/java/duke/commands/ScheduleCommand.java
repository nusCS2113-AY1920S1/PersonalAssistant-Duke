package duke.commands;

import duke.storage.Storage;
import duke.tasks.Schedule;
import duke.tasks.Task;
import duke.tasks.TaskList;
import duke.ui.Ui;

import java.util.ArrayList;
import java.util.Date;


/**
 * ScheduleCommand is a public class that inherits from abstract class Command.
 * Allows user to query tasks that occur on the same day.
 * @author HashirZahir
 */

public class ScheduleCommand extends Command{
    private Date date;

    public ScheduleCommand(Date date) {
        this.date = date;
    }

    /**
     * Display tasks that occur on the same day.
     * @param tasks the TaskList object in which the tasks will be checked against
     * @param ui the ui object to display the user interface of an "add" command
     * @param storage the storage object that stores the list of tasks
     * @param schedule
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage, Schedule schedule) {
        ArrayList<Task> sameDayTasks = tasks.getSameDayTasks(date);
        ui.showList(sameDayTasks);
    }
}
