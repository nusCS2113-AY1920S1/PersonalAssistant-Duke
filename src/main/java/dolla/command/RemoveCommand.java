package dolla.command;

import dolla.ui.Ui;
import dolla.task.TaskList;

import java.util.ArrayList;

/**
 * RemoveCommand is a Command used to remove a Task from the TaskList.
 */
public abstract class RemoveCommand extends Command {
    protected String taskNumStr;

    public RemoveCommand(String taskNumStr) {
        this.taskNumStr = taskNumStr;
    }

    /**
     * Removes a task from the specified TaskList.
     * <p>
     *     The method first converts taskNumStr into an int. It will then check if a task
     *     corresponding to that number exists in the specified TaskList and subsequently
     *     remove that task if so.
     * </p>
     * <p>
     *     If taskNumStr is not an int, the method will return without doing anything.
     * </p>
     * <p>
     *     If taskNumInt does not correspond to any task in the specified TaskList, an
     *     alert is printed to the user, and the method will return.
     * </p>
     * @param tasks The TaskList where the task corresponding to taskNum is to removed.
     */
    //@Override
    public void execute(TaskList tasks) {
        int taskNumInt = stringToInt(taskNumStr);
        if (taskNumInt == 0) {
            return; // don't do anything if task number is invalid
        }

        ArrayList<String> msg = new ArrayList<String>();
        try {
            tasks.getFromList(taskNumInt - 1); // Check if the task exists first
            msg.add("Noted. I've removed this task: ");
            msg.add("  " + tasks.getFromList(taskNumInt - 1).getTask());
            tasks.removeFromList(taskNumInt - 1);
            msg.add("Now you have " + tasks.size() + " tasks in the list.");
        } catch (IndexOutOfBoundsException e) {
            //Ui.printNoTaskAssocError(taskNumInt);
            return;
        }
        Ui.printMsg(msg);
    }
}
