package planner.command;

import planner.util.Reminder;
import planner.util.TaskList;
import planner.util.Storage;
import planner.util.Ui;
import planner.exceptions.ModEmptyListException;

public class ListCommand extends Command {

    public ListCommand() {

    }

    /**
     * Takes in TaskList, Ui and Storage objects which then displays
     * all the actively tracked Tasks in TaskList.
     * @param tasks TaskList object containing current active taskList.
     * @param ui Ui object containing all output methods to user.
     * @param store Storage object which updates stored data.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage store, Reminder reminder) throws ModEmptyListException {
        boolean isEmpty = tasks.getTasks().isEmpty();
        if (isEmpty) {
            throw new ModEmptyListException();
        } else {
            ui.listMsg();
            ui.printTaskList(tasks.getTasks());
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
