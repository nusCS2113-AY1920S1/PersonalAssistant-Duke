package duke.logic.commands;

import duke.commons.exceptions.FileNotSavedException;
import duke.commons.exceptions.QueryOutOfBoundsException;
import duke.logic.commands.results.CommandResultText;
import duke.model.Event;
import duke.model.Model;

/**
 * Deletes an Event.
 */
public class DeleteCommand extends Command {
    private int index;
    private static final String MESSAGE_DELETE = "Alright! I've removed this task:\n  ";

    /**
     * Creates a new DeleteCommand with the given index.
     *
     * @param index The index of the task.
     */
    public DeleteCommand(int index) {
        this.index = index;
    }

    /**
     * Executes this command and returns a text result.
     *
     * @param model The model object containing event list.
     */
    @Override
    public CommandResultText execute(Model model) throws QueryOutOfBoundsException, FileNotSavedException {
        try {
            Event event = model.getEvents().remove(index);
            model.save();
            return new CommandResultText(MESSAGE_DELETE + event);
        } catch (IndexOutOfBoundsException e) {
            throw new QueryOutOfBoundsException();
        }
    }
}
