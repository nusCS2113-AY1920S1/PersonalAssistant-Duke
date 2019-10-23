package duke.logic.commands;

import duke.commons.Messages;
import duke.commons.exceptions.DukeException;
import duke.logic.commands.results.CommandResultText;
import duke.model.Model;
import duke.model.transports.Route;

public class RouteEditCommand extends Command {
    private int index;
    private String var;
    private String val;
    private static final String MESSAGE_SUCCESS = "Edited the Route!\n  ";

    /**
     * Creates a new RouteEditCommand with the given parameters.
     *
     * @param index The index of Route in RouteList.
     * @param var The variable of Route to edit.
     * @param val The value to assign
     */
    public RouteEditCommand(int index, String var, String val) {
        this.index = index;
        this.var = var;
        this.val = val;
    }

    /**
     * Executes this command on the given Route.
     *
     * @param model The model object containing information about the user.
     */
    @Override
    public CommandResultText execute(Model model) throws DukeException {
        try {
            Route route = model.getRoutes().get(index);
            switch (var.toLowerCase()) {
                case "name":
                    route.setName(val);
                    break;
                case "description":
                    route.setDescription(val);
                    break;
                default:
                    throw new DukeException(Messages.UNKNOWN_VAR);
            }

            model.save();
            return new CommandResultText(MESSAGE_SUCCESS);
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException(Messages.OUT_OF_BOUNDS);
        }
    }
}