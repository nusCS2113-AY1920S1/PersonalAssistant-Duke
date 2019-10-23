package commands;

import members.Member;
import tasks.Task;
import utils.DukeException;
import utils.Storage;
import core.Ui;
import java.util.ArrayList;

public class LinkCommand extends Command {
    private String line;

    /**
     * This is a class for command LINK, which links a task based on list index to a member's name
     * @param line list index to member name
     */
    public LinkCommand(String line) {
        this.line = line;
    }

    @Override
    public void execute(ArrayList<Task> tasks, ArrayList<Member> members, Storage storage) throws DukeException {
        try {
            String[] arrOfStr = line.split(" to ",2);
            int indexInList = Integer.parseInt(arrOfStr[0]);
            String memberName = arrOfStr[1];

            for (int i = 0; i < members.size(); i++) {
                if (members.get(i).getName().equals(memberName)) {
                    members.get(i).setTask(indexInList);
                    storage.storeMemberList(members);
                    Ui.print("Task " + indexInList + " added to " + memberName);
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