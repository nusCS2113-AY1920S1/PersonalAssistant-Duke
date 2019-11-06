package sgtravel.logic.commands.results;

import sgtravel.model.Event;
import sgtravel.model.lists.RouteList;
import sgtravel.model.lists.EventList;
import sgtravel.model.locations.BusStop;
import sgtravel.model.locations.CustomNode;
import sgtravel.model.locations.TrainStation;
import sgtravel.model.profile.ProfileCard;
import sgtravel.model.locations.Venue;
import sgtravel.model.transports.Route;
import sgtravel.model.locations.RouteNode;

import java.util.ArrayList;

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
        int index = 1;
        message = "Here is the information of Routes:\n";
        for (Route route: routes.getRoutes()) {
            message += "(" + index + ") " + route.getName() + "\n" + route.getDescription() + "\n";
            index++;
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
                message += node.getAddress() + " " + node.getDescription() + "\n";
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

    /**
     * Alternative constructor that helps to create text for an ArrayList of Venue.
     *
     * @param message The message to show at the top.
     * @param venues The ArrayList of Venue.
     */
    public CommandResultText(String message, ArrayList<Venue> venues) {
        String result = message;
        for (Venue venue : venues) {
            if (venue instanceof BusStop) {
                result += ((BusStop) venue).getBusCode() + " " + venue.getAddress() + "\n";
            } else {
                result += venue.getAddress() + "\n";
            }
        }
        this.message = result;
    }

    /**
     * Alternative constructor that helps to create text for a list of events.
     */
    public CommandResultText(ProfileCard profileCard) {
        message = "PROFILE:\n\n";
        message += "Name: " + profileCard.getPersonName() + "\n";
        message += "Age: " + profileCard.getAge() + "\n";
        message += "Likes:\n";
        String[] category = {"sports", "entertainment", "arts", "lifestyle"};
        int i = 0;
        for (Boolean setting : profileCard.getPreference()) {
            message += (category[i] + " : " + setting + "\n");
            i += 1;
        }
    }
}
