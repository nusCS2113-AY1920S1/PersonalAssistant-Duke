package duke.logic.commands;

import duke.logic.commands.results.CommandResultText;
import duke.commons.exceptions.DukeException;
import duke.model.Event;
import duke.model.Model;

/**
 * Adds a new event to users EventList.
 */
public class AddCommand extends Command {
    private final Event event;
    private static final String MESSAGE_ADDITION = "Got it. I've added this task:\n  ";

    /**
     * Creates a new AddCommand with the given task.
     *
     * @param event The event to add.
     */
    public AddCommand(Event event) {
        this.event = event;
    }

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param model The model object containing task list.
     */
    @Override
    public CommandResultText execute(Model model) throws DukeException {
        model.getEvents().add(event);
        model.save();
        return new CommandResultText(MESSAGE_ADDITION + event);
    }
}
