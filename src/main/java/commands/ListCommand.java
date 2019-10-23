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
    private String line;

    private String option;

    private String memberIndex;

    /**
     * This is a class for command LIST, which list all the tasks in the task list.
     */
    public ListCommand(){
    }

    public ListCommand(String line) {
        this.line = line;
    }

    @Override
    public void execute(ArrayList<Task> tasks, ArrayList<Member> members, Storage storage) {
        String[] option = line.split("\\s+");
        String output = "Here are the ";
        if (option[0].equals("tasks")) {
            output += "task(s) in your list:";
            for (int i = 0;i < tasks.size();i++) {
                output += "\n" + (i + 1) + "." + tasks.get(i);
            }
        } else if (option[0].equals("members")) {
            output = "members in your team:";
            for (int i = 0; i < members.size(); i++) {
                output += "\n" + (i + 1) + "." + members.get(i);
            }
        } else if (option[0].equals("member") && option.length > 1) {
            int memberIndex = Integer.parseInt(option[1]);
            ArrayList<Task> tasksInCharge = members.get(memberIndex - 1).getTasksInCharge();
            ArrayList<Integer> tasksInChargeIndex = members.get(memberIndex - 1).getTasksInChargeIndex();
            if (tasksInCharge.size() > 0) {
                output += "task(s) of member ";
                output += memberIndex + ". " + members.get(memberIndex - 1).getName();
                for (int i = 0; i < tasksInCharge.size(); i++) {
                    output += "\n" + tasksInChargeIndex.get(i) + ". " + tasksInCharge.get(i);
                }
            } else {
                output += "member " + memberIndex + ". " + members.get(memberIndex - 1).getName();
                output += " is free.";
            }

        }
        Ui.print(output);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
