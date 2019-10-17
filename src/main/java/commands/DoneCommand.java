package commands;

import members.Member;
import tasks.Task;
import utils.DukeException;
import utils.Storage;
import core.Ui;

import java.util.ArrayList;

/**
 * This class is to handle "done" command
 */
public class DoneCommand extends Command {
    private String line;

    /**
     * This is a class for command DONE, which mark one task in the task list as done.
     *
     * @param line the serial number of task to be marked as done
     */
    public DoneCommand(String line) {
        this.line = line;
    }

    @Override
    public void execute(ArrayList<Task> tasks, ArrayList<Member> members, Storage storage) throws DukeException {
        try {
            int order = Integer.parseInt(line);
            tasks.get(order - 1).markAsDone();
            storage.storeTaskList(tasks);
            Ui.print("Nice! I've marked this task as done: \n" + tasks.get(order - 1));
        } catch (DukeException e) {
            Ui.print(e.getMessage());
        } catch (Exception e) {
            throw new DukeException("Not a valid task number");
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
