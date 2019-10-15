package commands;

import members.Member;
import tasks.Task;
import utils.Storage;
import core.Ui;
import java.util.ArrayList;

/**
 * This class is to handle "list" command
 */
public class ListCommand extends Command {
    private String option;

    /**
     * This is a class for command LIST, which list all the tasks in the task list.
     */
    public ListCommand(){
    }

    public ListCommand(String option) {
        this.option = option;
    }

    @Override
    public void execute(ArrayList<Task> tasks, ArrayList<Member> members, Storage storage) {
        String output = "Here are the ";
        if (option.equals("tasks")) {
            output += "tasks in your list:";
            for (int i = 0;i < tasks.size();i++) {
                output += "\n" + (i + 1) + "." + tasks.get(i);
            }
        } else if (option.equals("members")) {
            output = "members in your team:";
            for (int i = 0; i < members.size(); i++) {
                output += "\n" + (i + 1) + "." + members.get(i);
            }
        }
        Ui.print(output);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
