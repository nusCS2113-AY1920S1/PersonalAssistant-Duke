package command;

import dukeException.DukeException;
import storage.Storage;
import task.TaskList;
import ui.Ui;

import java.io.IOException;

public class DeleteCommand extends Command {
    private int num;

    public DeleteCommand(String[] splitStr) throws DukeException {
        if (splitStr.length == 1) {
            throw new DukeException("☹ OOPS!!! Please add the index of the task you want to remove");
        }
        this.num = Integer.parseInt(splitStr[1]);
    }

    /**
     * Run the command.
     * @param tasks task list
     * @param ui user interface
     * @param storage handles read write of text file
     * @throws DukeException for Duke specific exception
     * @throws IOException if IO exception found
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException, IOException {
        if (this.num < 1 || this.num > tasks.size()) throw new DukeException("☹ OOPS!!! That task is not in your list");
        ui.showString("Got it. I've removed this task:\n"
                + tasks.get(this.num - 1).toString());
        tasks.remove(this.num - 1);
        storage.saveToFile(tasks);
        ui.showString("Now you have " + tasks.size() + " task(s) in the list.");
    }
}
