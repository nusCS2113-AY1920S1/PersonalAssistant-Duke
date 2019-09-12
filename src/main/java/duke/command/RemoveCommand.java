package duke.command;

import duke.Ui;
import duke.task.TaskList;

import java.util.ArrayList;

public class RemoveCommand extends Command {
    protected String taskNumStr;

    public RemoveCommand(String taskNumStr) {
        this.taskNumStr = taskNumStr;
    }

    @Override
    public void execute(TaskList tasks) {
        int taskNumInt = stringToInt(taskNumStr);
        if (taskNumInt == 0) return; // don't do anything if task number is invalid

        ArrayList<String> msg = new ArrayList<String>();
        try {
            tasks.getFromList(taskNumInt-1); // Check if the task exists first
            msg.add("Noted. I've removed this task: ");
            msg.add("  " + tasks.getFromList(taskNumInt-1).getTask());
            tasks.removeFromList(taskNumInt-1);
            msg.add("Now you have " + tasks.size() + " tasks in the list.");
        }
        catch (IndexOutOfBoundsException e) {
            msg.add(taskNumInt + " is not associated to any task number.");
            msg.add("Use 'list' to check the tasks that are here first!");
            Ui.printMsg(msg);
            return;
        }

        Ui.printMsg(msg);

    }
}
