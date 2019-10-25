package duke.logic.commands;

import duke.commons.enumerations.Constraint;
import duke.commons.exceptions.CorruptedFileException;
import duke.commons.exceptions.FileNotSavedException;
import duke.commons.exceptions.InputNotIntegerException;
import duke.commons.exceptions.QueryOutOfBoundsException;
import duke.commons.exceptions.UnknownConstraintException;
import duke.commons.exceptions.UnknownFieldException;
import duke.logic.commands.results.CommandResultText;
import duke.model.Model;
import duke.model.locations.RouteNode;

/**
 * Class representing a command to edit a RouteNode in RouteList.
 */
public class RouteNodeEditCommand extends Command {
    private int indexRoute;
    private int indexNode;
    private String var;
    private String val;
    private static final String MESSAGE_SUCCESS = "Edited the Route Node!\n  ";

    /**
     * Creates a new RouteNodeEditCommand with the given parameters.
     *
     * @param indexRoute The index of Route in RouteList.
     * @param indexNode The index of Node in RouteList.
     * @param var The variable of Route to edit.
     * @param val The value to assign
     */
    public RouteNodeEditCommand(int indexRoute, int indexNode, String var, String val) {
        this.indexRoute = indexRoute;
        this.indexNode = indexNode;
        this.var = var;
        this.val = val;
    }

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param model The model object containing information about the user.
     * @return The CommandResultText.
     * @throws CorruptedFileException If the file is corrupted.
     * @throws FileNotSavedException If the file is not saved.
     * @throws InputNotIntegerException If the input is not an integer.
     * @throws QueryOutOfBoundsException If the query is out of bounds.
     * @throws UnknownFieldException If the field is unknown.
     * @throws UnknownConstraintException If the constraint is unknown.
     */
    @Override
    public CommandResultText execute(Model model) throws CorruptedFileException, FileNotSavedException,
            InputNotIntegerException, QueryOutOfBoundsException, UnknownFieldException, UnknownConstraintException {
        try {
            RouteNode node = model.getRoutes().get(indexRoute).getNode(indexNode);
            switch (var.toLowerCase()) {
            case "address":
                node.setAddress(val);
                break;
            case "description":
                node.setDescription(val);
                break;
            case "type":
                node.setType(Constraint.valueOf(val));
                break;
            case "latitude":
                node.setLatitude(Integer.parseInt(val));
                break;
            case "longitude":
                node.setLongitude(Integer.parseInt(val));
                break;
            default:
                throw new UnknownFieldException();
            }
            model.save();
            return new CommandResultText(MESSAGE_SUCCESS);
        } catch (NumberFormatException e) {
            throw new InputNotIntegerException();
        } catch (IllegalArgumentException e) {
            throw new UnknownConstraintException();
        } catch (IndexOutOfBoundsException e) {
            throw new QueryOutOfBoundsException("ROUTE_NODE");
        }
    }
}
