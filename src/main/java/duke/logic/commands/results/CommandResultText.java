package duke.logic.commands.results;

import duke.model.lists.RouteList;
import duke.model.lists.TaskList;
import duke.model.locations.BusStop;
import duke.model.locations.TrainStation;
import duke.model.transports.Route;
import duke.model.locations.RouteNode;
import duke.model.Task;

/**
 * Class representing a text-based CommandResult.
 */
public class CommandResultText extends CommandResult {
    /**
     * Constructs a basic CommandResultImage object.
     *
     * @param message Message for ui to display.
     */
    public CommandResultText(String message) {
        this.message = message;
    }

    /**
     * Alternative constructor that helps to create text for a list of tasks.
     */
    public CommandResultText(TaskList tasks) {
        message = "Here are the list of tasks:\n";
        int i = 1;
        for (Task t : tasks) {
            message += (i + ". " + t + "\n");
            i += 1;
        }
    }

    /**
     * Alternative constructor that helps to create text for a list of Routes.
     */
    public CommandResultText(RouteList routes) {
        message = "Here is the information of Routes:\n";
        for (Route route: routes.getRoutes()) {
            message += route.getDescription() + "\n";
        }
    }

    /**
     * Alternative constructor that helps to create text for a Route.
     */
    public CommandResultText(Route route) {
        message = "Here is the information of the Route:\n" + route.getName() + "\n";
        for (RouteNode node: route.getNodes()) {
            if (node instanceof BusStop) {
                message += ((BusStop) node).getBusCode() + " ";
            } else if (node instanceof TrainStation) {
                message += ((TrainStation) node).getTrainCodes() + " ";
            }
            message += node.getAddress() + "\n";
        }
    }

    /**
     * Alternative constructor that helps to create text for a Route Node.
     */
    public CommandResultText(RouteNode node) {
        message = "Here is the information of the ";
        if (node instanceof BusStop) {
            message += "Bus Stop:\n" + ((BusStop) node).getBusCode() + "\n";
        } else if (node instanceof TrainStation) {
            message += "Train Station:\n" + ((TrainStation) node).getTrainCodes() + "\n";
        }

        message +=  node.getAddress() + "\n" + node.getDescription() + "\n"
                + "(" + node.getType().toString() + ", " + node.getCoordinate() + ")";
    }
}
