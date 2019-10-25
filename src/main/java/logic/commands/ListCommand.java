package logic.commands;

import logic.CommandResult;
import model.members.Member;
import model.tasks.Task;
import utils.DukeException;
import utils.Storage;
import core.Ui;
import java.util.ArrayList;

//@@ author yuyanglin28

/**
 * This class is to handle "list" command
 */
public class ListCommand extends Command {
    private String option;

    private String memberName;

    private int taskIndex;

    private boolean showNopicTask = false;

    private boolean showFreeMember = false;

    //@@author yuyanglin28
    /**
     * constructor
     * @param line this is the input of user
     */

    public ListCommand(String line) throws DukeException {
        String[] arrOfStr = line.split("\\s+");
        option = arrOfStr[0].trim();
        if (option.equals("tasks") && arrOfStr.length > 1) {
            if (arrOfStr[1].trim().equals("nopic")) {
                showNopicTask = true;
            }
        } else if (option.equals("members") && arrOfStr.length > 1) {
            if (arrOfStr[1].trim().equals("free")) {
                showFreeMember = true;
            }
        } else if (option.equals("task")) {
            taskIndex = Integer.parseInt(arrOfStr[1].trim());
        } else if (option.equals("member")) {
            memberName = arrOfStr[1].trim();
        } else {
            throw new DukeException("basic usage: list tasks\n" +
                    "for more usage, please refer to the user guide");
        }
    }

    @Override
    public CommandResult execute(ArrayList<Task> tasks, ArrayList<Member> members, Storage storage)
            throws DukeException {
        String output = "Here are the ";

        if (option.equals("tasks") && showNopicTask == false) {
            output += "task(s) in your list:";
            for (int i = 0;i < tasks.size();i++) {
                output += "\n" + (i + 1) + "." + tasks.get(i);
            }
        } else if (option.equals("members") && showFreeMember == false) {
            output += "member(s) in your team:";
            for (int i = 0; i < members.size(); i++) {
                output += "\n" + (i + 1) + "." + members.get(i);
            }
        }

        if (showFreeMember) {
            output += "free member(s): \n";
            int count = 0;
            for (int i = 0; i < members.size(); i++) {
                if (members.get(i).getTasksInCharge().size() == 0) {
                    output += (i + 1) + "." + members.get(i).getName() + " is free.\n";
                    count++;
                }
            }
            if (count > 0) {
                output += "There are/is " + count + " free member(s).";
            } else {
                output = "No one is free";
            }
        } else if (showNopicTask) {
            output += "task(s) haven't been assigned: \n";
            int count = 0;
            for (int i = 0; i < tasks.size(); i++) {
                if (tasks.get(i).getPics().size() == 0) {
                    output += (i + 1) + "." + tasks.get(i) + "\n";
                    count++;
                }
            }
            if (count > 0) {
                output += "There are/is " + count + " task(s) haven't been assigned.";
            } else {
                output = "All tasks have assigned to someone";
            }

        }

        if (option.equals("member")) {
            try {
                int memberIndex = 0;
                for (int i = 0; i < members.size(); i++) {
                    if (members.get(i).getName().equals(memberName)) {
                        memberIndex = i + 1;
                    }
                }
                ArrayList<Task> tasksInCharge = members.get(memberIndex - 1).getTasksInCharge();
                ArrayList<Integer> tasksInChargeIndex = members.get(memberIndex - 1).getTasksInChargeIndex();
                if (tasksInCharge.size() > 0) {
                    output += "task(s) of member ";
                    output += memberIndex + ". " + members.get(memberIndex - 1).getName();
                    for (int i = 0; i < tasksInCharge.size(); i++) {
                        output += "\n" + tasksInChargeIndex.get(i) + ". " + tasksInCharge.get(i);
                    }
                } else {
                    output = "member " + memberIndex + ". " + members.get(memberIndex - 1).getName();
                    output += " is free.";
                }
            } catch (Exception e) {
                throw new DukeException("Member not found.");
            }

        } else if (option.equals("task")) {
            output += "persons in charge of task " + taskIndex + "." + tasks.get(taskIndex - 1);
            if (tasks.get(taskIndex - 1).getPics().size() > 0) {
                for (int i = 0; i < tasks.get(taskIndex - 1).getPics().size(); i++) {
                    int memberIndex = members.indexOf(tasks.get(taskIndex - 1).getPics().get(i)) + 1;
                    output += "\n" + memberIndex + "." + members.get(memberIndex - 1).getName();
                }
            } else {
                output = taskIndex + "." + tasks.get(taskIndex - 1) + " hasn't been assigned.";
            }
        }

        return new CommandResult(output);
    }
}
