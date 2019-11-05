package duke.logic.commands;

import duke.commons.exceptions.DuplicateTaskException;
import duke.commons.exceptions.FileNotSavedException;
import duke.logic.commands.results.CommandResultText;
import duke.model.Event;
import duke.model.Model;

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
