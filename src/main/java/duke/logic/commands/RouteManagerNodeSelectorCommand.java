package duke.logic.commands;

import duke.commons.exceptions.DukeException;
import duke.logic.commands.results.CommandResultText;
import duke.model.Model;

public class RouteManagerNodeSelectorCommand extends Command{
    private int index;
    private static final String MESSAGE_SUCCESS = "Alright! Selecting this Node.\n";

    /**
     * Creates a new DeleteCommand with the given index.
     *
     * @param index The index of the task.
     */
    public RouteManagerNodeSelectorCommand(int index) {
        this.index = index;
    }

    /**
     * Executes this command and returns a text result.
     *
     * @param model The model object containing event list.
     */
    @Override
    public CommandResultText execute(Model model) throws DukeException {
        model.getRouteManager().setNode(index);
        return new CommandResultText(MESSAGE_SUCCESS);
    }
}
