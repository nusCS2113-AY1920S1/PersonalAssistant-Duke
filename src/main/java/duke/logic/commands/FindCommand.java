package duke.logic.commands;

import duke.logic.commands.results.CommandResultText;
import duke.commons.exceptions.DukeException;
import duke.model.Event;
import duke.model.Model;
import duke.model.lists.EventList;

/**
 * Finds a task by keyword.
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
     * Executes this command on the given task list and user interface.
     *
     * @param model The model object containing information about the user.
     */
    @Override
    public CommandResultText execute(Model model) throws DukeException {
        EventList events = model.getEvents();
        EventList result = new EventList();
        for (Event event: events) {
            if (event.toString().contains(keyword)) {
                result.add(event);
            }
        }
        return new CommandResultText(result);
    }
}
