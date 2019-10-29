package duke.logic;

import duke.commons.Messages;
import duke.commons.exceptions.DukeException;
import duke.commons.exceptions.QueryOutOfBoundsException;
import duke.model.lists.RouteList;
import duke.model.transports.Route;

/**
 * Handles manipulation of Routes when activated.
 */
public class RouteManager {
    private boolean isActivated = false;
    private int routeIndex = -1;
    private int nodeIndex = -1;
    private RouteList routes;

    /**
     * Constructs the Route Manager.
     *
     * @param routes The RouteList of the model.
     */
    public RouteManager(RouteList routes) {
        this.routes = routes;
    }

    /**
     * Selects and sets a Route for the RouteManager to use.
     *
     * @param index The index of the Route to select.
     * @throws QueryOutOfBoundsException If the index is out of bounds.
     */
    public void setRoute(int index) throws QueryOutOfBoundsException {
        if (index >= 0 && index < routes.getRoutes().size()) {
            routeIndex = index + 1;
            nodeIndex = -1;
            return;
        }
        throw new QueryOutOfBoundsException(String.valueOf(index));
    }

    /**
     * Selects and sets a Node for the RouteManager to use.
     *
     * @param index The index of the Node to select.
     * @throws QueryOutOfBoundsException If the index is out of bounds.
     */
    public void setNode(int index) throws DukeException {
        if (routeIndex == -1) {
            throw new DukeException("Route not selected!");
        }
        if (index >= 0 && index < routes.getRoutes().get(routeIndex - 1).getNumNodes()) {
            nodeIndex = index + 1;
            return;
        }
        throw new QueryOutOfBoundsException(String.valueOf(index));
    }

    /**
     * Resets the Route and returns to selecting the Route instead.
     */
    public void resetRoute() {
        routeIndex = -1;
    }

    /**
     * Resets the RouteNode and returns the Route.
     */
    public void resetNode() {
        nodeIndex = -1;
    }

    /**
     * Turns on the Route Manager.
     */
    public void turnOn() {
        isActivated = true;
    }

    /**
     * Turns off the Route Manager.
     */
    public void turnOff() {
        isActivated = false;
    }

    /**
     * Gets the status of the Route Manager.
     *
     * @return isActivated Whether the Route Manager is activated or not.
     */
    public boolean isActivated() {
        return isActivated;
    }

    /**
     * Returns the Route index.
     *
     * @return routeIndex The Route index, or -1 if not selected.
     */
    public int getRouteIndex() {
        return routeIndex;
    }

    /**
     * Returns the Node index.
     *
     * @return nodeIndex The Node index, or -1 if not selected.
     */
    public int getNodeIndex() {
        return nodeIndex;
    }

    /**
     * Gets the status information of the RouteList.
     *
     * @return The status information of the RouteList
     */
    public String getRouteListStatus() {
        String message = "";
        int index = 1;
        if (routes.size() > 0) {
            message = Messages.ROUTEMANAGER_STATUS_ROUTELIST_STARTER;
            for (Route route : routes) {
                message += "(" + index + ") " + route.getName() + "\n";
                index++;
            }
            message += Messages.ROUTEMANAGER_STATUS_ROUTELIST_END;
        } else {
            message = Messages.ROUTEMANAGER_STATUS_ROUTELIST_EMPTY;
        }
        return message;
    }

    /**
     * Gets the welcome message that displays when the Route Manager is activated.
     *
     * @return The welcome message.
     */
    public String getWelcomeMessage() {
        return Messages.ROUTEMANAGER_WELCOME_MESSAGE + getRouteListStatus();
    }

    /**
     * Gets the prefix to add onto a conversation starter for the Route Manager conversations.
     */
    public String getConversationPrefix() {
        if (nodeIndex == -1) {
            return Messages.ROUTEMANAGER_CONVERSATION_PREFIX_ROUTE;
        } else {
            return Messages.ROUTEMANAGER_CONVERSATION_PREFIX_ROUTENODE;
        }
    }

    /**
     * Returns the size of the Route being selected.
     *
     * @return The size of the Rotue being selected.
     */
    public int getRouteSize() {
        return routes.get(routeIndex - 1).getNumNodes();
    }

    /**
     * Returns the appropriate help message in context.
     *
     * @return The appropriate help message.
     */
    public String getHelpMessage() {
        if (routeIndex == -1) {
            return Messages.ROUTEMANAGER_HELP_GENERAL;
        } else if (nodeIndex == -1) {
            return Messages.ROUTEMANAGER_HELP_ROUTE;
        } else {
            return Messages.ROUTEMANAGER_HELP_ROUTENODE;
        }
    }
}
