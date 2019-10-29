package duke.logic.commands;

import duke.commons.exceptions.DukeException;
import duke.logic.commands.results.CommandResultText;
import duke.model.Model;

/**
 * Class representing a command to handle a RouteManagerReturnCommand.
 */
public class RouteManagerReturnCommand extends Command {
    private static final String MESSAGE_SUCCESS = "Alright! Selecting back the Route instead!\n";

    /**
     * Executes this command and returns a text result.
     *
     * @param model The model object containing event list.
     */
    @Override
    public CommandResultText execute(Model model) throws DukeException {
        if (model.getRouteManager().getNodeIndex() == -1 && model.getRouteManager().getRouteIndex() != -1  ) {
            model.getRouteManager().resetRoute();
        }
        model.getRouteManager().resetNode();
        return new CommandResultText(MESSAGE_SUCCESS);
    }
}
