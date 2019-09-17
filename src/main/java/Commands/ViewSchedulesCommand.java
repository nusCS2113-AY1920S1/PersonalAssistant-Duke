package Commands;

import Interface.Storage;
import Interface.Ui;
import Tasks.TaskList;

/**
 * A method which allows the user to view tasks in form of schedule.
 */
public class ViewSchedulesCommand extends Command{

    /**
     * Executes the display of all the task as a schedule,
     * differentiated by type.
     * @param list The TaskList object to sort all the tasks and get the schedule
     * @param ui The Ui object to display the user schedule
     * @param storage The Storage object to access file to load or save the tasks
     * @return This returns the method in the Ui object which returns the string to display user's schedule
     */
    @Override
    public String execute(TaskList list, Ui ui, Storage storage){
        return ui.showUserSchedule(list.schedule());
    }
}
