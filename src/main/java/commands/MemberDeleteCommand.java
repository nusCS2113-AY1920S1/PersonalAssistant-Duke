package commands;

import members.Member;
import tasks.Task;
import utils.DukeException;
import utils.Storage;
import core.Ui;
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
    public void execute(ArrayList<Task> tasks, ArrayList<Member> members, Storage storage) throws DukeException {
        try {
            String memberName = line;

            for (int i = 0; i < members.size(); i++) {
                if (members.get(i).getName().equals(memberName)) {
                    members.remove(i);
                    storage.storeMemberList(members);
                    Ui.print("Member: " + memberName + " has been successfully removed from the list of members");
                }
            }
        } catch (Exception e) {
            throw new DukeException("Member not found");
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}