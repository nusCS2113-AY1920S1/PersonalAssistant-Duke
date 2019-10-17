package commands;

import members.Member;
import tasks.Task;
import utils.DukeException;
import utils.Storage;

import java.util.ArrayList;

/**
 * a general class for Command, to be extended
 */
public abstract class Command {
    /**
     * The method to execute the corresponding command.
     * @param tasks the tasklist ArrayList
     * @param storage the object to manage storage issues
     * @throws DukeException if anything wrong happend
     */
    public abstract void execute(ArrayList<Task> tasks,
                                 ArrayList<Member> members, Storage storage) throws DukeException;

    /**
     * simple return if the command is bye.
     * @return if the command is bye
     */
    public abstract boolean isExit();
}
