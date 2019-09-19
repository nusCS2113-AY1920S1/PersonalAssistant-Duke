package duke.command;

import duke.task.TaskList;
import duke.task.DukeException;
import duke.task.Ui;
import duke.task.Storage;
import duke.task.Task;


/**
 * Represents the command to delete a task.
 */
public class DeleteTaskCommand extends Command {
    /**
     * Takes in a flag to represent if it should exit and the input given by the User.
     * @param isExit True if the program should exit after running this command, false otherwise
     * @param input Input given by the user
     */
    public DeleteTaskCommand(boolean isExit, String input) {
        super(isExit, input);
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException {
        if (input.length() < 8) {
            throw new DukeException("OOPS!!! The task to delete cannot be empty.");
        }
        int taskNumber;
        try {
            taskNumber = Integer.parseInt(input.substring(7));
        } catch (NumberFormatException e) {
            ui.showError("Please enter a number.");
            return;
        }
        if (taskNumber > taskList.getSize()) {
            throw new DukeException("You have entered a number larger than the number of tasks.");
        }
        Task toDelete = taskList.deleteFromArrayList(taskNumber - 1);
        ui.output = "Noted. I've removed this task: \n  " + toDelete.toString()
                + "\nNow you have " + taskList.getSize() + " task(s) in the list.";
        storage.saveToFile();
    }
}
