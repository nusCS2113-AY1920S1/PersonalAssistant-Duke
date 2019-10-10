package command;

import ui.Ui;
import dolla.task.Task;
import dolla.task.TaskList;

import java.util.ArrayList;

/**
 * duke.command.ShowListCommand is a command used to display all the tasks stored in the duke.task.TaskList.
 */
public abstract class ShowListCommand extends Command {

    /**
     * Creates and prints out the tasks from the specified duke.task.TaskList in a readable format.
     * @param tasks duke.task.TaskList to have its tasks printed.
     */
    //@Override
    public void execute(TaskList tasks) {
        ArrayList<String> msg = new ArrayList<String>();
        msg.add("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            Task currTask = tasks.getFromList(i);
            msg.add((i + 1) + "."  + currTask.getTask());
        }
        Ui.printMsg(msg);
    }
}
