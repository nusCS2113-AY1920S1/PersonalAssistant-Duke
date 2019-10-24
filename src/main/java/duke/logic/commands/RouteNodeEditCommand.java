package duke.logic.commands;

import duke.commons.Messages;
import duke.commons.enumerations.Constraint;
import duke.commons.exceptions.DukeException;
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
     */
    @Override
    public CommandResultText execute(Model model) throws DukeException {
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
                throw new DukeException(Messages.ERROR_FIELD_UNKNOWN);
            }
            model.save();
            return new CommandResultText(MESSAGE_SUCCESS);
        } catch (NumberFormatException e) {
            throw new DukeException(Messages.PROMPT_NOT_INT);
        } catch (IllegalArgumentException e) {
            throw new DukeException(Messages.ERROR_CONSTRAINT_UNKNOWN);
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException(Messages.ERROR_INDEX_OUT_OF_BOUNDS);
        }
    }
}
