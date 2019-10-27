package duke.logic.commands.results;

import duke.model.Event;
import duke.model.lists.RouteList;
import duke.model.lists.EventList;
import duke.model.locations.BusStop;
import duke.model.locations.CustomNode;
import duke.model.locations.TrainStation;
import duke.model.transports.Route;
import duke.model.locations.RouteNode;

/**
 * Defines the result of various commands as a string.
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
     * Alternative constructor that helps to create text for a list of events.
     */
    public CommandResultText(EventList events) {
        message = "Here are the list of events:\n";
        int i = 1;
        for (Event t : events) {
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
        message += (route.getDescription()).replace("/", "\n") + "\n";

        int index = 1;
        for (RouteNode node: route.getNodes()) {
            message += "(" + index + ") ";
            if (node instanceof BusStop) {
                message += ((BusStop) node).getBusCode() + " " + node.getAddress() + "\n";
            } else if (node instanceof TrainStation) {
                message += ((TrainStation) node).getTrainCodes() + " " + node.getDescription() + "\n";
            } else if (node instanceof CustomNode)  {
                message += node.getAddress() + "\n";
            }
            index++;
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
