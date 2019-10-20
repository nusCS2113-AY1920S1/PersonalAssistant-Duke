package duke.logic.commands;

import duke.logic.commands.results.CommandResultText;
import duke.commons.exceptions.DukeException;
import duke.commons.Messages;
import duke.model.Model;
import duke.model.events.Task;

/**
 * Class representing a command to mark a task as done.
 */
public class MarkDoneCommand extends Command {
    private int index;
    private static final String MESSAGE_MARK_DONE = "Nice! I've marked this task as done:\n  ";

    /**
     * Creates a new MarkDoneCommand with the given index.
     *
     * @param index The index of the task.
     */
    public MarkDoneCommand(int index) {
        this.index = index;
    }

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param model The model object containing information about the user.
     */
    @Override
    public CommandResultText execute(Model model) throws DukeException {
        try {
            Task task = model.getTasks().get(index);
            task.setDone(true);
            model.save();
            return new CommandResultText(MESSAGE_MARK_DONE + task);
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException(Messages.OUT_OF_BOUNDS);
        }
    }
}
