package duke.logic.commands;

import duke.commons.Messages;
import duke.commons.exceptions.DukeException;
import duke.logic.commands.results.CommandResultText;
import duke.model.Model;
import duke.model.lists.RouteList;

public class RouteDeleteCommand extends Command {
    private int index;
    private String name;
    private static final String MESSAGE_DELETION = "Got it. I've deleted this Route:\n  ";

    /**
     * Deletes a Route at the given index in Route List.
     *
     * @param index The index of the Route.
     */
    public RouteDeleteCommand(int index) {
        this.index = index;
    }

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param model The model object containing information about the user.
     */
    @Override
    public CommandResultText execute(Model model) throws DukeException {
        try {
            RouteList routes = model.getRoutes();
            name = routes.get(index).getName();
            routes.remove(index);
            model.save();
            return new CommandResultText(MESSAGE_DELETION + name);
        } catch (IllegalArgumentException e) {
            throw new DukeException(Messages.OUT_OF_BOUNDS);
        }
    }
}