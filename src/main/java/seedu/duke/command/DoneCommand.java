package seedu.duke.command;

import seedu.duke.Duke;
import seedu.duke.Parser;

/**
 * DoneCommand is a specific kind of command used to mark a task as done.
 */
public class DoneCommand extends Command {
    private int index;

    /**
     * Instantiation of done command with the index of the target task.
     *
     * @param i the index of the target task to be marked as done
     */
    public DoneCommand(int i) {
        index = i;
    }

    /**
     * Executes the done command by calling the markDone function of the task list.
     *
     * @return a flag whether the task is successfully marked as done. Returns false if the markDone function
     *         throws exception.
     */
    @Override
    public boolean execute() {
        try {
            String msg = Duke.getTaskList().markDone(index);
            if (!silent) {
                responseMsg = msg;
                Duke.getUI().showResponse(msg);
            }
            return true;
        } catch (Parser.UserInputException e) {
            if (!silent) {
                Duke.getUI().showError(e.toString());
            }
            return false;
        }
    }
}
