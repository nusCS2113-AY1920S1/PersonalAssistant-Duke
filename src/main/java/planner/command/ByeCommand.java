package planner.command;

import planner.util.Reminder;
import planner.util.TaskList;
import planner.util.Ui;
import planner.util.Storage;

public class ByeCommand extends Command {

    /**
     * Takes in TaskList, Ui and Storage objects which then
     * saves the data prior to existing the run loop.
     * @param task TaskList object containing current active taskList.
     * @param ui Ui object containing all output methods to user.
     * @param store Storage object which updates stored data.
     */
    @Override
    public void execute(TaskList task, Ui ui, Storage store, Reminder reminder) {
        store.writeData(task.getTasks());
        ui.goodbyeMsg();
        reminder.stop();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
