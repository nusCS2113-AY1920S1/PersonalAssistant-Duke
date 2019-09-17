package command;

import dukeException.DukeException;
import storage.Storage;
import task.TaskList;
import ui.Ui;

import java.io.IOException;

/**
 * command.Command to mark task as done
 */
public class DoneCommand extends Command {
    private int num;

    public DoneCommand(String[] splitStr) throws DukeException {
        if (splitStr.length == 1) {
            throw new DukeException("☹ OOPS!!! Please add the index of the task you have completed");
        }
        num = Integer.parseInt(splitStr[1]);
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException, IOException {
        if (num < 1 || num > tasks.size()) {
            throw new DukeException("☹ OOPS!!! That task is not in your list");
        }
        tasks.get(num - 1).markAsDone();
        storage.saveToFile(tasks);
    }
}
