package planner.command;

import planner.util.Reminder;
import planner.util.TaskList;
import planner.util.Storage;
import planner.util.Ui;
import planner.exceptions.ModInvalidIndexException;

import java.util.Objects;

public class DoneCommand extends Command {

    private int index;

    /**
     * Constructor for DoneCommand.
     * @param index is reduced by one
     *             to return to zero based indexing.
     */
    public DoneCommand(int index) {
        this.index = index - 1;
    }

    private int getIndex() {
        return index;
    }

    /**
     * Takes in TaskList, Ui and Storage objects which then marks
     * the task index which has been completed.
     * @param tasks TaskList object containing current active taskList.
     * @param ui Ui object containing all output methods to user.
     * @param store Storage object which updates stored data.
     * @throws ModInvalidIndexException when user has input an index that
     *                  is not within the current range.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage store, Reminder reminder) throws ModInvalidIndexException {
        if (index >= tasks.getTasks().size() || index < 0) {
            throw new ModInvalidIndexException();
        } else {
            tasks.getTasks().get(index).setTaskDone();
            ui.doneTaskMsg(tasks.getTasks().get(index));
            store.writeData(tasks.getTasks());
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof DoneCommand)) {
            return false;
        }
        DoneCommand otherCommand = (DoneCommand) obj;
        return otherCommand.getIndex() == otherCommand.getIndex();
    }

    @Override
    public int hashCode() {
        return Objects.hash(index);
    }
}
