package duke.logic.commands;

import duke.commons.exceptions.ApiException;
import duke.commons.exceptions.OutOfBoundsException;
import duke.logic.api.ApiParser;
import duke.logic.commands.results.CommandResultImage;
import duke.model.Model;
import duke.model.locations.BusStop;
import duke.model.locations.RouteNode;
import duke.model.locations.TrainStation;
import duke.model.locations.Venue;
import duke.model.transports.Route;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Map;

/**
 * Shows nearby neighbours of a given RouteNode.
 */
public class RouteNodeNeighboursCommand extends Command {
    private static final String DIMENSIONS = "512";
    private static final String ZOOM_LEVEL = "16";
    private static final String RED_VALUE_OTHER = "255";
    private static final String GREEN_VALUE_OTHER = "122";
    private static final String BLUE_VALUE_OTHER = "0";
    private static final String RED_VALUE_QUERY = "122";
    private static final String GREEN_VALUE_QUERY = "255";
    private static final String BLUE_VALUE_QUERY = "0";
    private static final String RED_VALUE_NEIGHBOUR = "0";
    private static final String GREEN_VALUE_NEIGHBOUR = "0";
    private static final String BLUE_VALUE_NEIGHBOUR = "0";
    private static final String LINE_WIDTH = "2";
    private static final String MESSAGE_SUCCESS = "Here are some Nodes that are close to this:\n";
    private static final int DISTANCE_THRESHOLD = 3000;
    private static final int NEIGHBOUR_MAX_SIZE = 5;
    private int indexRoute;
    private int indexNode;

    /**
     * Creates a new RouteNodeNeighboursCommand with the given parameters.
     *
     * @param indexRoute The index of Route in RouteList.
     * @param indexNode The index of Node in RouteList.
     */
    public RouteNodeNeighboursCommand(int indexRoute, int indexNode) {
        this.indexRoute = indexRoute;
        this.indexNode = indexNode;
    }

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param model The model object containing information about the user.
     * @return The CommandResultText.
     * @throws ApiException If the api call fails.
     * @throws OutOfBoundsException If the index is out of bounds.
     */
    @Override
    public CommandResultImage execute(Model model) throws ApiException, OutOfBoundsException {
        ArrayList<Venue> result = getNeighbour(model);
        Image image = getImage(model, result);

        return new CommandResultImage(image, MESSAGE_SUCCESS, result);
    }

    private Image getImage(Model model, ArrayList<Venue> nearbyNodes) throws ApiException {
        Route route = model.getRoutes().get(indexRoute);
        RouteNode node = model.getRoutes().get(indexRoute).getNode(indexNode);
        String rgb = RED_VALUE_OTHER + "," + GREEN_VALUE_OTHER + "," + BLUE_VALUE_OTHER;

        String param;
        if (node instanceof BusStop) {
            param = ((BusStop) node).getBusCode();
        } else {
            param = node.getAddress();
        }

        Venue query = ApiParser.getLocationSearch(param);
        ArrayList<String> points = generateOtherPoints(route, node);

        Image image = ApiParser.getStaticMap(ApiParser.generateStaticMapParams(DIMENSIONS, DIMENSIONS, ZOOM_LEVEL,
                String.valueOf(query.getLatitude()), String.valueOf(query.getLongitude()), "",
                generateLineParam(points, rgb), generatePointParam(route, node, nearbyNodes)));

        return image;
    }

    private ArrayList<Venue> getNeighbour(Model model) throws OutOfBoundsException {
        try {
            ArrayList<Venue> result = new ArrayList<>();
            ArrayList<Venue> temp = new ArrayList<>();
            RouteNode node = model.getRoutes().get(indexRoute).getNode(indexNode);
            for (Map.Entry mapElement : model.getMap().getBusStopMap().entrySet()) {
                if (node instanceof BusStop && ((BusStop) mapElement.getValue()).getDistance(node) < DISTANCE_THRESHOLD
                        && !((BusStop) mapElement.getValue()).equals(node)) {
                    temp.add((BusStop) mapElement.getValue());
                }
            }

            for (Map.Entry mapElement : model.getMap().getTrainMap().entrySet()) {
                if (((TrainStation) mapElement.getValue()).getDistance(node) < DISTANCE_THRESHOLD
                        && !((TrainStation) mapElement.getValue()).equals(node)) {
                    temp.add((TrainStation) mapElement.getValue());
                }
            }

            temp.sort((Venue o1, Venue o2) -> (int) (o1.getDistance(node) - o2.getDistance(node)));
            for (int i = 0; i < NEIGHBOUR_MAX_SIZE && i < temp.size(); i++) {
                result.add(temp.get(i));
            }

            return result;
        } catch (IndexOutOfBoundsException e) {
            throw new OutOfBoundsException();
        }
    }

    /**
     * Generates 6 other nodes beside the selected one in the Route as an ArrayList.
     *
     * @param route The Route object.
     * @param query The original RouteNode being queried.
     * @return points The ArrayList of points.
     */
    private ArrayList<String> generateOtherPoints(Route route, RouteNode query) {
        ArrayList<String> points = new ArrayList<>();
        int startIndex = Math.max(0, indexNode - 3);
        int endIndex = Math.min(route.size() - 1, startIndex + 6);

        for (int i = startIndex; i < endIndex; i++) {
            if (!(route.getNode(i)).equals(query)) {
                points.add(route.getNode(i).getLatitude() + "," + route.getNode(i).getLongitude());

            }
        }

        return points;
    }

    /**
     * Generates the line parameters for the StaticMap request.
     *
     * @param points The ArrayList of points.
     * @param rgb The RGB value.
     * @return The line parameters.
     */
    private String generateLineParam(ArrayList<String> points, String rgb)  {
        return ApiParser.generateStaticMapLines(points, rgb, LINE_WIDTH);
    }

    /**
     * Generates the point parameters of a Route.
     *
     * @param route The Route object.
     * @param query The RouteNode being shown.
     * @return result The point parameters.
     */
    private String generatePointParam(Route route, RouteNode query, ArrayList<Venue> nearbyNodes) {
        String result = "";

        int index = 1;
        for (Venue node : nearbyNodes) {
            result += ApiParser.generateStaticMapPoint(String.valueOf(node.getLatitude()),
                    String.valueOf(node.getLongitude()), RED_VALUE_NEIGHBOUR, GREEN_VALUE_NEIGHBOUR,
                    BLUE_VALUE_NEIGHBOUR, String.valueOf(index)) + "|";
            index++;
        }

        for (RouteNode node : route.getNodes()) {
            if (!node.equals(query) && isWithinDistance(node, query)) {
                result += ApiParser.generateStaticMapPoint(String.valueOf(node.getLatitude()),
                        String.valueOf(node.getLongitude()), RED_VALUE_OTHER, GREEN_VALUE_OTHER, BLUE_VALUE_OTHER,
                        node.getAddress()) + "|";
            } else {
                result += ApiParser.generateStaticMapPoint(String.valueOf(node.getLatitude()),
                        String.valueOf(node.getLongitude()), RED_VALUE_QUERY, GREEN_VALUE_QUERY, BLUE_VALUE_QUERY,
                        node.getAddress()) + "|";
            }
        }

        result = result.substring(0, result.length() - 1);

        return result;
    }

    /**
     * Checks if a node is close enough to appear in the StaticMap image of the query.
     *
     * @param query The node being checked.
     * @param node The node in the center of the image.
     * @return Whether the node is close enough to be added.
     */
    private boolean isWithinDistance(RouteNode query, RouteNode node) {
        return (node.getLatitude() - query.getLatitude()) < 0.04
                && (node.getLongitude() - query.getLongitude()) < 0.04;
    }
}
