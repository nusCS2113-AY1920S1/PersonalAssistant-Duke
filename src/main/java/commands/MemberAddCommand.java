package commands;

import members.Member;
import core.Ui;
import tasks.Task;
import utils.CommandResult;
import utils.Storage;

import java.util.ArrayList;

/**
 * This class is to handle "add" command
 */
public class MemberAddCommand extends Command {
    private String name;

    /**
     * This is a class for command of ADD, which add task to the task list.
     *
     * @param name the command line String without the first ADD command
     */
    public MemberAddCommand(String name) {
        this.name = name;
    }

    @Override
    public CommandResult execute(ArrayList<Task> tasks, ArrayList<Member> members, Storage storage)  {
        Member newMember = new Member(this.name);
        members.add(newMember);
        storage.storeMemberList(members);
        return new CommandResult("Nice, I've added " + name + " to your team.\n"
                + "Now you have " + members.size() + " members.");
    }
}

