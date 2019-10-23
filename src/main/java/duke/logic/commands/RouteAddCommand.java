package duke.logic.commands;

import duke.commons.exceptions.DukeException;
import duke.logic.commands.results.CommandResultText;
import duke.model.Model;
import duke.model.lists.RouteList;
import duke.model.transports.Route;

public class RouteAddCommand extends Command {
    private String name;
    private static final String MESSAGE_ADDITION = "Got it. I've added this route:\n  ";

    /**
     * Creates a new RouteAddCommand with the given name.
     *
     * @param name The index of the task.
     */
    public RouteAddCommand(String name) {
        this.name = name;
    }

    /**
     * Executes this command on the given Route List and user interface.
     *
     * @param model The model object containing information about the user.
     */
    @Override
    public CommandResultText execute(Model model) throws DukeException {
        RouteList routes = model.getRoutes();
        routes.add(new Route(name, ""));
        model.save();
        return new CommandResultText(MESSAGE_ADDITION + name);
    }
}
