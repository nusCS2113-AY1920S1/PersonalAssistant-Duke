package logic.commands;

import model.members.Member;
import model.tasks.Task;
import logic.CommandResult;
import utils.DukeException;
import utils.Storage;

import java.util.ArrayList;

public class MemberDeleteCommand extends Command {
    private String line;

    /**
     * This is a class for command UNLINK, which unlinks a task based on list index to a member's name using /from
     * @param line list index to member name
     */
    public MemberDeleteCommand(String line) {
        this.line = line;
    }

    @Override
    public CommandResult execute(ArrayList<Task> tasks, ArrayList<Member> members, Storage storage)
            throws DukeException {
        try {
            String memberName = line;
            for (int i = 0; i < members.size(); i++) {
                if (members.get(i).getName().equals(memberName)) {
                    members.remove(i);
                    storage.storeMemberList(members);
                    return new CommandResult("Member: " + memberName
                            + " has been successfully removed from the list of members");
                }
            }
            throw new DukeException("Member not found");
        } catch (Exception e) {
            throw new DukeException("Member not found");
        }
    }
}