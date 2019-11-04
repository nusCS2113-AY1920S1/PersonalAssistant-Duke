package duke.logic.commands;

import duke.commons.exceptions.FileNotSavedException;
import duke.commons.exceptions.QueryOutOfBoundsException;
import duke.commons.exceptions.UnknownFieldException;
import duke.logic.commands.results.CommandResultText;
import duke.model.Model;
import duke.model.transports.Route;

/**
 * Edits a Route in RouteList.
 */
public class RouteEditCommand extends Command {
    private static final String MESSAGE_SUCCESS = "Edited the Route!\n";
    private int index;
    private String field;
    private String newValue;

    /**
     * Creates a new RouteEditCommand with the given parameters.
     *
     * @param index The index of Route in RouteList.
     * @param field The variable of Route to edit.
     * @param newValue The value to assign
     */
    public RouteEditCommand(int index, String field, String newValue) {
        this.index = index;
        this.field = field;
        this.newValue = newValue;
    }

    /**
     * Executes this command on the given Route.
     *
     * @param model The model object containing information about the user.
     * @return The CommandResultText.
     * @throws FileNotSavedException If the file is not saved.
     * @throws UnknownFieldException If the queried field is not valid.
     * @throws QueryOutOfBoundsException If the query is out of bounds.
     */
    @Override
    public CommandResultText execute(Model model) throws FileNotSavedException,
            UnknownFieldException, QueryOutOfBoundsException {
        try {
            Route route = model.getRoutes().get(index);
            switch (field.toLowerCase()) {
            case "name":
                route.setName(newValue);
                break;
            case "description":
                route.setDescription(newValue);
                break;
            default:
                throw new UnknownFieldException();
            }

            model.save();
            return new CommandResultText(MESSAGE_SUCCESS);
        } catch (IndexOutOfBoundsException e) {
            throw new QueryOutOfBoundsException();
        }
    }
}
