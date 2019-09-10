import java.util.*;
import java.io.*;
import java.text.*;

/**
 * Carries out the necessary operations to mark a Task as done, when
 * supplied the index number of that Task in the TaskList.
 */

public class DoneCommand extends Command {
    protected int taskNo;

    /**
     * Constructor for a DoneCommand.
     * @param taskNo the index number of the Task to be marked as done.
     */
    public DoneCommand(String taskNo) {
        super();
        this.taskNo = Integer.parseInt(taskNo) - 1;
    }

    /**
     * This method will check if the task number is valid, and then mark as done the Task
     * whose index number matches the number provided by the user.
     * @param ui An instance of the user interface.
     * @param storage An instance of the Storage class.
     * @param tasks The TaskList, containing all the created Tasks thus far.
     * @throws DukeException if the number provided by the user is invalid.
     */
    public void execute(Ui ui, Storage storage, TaskList tasks) throws DukeException {
        if (taskNo + 1 > tasks.getNumberOfItems()) {
            throw new DukeException("We don't have that many tasks!");
        }
        tasks.markItemAsDone(taskNo);
        ui.printTask("Nice! I've marked this task as done:", tasks.getItemToPrint(taskNo));
    }
}