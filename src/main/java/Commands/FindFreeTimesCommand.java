package Commands;

import Interface.Storage;
import Interface.Ui;
import Tasks.TaskList;

import java.util.Date;

public class FindFreeTimesCommand extends Command {
    protected String duration;

    /**
     * Creates a FindFreeTimesCommand object.
     * @param duration The duration that the user wants to use to find in the TaskList object
     */
    public FindFreeTimesCommand(String duration) {
        this.duration = duration;
    }

    /**
     * Executes the finding of available block period inside the TaskList object with the given duration.
     * @param list The TaskList object used to find free time with the given duration
     * @param ui The Ui object to display the find free time message
     * @param storage The Storage object to access file to load or save the tasks
     * @return This returns the method in the Ui object which returns the string to display find free time message
     */
    @Override
    public String execute(TaskList list, Ui ui, Storage storage) throws Exception {
        return ui.showFreeTimes(list.findFreeTimes(duration));
    }
}
