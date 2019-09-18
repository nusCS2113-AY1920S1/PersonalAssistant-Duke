package commands;

import tasks.Task;
import utils.Storage;
import core.Ui;
import java.util.ArrayList;

/**
 * This class is to handle "list" command
 */
public class ListCommand extends Command {
    /**
     * This is a class for command LIST, which list all the tasks in the task list.
     */
    public ListCommand(){
    }

    @Override
    public void execute(ArrayList<Task> tasks, Storage storage) {
        String output = "Here are the tasks in your list:";
        for (int i = 0;i < tasks.size();i++) {
            output += "\n" + (i + 1) + "." + tasks.get(i);
        }
        Ui.print(output);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
