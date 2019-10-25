package duke.model.transports;

import duke.commons.exceptions.NullResultException;
import duke.commons.exceptions.QueryOutOfBoundsException;
import duke.commons.exceptions.RouteNodeDuplicateException;
import duke.model.locations.BusStop;
import duke.model.locations.RouteNode;
import duke.model.locations.TrainStation;

import java.util.ArrayList;

/**
<<<<<<< HEAD
 * Class representing a route.
=======
 * Represents a route between 2 locations as a list of route nodes.
>>>>>>> 20921c7839efb1481af98f3b5c7e0465ff5bb57e
 */
public class Route {
    private ArrayList<RouteNode> nodes;
    private String name;
    private String description;

    /**
     * Constructs an empty route object.
     *
     * @param name The name of the route.
     * @param description The description of the route.
     */
    public Route(String name, String description) {
        this.nodes = new ArrayList<>();
        this.name = name;
        this.description = description;
    }

    /**
     * Alternative constructor with predefined nodes.
     *
     * @param nodes The nodes of the route.
     * @param name The name of the route.
     * @param description The description of the route.
     */
    public Route(ArrayList<RouteNode> nodes, String name, String description) {
        this.nodes = nodes;
        this.name = name;
        this.description = description;
    }

    /**
     * Gets the node at index.
     *
     * @param index The index of node.
     * @return node The node at index.
     * @throws IndexOutOfBoundsException The exception when index is out of bounds.
     */
    public RouteNode getNode(int index) throws IndexOutOfBoundsException {
        try {
            return nodes.get(index);
        } catch (IndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     * Gets the Arraylist of Route Nodes.
     *
     * @return nodes The ArrayList of Route Nodes.
     */
    public ArrayList<RouteNode> getNodes() {
        return nodes;
    }

    /**
     * Gets the starting node of the route.
     *
     * @return node The start node.
     */
    public RouteNode getStartNode() {
        if (nodes.size() > 0) {
            return nodes.get(0);
        } else {
            return null;
        }
    }

    /**
     * Gets the starting node of the route.
     * @return node The start node.
     */
    public RouteNode getEndNode() {
        if (nodes.size() > 0) {
            return nodes.get(nodes.size() - 1);
        } else {
            return null;
        }
    }

    public int getNumNodes() {
        return nodes.size();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void remove(int index) throws IndexOutOfBoundsException {
        nodes.remove(index);
    }

    /**
     * Adds a new node to the route at a given index.
     *
     * @param newNode The new node to add.
     * @param index The index of the node to add to.
     * @exception QueryOutOfBoundsException If the index is out of bounds.
     * @exception RouteNodeDuplicateException If the route is a duplicate.
     */
    public void addNode(RouteNode newNode, int index) throws RouteNodeDuplicateException, QueryOutOfBoundsException {
        if (index >= 0 && index < nodes.size()) {
            for (RouteNode node : nodes) {
                if (node instanceof BusStop && newNode instanceof BusStop
                        && ((BusStop) node).getBusCode().equals(((BusStop) newNode).getBusCode())) {
                    throw new RouteNodeDuplicateException();
                }
                if (node instanceof TrainStation && newNode instanceof TrainStation
                        && ((TrainStation) node).getTrainCodes().equals(((TrainStation) newNode).getTrainCodes())) {
                    throw new RouteNodeDuplicateException();
                }
            }
            nodes.add(index, newNode);
            return;
        }

        throw new QueryOutOfBoundsException("NODE");
    }

    /**
     * Alternate method to add a node at the end of the Route.
     *
     * @param newNode The new node to add.
     * @exception RouteNodeDuplicateException If the route is a duplicate.
     */
    public void addNode(RouteNode newNode) throws RouteNodeDuplicateException {
        for (RouteNode node: nodes) {
            if (node instanceof BusStop && newNode instanceof BusStop
                    && ((BusStop) node).getBusCode().equals(((BusStop) newNode).getBusCode())) {
                throw new RouteNodeDuplicateException();
            }
            if (node instanceof TrainStation && newNode instanceof TrainStation
                    && ((TrainStation) node).getTrainCodes().equals(((TrainStation) newNode).getTrainCodes())) {
                throw new RouteNodeDuplicateException();
            }
        }

        nodes.add(newNode);
    }

    /**
     * Fetches a node with the given name.
     *
     * @param name The query name.
     * @return node The queried node.
     * @throws NullResultException The exception when nothing is found.
     */
    public RouteNode fetchNode(String name) throws NullResultException {
        for (RouteNode node: nodes) {
            if (node.getAddress().equals(name.toLowerCase())) {
                return node;
            }
        }

        throw new NullResultException();
    }

    /**
     * Returns true if both routes are the same.
     */
    public boolean isSameRoute(Route otherRoute) {
        if (otherRoute == this) {
            return true;
        }

        return otherRoute != null && otherRoute.getName().equals(getName());
    }
}
