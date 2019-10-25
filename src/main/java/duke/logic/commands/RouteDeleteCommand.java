package duke.logic.commands;

import duke.commons.exceptions.CorruptedFileException;
import duke.commons.exceptions.FileNotSavedException;
import duke.commons.exceptions.QueryOutOfBoundsException;
import duke.logic.commands.results.CommandResultText;
import duke.model.Model;
import duke.model.lists.RouteList;

/**
 * Class representing a command to delete a Route from RouteList.
 */
public class RouteDeleteCommand extends Command {
    private int index;
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
     * @return The CommandResultText.
     * @throws CorruptedFileException If the file is corrupted.
     * @throws FileNotSavedException If the file cannot be saved.
     * @throws QueryOutOfBoundsException If the query is out of bounds.
     */
    @Override
    public CommandResultText execute(Model model) throws CorruptedFileException, FileNotSavedException,
            QueryOutOfBoundsException {
        try {
            RouteList routes = model.getRoutes();
            String routeName = routes.get(index).getName();
            routes.remove(index);
            model.save();
            return new CommandResultText(MESSAGE_DELETION + routeName);
        } catch (IndexOutOfBoundsException e) {
            throw new QueryOutOfBoundsException("ROUTE");
        }
    }
}
