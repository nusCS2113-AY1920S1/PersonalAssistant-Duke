package logic.commands;

import model.members.Member;
import model.tasks.Task;
import logic.CommandResult;
import utils.DukeException;
import utils.Storage;

import java.util.ArrayList;

/**
 * This class is to handle "delete" command
 */
public class DeleteCommand extends Command {

    private int[] indexes;

    /**
     * This is a class for command DELETE, which remove one task from the task list.
     * @param indexes the serial numbers in the command line after DELETE,
     *                which represents the indexes of tasks to be deleted
     */
    public DeleteCommand(int[] indexes) {
        this.indexes = indexes;
    }

    @Override
    public CommandResult execute(ArrayList<Task> tasks, ArrayList<Member> members, Storage storage)
            throws DukeException {
        try {
            Task[] toDelete = new Task[indexes.length];
            for (int i = 0; i < indexes.length; i++) {
                toDelete[i] = tasks.get(indexes[i]);
            }
            for (int i = 0; i < toDelete.length; i++) {
                tasks.remove(toDelete[i]);
            }
            storage.storeTaskList(tasks);
            return new CommandResult("Noted. I've removed the task(s) you input. \n");
        } catch (Exception e) {
            throw new DukeException("Not a valid task number");
        }
    }
}
