package commands;

import members.Member;
import core.Ui;
import tasks.Task;
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
    public void execute(ArrayList<Task> tasks, ArrayList<Member> members, Storage storage) {
        Ui.print("Bye. Hope to see you again soon!");
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
