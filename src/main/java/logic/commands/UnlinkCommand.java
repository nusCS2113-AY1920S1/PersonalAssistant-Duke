package logic.commands;

import logic.CommandResult;
import model.members.Member;
import model.tasks.Task;
import utils.DukeException;
import utils.Storage;
import core.Ui;
import java.util.ArrayList;

public class UnlinkCommand extends Command {
    private String line;

    /**
     * This is a class for command UNLINK, which unlinks a task based on list index to a member's name using /from
     * @param line list index to member name
     */
    public UnlinkCommand(String line) {
        this.line = line;
    }

    @Override
    public CommandResult execute(ArrayList<Task> tasks, ArrayList<Member> members, Storage storage) throws DukeException {
        try {
            String[]arrOfStr = line.split(" /from ",2);
            int indexInList = Integer.parseInt(arrOfStr[0]);
            String memberName = arrOfStr[1];

            for (int i = 0; i < members.size(); i++) {
                if (members.get(i).getName().equals(memberName)) {

                    ArrayList<Member> pics = tasks.get(indexInList - 1).getPics();
                    pics.remove(members.get(i));
                    tasks.get(indexInList - 1).setPics(pics);

                    members.get(i).removeTask(indexInList);
                    storage.storeMemberList(members);
                    return new CommandResult("Task " + indexInList + " is successfully removed from " + memberName);
                }
            }
            throw new DukeException("Member not found or invalid task index");
        } catch (Exception e) {
            throw new DukeException("Member not found or invalid task index");
        }
    }
}