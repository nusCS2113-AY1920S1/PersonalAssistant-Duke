package duke.logic.commands;

import duke.commons.exceptions.ChronologyBeforePresentException;
import duke.commons.exceptions.ChronologyInconsistentException;
import duke.commons.exceptions.DuplicateTaskException;
import duke.commons.exceptions.FileNotSavedException;
import duke.logic.commands.results.CommandResultText;
import duke.model.Event;
import duke.model.Model;

import java.time.LocalDateTime;

/**
 * Adds a new event to users EventList.
 */
public class AddCommand extends Command {
    private final Event event;
    private static final String MESSAGE_ADDITION = "Got it. I've added this event:\n  ";

    /**
     * Creates a new AddCommand with the given event.
     *
     * @param event The event to add.
     */
    public AddCommand(Event event) {
        this.event = event;
    }

    /**
     * Executes this command and returns a text result.
     *
     * @param model The model object containing event list.
     * @throws FileNotSavedException If the data could not be saved.
     * @throws DuplicateTaskException If there is a duplicated event.
     * @throws ChronologyBeforePresentException If the dates are before now.
     * @throws ChronologyInconsistentException If the dates are inconsistent.
     */
    @Override
    public CommandResultText execute(Model model) throws DuplicateTaskException, FileNotSavedException,
            ChronologyInconsistentException, ChronologyBeforePresentException {
        checkLogicalDate(event.getStartDate(), event.getEndDate());
        model.getEvents().add(event);
        model.save();
        return new CommandResultText(MESSAGE_ADDITION + event);
    }

    /**
     * Checks if the dates provided is logical.
     * Does nothing if it's logical.
     * @param end The end date.
     * @param start The start date.
     * @throws ChronologyBeforePresentException If the dates are before now.
     * @throws ChronologyInconsistentException If the dates are inconsistent.
     */
    private void checkLogicalDate(LocalDateTime start, LocalDateTime end) throws ChronologyBeforePresentException,
            ChronologyInconsistentException {
        if (start.isBefore(LocalDateTime.now()) || end.isBefore(LocalDateTime.now())) {
            throw new ChronologyBeforePresentException();
        } else if (end.isBefore(start)) {
            throw new ChronologyInconsistentException();
        }
    }
}
