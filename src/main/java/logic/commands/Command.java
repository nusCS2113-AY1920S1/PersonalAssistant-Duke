package logic.commands;

import model.members.Member;
import model.tasks.Task;
import logic.CommandResult;
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
    public abstract CommandResult execute(ArrayList<Task> tasks,
                                          ArrayList<Member> members, Storage storage) throws DukeException;
}
