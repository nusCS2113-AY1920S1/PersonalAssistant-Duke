package duke.model.locations;

import duke.commons.Messages;
import duke.commons.enumerations.Constraint;
import duke.commons.exceptions.DukeException;

import java.util.ArrayList;

public class Route {
    private ArrayList<RouteNode> nodes;
    private String name;
    private String description;

    /**
     * Constructs an empty route object.
     * @param name The name of the route.
     * @param description The description of the route.
     */
    public Route(String name, String description) {
        this.nodes = new ArrayList<RouteNode>();
        this.name = name;
        this.description = description;
    }

    /**
     * Alternative constructor with predefined nodes.
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
     * Adds a new node to the route at a given index.
     * @param newNode The new node to add.
     * @param index The index of the node to add to.
     */
    public void addNode(RouteNode newNode, int index) throws DukeException {
        for (RouteNode node: nodes) {
            if (node.getAddress().equals(newNode.getAddress())) {
                throw new DukeException(Messages.DUPLICATED_ROUTENODE);
            }
        }

        if (index == 0) {
            nodes.add(newNode);
        } else {
            nodes.add(index - 1, newNode);
        }
    }

    /**
     * Alternate method to add a node at the end of the Route.
     * @param newNode The new node to add.
     */
    public void addNode(RouteNode newNode) throws DukeException {
        for (RouteNode node: nodes) {
            if (node.getAddress().equals(newNode.getAddress())) {
                throw new DukeException(Messages.DUPLICATED_ROUTENODE);
            }
        }

        nodes.add(newNode);
    }

    /**
     * Gets the node at index - 1.
     * @param index The index of node.
     * @return node The node at index - 1.
     * @throws DukeException The exception when index is out of bounds.
     */
    public RouteNode getNode(int index) throws DukeException {
        try {
            return nodes.get(index - 1);
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException(Messages.OUT_OF_BOUNDS);
        }
    }

    /**
     * Fetches a node with the given name.
     * @param name The query name.
     * @return node The queried node.
     * @throws DukeException The exception when nothing is found.
     */
    public RouteNode fetchNode(String name) throws DukeException {
        for (RouteNode node: nodes) {
            if (node.getAddress().equals(name.toLowerCase())) {
                return node;
            }
        }

        throw new DukeException(Messages.DATA_NULL);
    }

    /**
     * Gets the arraylist of Route Nodes.
     * @return nodes The arrayList of Route Nodes.
     */
    public ArrayList<RouteNode> getNodes() {
        return nodes;
    }

    /**
     * Gets the starting node of the route.
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

    public void remove(int index) throws IndexOutOfBoundsException {
        nodes.remove(index);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns true if both routes are the same.
     */
    public boolean isSameRoute(Route otherRoute) {
        if (otherRoute == this) {
            return true;
        }

        return otherRoute != null && otherRoute.getDescription().equals(getDescription());
    }
}
