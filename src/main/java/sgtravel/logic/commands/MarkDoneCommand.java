package sgtravel.logic.commands;

import sgtravel.commons.Messages;
import sgtravel.commons.exceptions.FileNotSavedException;
import sgtravel.commons.exceptions.OutOfBoundsException;
import sgtravel.logic.commands.results.CommandResultText;
import sgtravel.model.Event;
import sgtravel.model.Model;

/**
 * Marks an Event as done.
 */
public class MarkDoneCommand extends Command {
    private int index;

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
     * @throws OutOfBoundsException If the index is out of bounds.
     * @throws FileNotSavedException If the data cannot be saved.
     */
    @Override
    public CommandResultText execute(Model model) throws OutOfBoundsException, FileNotSavedException {
        try {
            Event event = model.getEvents().get(index);
            event.setDone(true);
            model.save();
            return new CommandResultText(Messages.MARK_DONE_SUCCESS + event);
        } catch (IndexOutOfBoundsException e) {
            throw new OutOfBoundsException();
        }
    }
}
