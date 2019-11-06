package sgtravel.logic.commands;

import sgtravel.commons.exceptions.DuplicateTaskException;
import sgtravel.commons.exceptions.FileNotSavedException;
import sgtravel.logic.commands.results.CommandResultText;
import sgtravel.model.Event;
import sgtravel.model.Model;

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
     */
    @Override
    public CommandResultText execute(Model model) throws DuplicateTaskException, FileNotSavedException {
        model.getEvents().add(event);
        model.save();
        return new CommandResultText(MESSAGE_ADDITION + event);
    }
}
