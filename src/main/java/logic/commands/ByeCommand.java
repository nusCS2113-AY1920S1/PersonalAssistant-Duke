package commands;

import members.Member;
import core.Ui;
import tasks.Task;
import utils.CommandResult;
import utils.Storage;

import java.util.ArrayList;

/**
 * This class is to handle "bye" command
 */
public class ByeCommand extends Command {
    /**
     * This is a class for command BYE, which exit the Duke assistant.
     */
    public ByeCommand() {
    }

    @Override
    public CommandResult execute(ArrayList<Task> tasks, ArrayList<Member> members, Storage storage) {
        CommandResult cr = new CommandResult("Bye. Hope to see you again soon!");
        cr.setExit();
        return cr;
    }
}
