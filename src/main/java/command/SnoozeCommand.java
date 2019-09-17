package command;

import dukeException.DukeException;
import storage.Storage;
import task.TaskList;
import ui.Ui;

import java.io.*;


public class SnoozeCommand extends Command{
    private int n;
    public SnoozeCommand(String[] splitStr) throws DukeException {
        if (splitStr.length == 1)
            throw new DukeException("☹ OOPS!!! Please add the index of the task you want to remove");
        this.n = Integer.parseInt(splitStr[1]);
    }

    /**
     * Run the command
     * @param tasks task list
     * @param ui user interface
     * @param storage handles read write of text file
     * @throws DukeException
     * @throws IOException
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException, IOException {
        if (this.n < 1 || this.n > tasks.size()) throw new DukeException("☹ OOPS!!! That task is not in your list");
        //ui.showString("Got it. I've deactivated this task:\n" +
          //      tasks.get(this.n - 1).toString());
        tasks.get(this.n - 1).markAsSnooze();
        //tasks.remove(this.n -1);
        storage.saveToFile(tasks);
        int activeTasks = tasks.size() - 1;
        ui.showString("Now you have " + activeTasks + " active task(s) in the list.");
    }
}