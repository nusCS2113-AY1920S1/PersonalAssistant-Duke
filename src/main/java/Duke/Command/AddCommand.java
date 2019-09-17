package Duke.Command;

import Duke.Exceptions.DukeCommandException;
import Duke.Exceptions.DukeException;
import Duke.Tasks.Task;
import Duke.Util.TaskList;
import Duke.Util.Ui;
import Duke.Util.Storage;

import java.util.Objects;


public class AddCommand extends Command {

    private Task task;

    public AddCommand(Task task) {
        this.task = task;
    }

    private Task getTask() {
        return task;
    }
    /**
     * Takes in TaskList, Ui and Storage objects which then adds
     * a new task at the end of the TaskList.
     * @param tasks TaskList object containing current active taskList.
     * @param ui Ui object containing all output methods to user.
     * @param store Storage object which updates stored data.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage store) {
        try {
            tasks.add(task);
            ui.addedTaskMsg();
            ui.printTask(task);
            ui.currentTaskListSizeMsg(tasks.getSize());
            store.writeData(tasks.getTasks());
        } catch (NullPointerException e) {
            ui.showLine();
            new DukeCommandException();
            ui.showLine();
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
        if (!(obj instanceof AddCommand)) {
            return false;
        }
        AddCommand otherCommand = (AddCommand) obj;
        return otherCommand.getTask() == otherCommand.getTask();
    }

    @Override
    public int hashCode() {
        return Objects.hash(task);
    }
}
