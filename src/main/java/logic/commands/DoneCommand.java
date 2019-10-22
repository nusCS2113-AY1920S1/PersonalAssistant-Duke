package commands;

import members.Member;
import tasks.Task;
import utils.CommandResult;
import utils.DukeException;
import utils.Storage;
import core.Ui;

import java.util.ArrayList;

/**
 * This class is to handle "done" command
 */
public class DoneCommand extends Command {
    private int index;

    /**
     * This is a class for command DONE, which mark one task in the task list as done.
     *
     * @param index the serial number of task to be marked as done
     */
    public DoneCommand(int index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(ArrayList<Task> tasks, ArrayList<Member> members, Storage storage)
            throws DukeException {
        tasks.get(index).markAsDone();
        storage.storeTaskList(tasks);
        return new CommandResult("Nice! I've marked this task as done: \n" + tasks.get(index));
    }
}
