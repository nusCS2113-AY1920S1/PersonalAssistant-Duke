package duke.logic.commands;

import duke.commons.exceptions.CorruptedFileException;
import duke.commons.exceptions.FileNotSavedException;
import duke.commons.exceptions.QueryOutOfBoundsException;
import duke.logic.commands.results.CommandResultText;
import duke.model.Event;
import duke.model.Model;

/**
 * Marks an Event as done.
 */
public class MarkDoneCommand extends Command {
    private int index;
    private static final String MESSAGE_MARK_DONE = "Nice! I've marked this task as done:\n  ";

    /**
     * Creates a new MarkDoneCommand with the given index.
     *
     * @param index The index of the event.
     */
    public MarkDoneCommand(int index) {
        this.index = index;
    }

    /**
     * Executes this command and returns a text result.
     *
     * @param model The model object containing event list.
     * @throws QueryOutOfBoundsException If the index is out of bounds.
     * @throws FileNotSavedException If the data cannot be saved.
     */
    @Override
    public CommandResultText execute(Model model) throws QueryOutOfBoundsException, FileNotSavedException {
        try {
            Event event = model.getEvents().get(index);
            event.setDone(true);
            model.save();
            return new CommandResultText(MESSAGE_MARK_DONE + event);
        } catch (IndexOutOfBoundsException e) {
            throw new QueryOutOfBoundsException();
        }
    }
}
