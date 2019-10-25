package duke.logic.commands;

import duke.commons.exceptions.CorruptedFileException;
import duke.commons.exceptions.FileNotSavedException;
import duke.commons.exceptions.QueryOutOfBoundsException;
import duke.commons.exceptions.UnknownFieldException;
import duke.logic.commands.results.CommandResultText;
import duke.model.Model;
import duke.model.transports.Route;

/**
 * Class representing a command to edit a Route in RouteList.
 */
public class RouteEditCommand extends Command {
    private int index;
    private String field;
    private String newValue;
    private static final String MESSAGE_SUCCESS = "Edited the Route!\n";

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
     * @throws CorruptedFileException If the file is corrupted.
     * @throws FileNotSavedException If the file is not saved.
     * @throws UnknownFieldException If the queried field is not valid.
     * @throws QueryOutOfBoundsException If the query is out of bounds.
     */
    @Override
    public CommandResultText execute(Model model) throws CorruptedFileException, FileNotSavedException,
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
            throw new QueryOutOfBoundsException("ROUTE");
        }
    }
}
