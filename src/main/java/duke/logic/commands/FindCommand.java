package duke.logic.commands;

import duke.commons.exceptions.DuplicateTaskException;
import duke.logic.commands.results.CommandResultText;
import duke.model.Event;
import duke.model.Model;
import duke.model.lists.EventList;

/**
 * Finds an Event by keyword.
 */
public class FindCommand extends Command {
    private String keyword;

    /**
     * Creates a new FindCommand with the given keyword.
     *
     * @param keyword The keyword to find.
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Executes this command and returns a text result.
     *
     * @param model The model object containing event list.
     */
    @Override
    public CommandResultText execute(Model model) throws DuplicateTaskException {
        EventList events = model.getEvents();
        EventList result = find(events);
        return new CommandResultText(result);
    }

    /**
     * Finds events with matching keyword.
     *
     * @param events The EventList to be search from.
     * @return EventList containing the events.
     * @throws DuplicateTaskException If there are duplicated events found.
     */
    private EventList find(EventList events) throws DuplicateTaskException {
        EventList result = new EventList();
        for (Event event: events) {
            if (event.toString().contains(keyword)) {
                result.add(event);
            }
        }
        return result;
    }
}
