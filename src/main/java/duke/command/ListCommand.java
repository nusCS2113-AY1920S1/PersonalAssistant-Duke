package duke.command;

import duke.util.Reminder;
import duke.util.TaskList;
import duke.util.Storage;
import duke.util.Ui;
import duke.exceptions.DukeEmptyListException;

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
    public void execute(TaskList tasks, Ui ui, Storage store, Reminder reminder) throws DukeEmptyListException {
        boolean isEmpty = tasks.getTasks().isEmpty();
        if (isEmpty) {
            throw new DukeEmptyListException();
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
