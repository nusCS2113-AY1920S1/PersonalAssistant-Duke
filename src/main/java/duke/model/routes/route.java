package duke.model.routes;

import duke.commons.Messages;
import duke.commons.enumerations.Constraint;
import duke.commons.exceptions.DukeException;

import java.util.ArrayList;

public class Route {
    private Constraint type;
    private ArrayList<RouteNode> nodes;
    private String name;
    private String description;

    /**
     * Constructs an empty route object.
     * @param type The type of the route.
     * @param name The name of the route.
     * @param description The description of the route.
     */
    public Route(Constraint type, String name, String description) {
        this.type = type;
        this.nodes = new ArrayList<RouteNode>();
        this.name = name;
        this.description = description;
    }

    /**
     * Alternative constructor with predefined nodes.
     * @param type The type of the route.
     * @param nodes The nodes of the route.
     * @param name The name of the route.
     * @param description The description of the route.
     */
    public Route(Constraint type, ArrayList<RouteNode> nodes, String name, String description) {
        this.type = type;
        this.nodes = nodes;
        this.name = name;
        this.description = description;
    }

    /**
     * Adds a new node to the route at a given index.
     * @param type The constraint of the node.
     * @param latitude The latitude of the node.
     * @param longitude The longitude of the node.
     * @param name The name of the node.
     * @param description The description of the node.
     * @param index The index of the node to add to.
     */
    public void addNode(Constraint type, double latitude, double longitude, String name,
                        String description, int index) throws DukeException {
        RouteNode newNode = new RouteNode(type, name, description, latitude, longitude);
        for (RouteNode node: nodes) {
            if (node.getName().equals(name)) {
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
            if (node.getName().equals(name.toLowerCase())) {
                return node;
            }
        }

        throw new DukeException(Messages.DATA_NULL);
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

    public Constraint getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
