package commands;

import core.Duke;
import members.Member;
import tasks.Task;
import utils.DukeException;
import utils.Storage;
import core.Ui;

import java.util.ArrayList;

/**
 * This class is to handle "delete" command
 */
public class DeleteCommand extends Command {

    private String line;

    //@@author yuyanglin28
    /**
     * This is a class for command DELETE, which remove one task from the task list.
     * @param line the serial number in the command line after DELETE
     */
    public DeleteCommand(String line) {
        this.line = line;
    }

    @Override
    public void execute(ArrayList<Task> tasks, ArrayList<Member> members, Storage storage) throws DukeException {
        try {
            int order = Integer.parseInt(line);
            for (int i = 0; i < members.size(); i++) {
                members.get(i).getTasksInCharge().remove(tasks.get(order - 1));
            }
            Ui.print("Noted. I've removed this task: \n" + tasks.remove(order - 1));
            for (int i = 0; i < members.size(); i++) {
                members.get(i).updateIndex();
            }
            storage.storeTaskList(tasks);
            storage.storeMemberList(members);
        } catch (Exception e) {
            throw new DukeException("Not a valid task number");
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
