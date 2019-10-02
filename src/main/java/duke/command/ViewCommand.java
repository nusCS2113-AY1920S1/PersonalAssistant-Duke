package duke.command;

import duke.core.Storage;
import duke.core.TaskList;
import duke.core.Ui;

public class ViewCommand extends Command {
    private String date;

    /**
     * Constructs and initialise a ViewCommand object.
     *
     * @param date  Refers to the selected date of the schedule
     */
    public ViewCommand (String date) {
        super();
        this.date = date;
    }



    /**
     * Indicates whether Duke should exist
     *
     * @return A boolean. True if the command tells Duke to exit, false
     * otherwise.
     */
    @Override
    public boolean isExit() {
        return false;
    }

    /**
     * run the command with the respect TaskList, UI, and storage.
     *
     * @param tasks   The task list where tasks are saved.
     * @param ui      The user interface.
     * @param storage object that handles local text file update
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showSchedules(tasks.fullTaskList(), date);
    }

}

