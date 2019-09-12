package Duke.Command;

import Duke.Util.TaskList;
import Duke.Util.Storage;
import Duke.Util.Ui;
import Duke.Tasks.Task;
import Duke.Exceptions.DukeInvalidIndexException;
import Duke.Exceptions.DukeEmptyListException;

import java.util.Objects;

public class DeleteCommand extends Command {

    private int index;

    /**
     * Constructor for DeleteCommand.
     * @param index is reduced by one to return to zero based indexing.
     */
    public DeleteCommand(int index) {
        this.index = index - 1;
    }

    private int getIndex() {
        return index;
    }
    /**
     * Takes in ui, tasks and store objects, and removes the tasks
     * based on the parsed user input.
     * @param tasks TaskList object containing current active tasks
     * @param ui Ui object containing all the methods to output to user
     * @param store Storage object which updates stored data.
     * @throws DukeInvalidIndexException when user has input an index that
     * is not within the current range
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage store) throws DukeInvalidIndexException, DukeEmptyListException {
        boolean isEmpty = tasks.getTasks().isEmpty();
        if (index < 0 || index >= tasks.getTasks().size()) {
            throw new DukeInvalidIndexException();
        } else if (isEmpty) {
            throw new DukeEmptyListException();
        } else {
            Task temp = tasks.getTasks().get(index);
            tasks.delete(index);
            ui.deleteMsg(temp);
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
        if (!(obj instanceof DeleteCommand)) {
            return false;
        }
        DeleteCommand otherCommand = (DeleteCommand) obj;
        return otherCommand.getIndex() == otherCommand.getIndex();
    }

    @Override
    public int hashCode() {
        return Objects.hash(index);
    }
}
