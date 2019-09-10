package duke.command;

import java.io.IOException;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;

/**
 * Class representing a command to delete an item from the task list.
 */
public class DeleteCommand extends Command {

    private final int taskNumber;
    private final String data;

    /**
     * Creates a new DeleteCommand with the specified index.
     *
     * @param data The index of the task to delete, where the first task is 1.
     * @throws DukeException when the string cannot be parsed into an integer.
     */
    public DeleteCommand(String data) throws DukeException {
        data = data.trim();
        this.data = data;
        String pattern = "^[0-9]+$";
        if (!data.matches(pattern)) {
            throw new DukeException("The task number should be numeric only");
        } else {
            try {
                this.taskNumber = Integer.parseInt(data);
            } catch (NumberFormatException exceptionMessage) {
                throw new DukeException("The number must be an integer and cannot exceed 9 digits");
            }
        }
    }

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param tasks The task list.
     * @param ui The user interface displaying events on the task list.
     * @param storage The place where tasks will be stored.
     */
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        if (this.taskNumber <= tasks.size()) {
            try {
                String taskInformation = tasks.get(taskNumber).toString();
                tasks.delete(this.taskNumber);
                ui.printMessage("Noted. I've removed this task: ");
                ui.printMessage(taskInformation);
                ui.printMessage("Now you have " + tasks.size() + " tasks in the list.");
                try {
                    storage.writeFile(tasks.export());
                } catch (IOException e) {
                    ui.printError("Error writing tasks to file");
                }
            } catch (IndexOutOfBoundsException exceptionMessage) {
                ui.printError("Invalid task number, there are " + tasks.size() + " tasks");
                ui.printError("You entered: " + data);
            }
        } else {
            ui.printError("Invalid task number, there are " + tasks.size() + " tasks");
        }

    }
}
