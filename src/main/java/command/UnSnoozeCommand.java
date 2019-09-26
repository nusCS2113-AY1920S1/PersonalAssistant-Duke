package command;

import exception.DukeException;
import storage.Storage;
import task.TaskList;
import ui.Ui;

import java.io.IOException;


public class UnSnoozeCommand extends Command {
    private int num;

    /**
     *
     * @param splitStr
     * @throws DukeException
     */
    public UnSnoozeCommand(String[] splitStr) throws DukeException {
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
     * @throws DukeException
     * @throws IOException
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException, IOException {
        if (this.num < 1 || this.num > tasks.size()) {
            throw new DukeException("☹ OOPS!!! That task is not in your list");
        }
        tasks.get(this.num - 1).markAsUnSnooze();
        storage.saveToFile(tasks);
        int activeTasks = tasks.size() + 1;
        ui.showString("Now you have " + activeTasks + " active task(s) in the list.");
    }
}