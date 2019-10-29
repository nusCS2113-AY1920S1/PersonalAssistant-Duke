package duke.logic.commands;

import duke.commons.exceptions.QueryOutOfBoundsException;
import duke.logic.commands.results.CommandResultText;
import duke.model.Model;

public class RouteManagerSelectorCommand extends Command{
    private int index;
    private static final String MESSAGE_SUCCESS = "Alright! Selecting this Route.\n  ";

    /**
     * Creates a new DeleteCommand with the given index.
     *
     * @param index The index of the task.
     */
    public RouteManagerSelectorCommand(int index) {
        this.index = index;
    }

    /**
     * Executes this command and returns a text result.
     *
     * @param model The model object containing event list.
     */
    @Override
    public CommandResultText execute(Model model) throws QueryOutOfBoundsException {
        model.getRouteManager().setRoute(index);
        return new CommandResultText(MESSAGE_SUCCESS);
    }
}
