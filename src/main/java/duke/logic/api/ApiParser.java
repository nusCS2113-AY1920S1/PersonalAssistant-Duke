package duke.logic.api;

import duke.commons.exceptions.ApiException;
import duke.commons.exceptions.OutOfBoundsException;
import duke.logic.api.requests.LocationSearchUrlRequest;
import duke.logic.api.requests.StaticMapUrlRequest;
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
 * Handles all API requests.
 */
public class ApiParser {
    private static final double STATIC_MAP_MAX_DIST = 0.04;
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
    private static final int DISTANCE_THRESHOLD = 3000;
    private static final int NEIGHBOUR_MAX_SIZE = 5;
    private static final int ROUTE_NEIGHBOUR_MAX_SIZE = 6;

    /**
     * Returns names and coordinates of location search.
     *
     * @param param The query location.
     * @return The location found.
     * @throws ApiException If there is an issue with the request.
     */
    public static Venue getLocationSearch(String param) throws ApiException {
        LocationSearchUrlRequest req = new LocationSearchUrlRequest(param);
        return req.execute();
    }

    /**
     * Gets Static Map from StaticMap API.
     *
     * @param param String formatted parameters
     * @return result The image from API
     * @throws ApiException If there is an issue with the request.
     */
    public static Image getStaticMap(String param) throws ApiException {
        StaticMapUrlRequest req = new StaticMapUrlRequest(param);
        return req.execute();
    }

    /**
     * Generates Param in String format for StaticMapUrlRequest.
     *
     * @param imageLength The length of StaticImage.
     * @param imageWidth The width of StaticImage.
     * @param zoomLvl The zoom level.
     * @param centerLat The latitude coordinates of center.
     * @param centerLong The longitude coordinates of center.
     * @param polygonRegion The polygon regions to highlight.
     * @param lineCoord The line coordinates to highlight.
     * @param pointCoord The point coordinates to show.
     * @return result The String param to parse.
     */
    public static String generateStaticMapParams(String imageLength, String imageWidth, String zoomLvl,
                     String centerLat, String centerLong, String polygonRegion, String lineCoord, String pointCoord) {
        String result = "layerchosen=default&" + "lat=" + centerLat + "&lng=" + centerLong + "&zoom=" + zoomLvl
                + "&height=" + imageLength + "&width=" + imageWidth;
        result += "&polygons=" + polygonRegion;
        result += "&lines=" + lineCoord;
        result += "&points=" + pointCoord + "&color=&fillColor=";
        return result;
    }

    /**
     * Generates parameter in String format for polygonRegion or lineCoord in StaticMap.
     *
     * @param points The ArrayList of points.
     * @param rgb The color of the region or line.
     * @return result The String param.
     */
    public static String generateStaticMapLines(ArrayList<String> points, String rgb, String lineWidth) {
        StringBuilder result = new StringBuilder();
        if (points.size() > 0) {
            result = new StringBuilder("[");
            for (String point : points) {
                result.append("[").append(point).append("],");
            }
            result = new StringBuilder(result.substring(0, result.length() - 1) + "]");

            if (!rgb.isEmpty()) {
                result.append(":").append(rgb).append(":").append(lineWidth);
            } else {
                result.append(":0,0,0:").append(lineWidth);
            }
        }
        return result.toString();
    }

    /**
     * Creates Point in String format for StaticMap.
     *
     * @param latitude The latitude.
     * @param longitude The longitude.
     * @param r The R value in RGB.
     * @param g The G value in RGB.
     * @param b The B value in RGB.
     * @param label The text label for the point.
     * @return result The String result.
     */
    public static String generateStaticMapPoint(String latitude, String longitude,
                                                String r, String g, String b, String label) {
        try {
            return "[" + latitude + "," + longitude + ",\"" + r + "," + g + "," + b + "\",\""
                    + (Character.toString(label.charAt(0))).toUpperCase() + "\"]";
        } catch (NullPointerException e) {
            return "[" + latitude + "," + longitude + ",\"" + r + "," + g + "," + b + "\",\""
                    + "\"]";
        }
    }

    /**
     * Generates an image from StaticMap API for RouteNodeNeighboursCommand.
     *
     * @param model The model object containing information about the user.
     * @param route The Route that the RouteNode belongs to.
     * @param node The RouteNode that is queried.
     * @param indexNode The index of the RouteNode in the Route.
     * @return image The image from StaticMap API.
     * @throws ApiException If the API request fails.
     * @throws OutOfBoundsException If fetching any node fails.
     */
    public static Image generateStaticMapNeighbours(Model model, Route route, RouteNode node, int indexNode)
            throws ApiException, OutOfBoundsException {
        ArrayList<String> points = generateOtherPoints(route, node, indexNode);

        String param = getLocationSearchName(node);
        Venue query = ApiParser.getLocationSearch(param);

        ArrayList<Venue> nearbyNodes = getNeighbour(model, node);

        String rgb = RED_VALUE_OTHER + "," + GREEN_VALUE_OTHER + "," + BLUE_VALUE_OTHER;

        return ApiParser.getStaticMap(ApiParser.generateStaticMapParams(DIMENSIONS, DIMENSIONS, ZOOM_LEVEL,
                String.valueOf(query.getLatitude()), String.valueOf(query.getLongitude()), "",
                generateLineParam(points, rgb), generatePointParam(route, node, nearbyNodes)));
    }

    /**
     * Returns a String in the RouteNode that is searchable by LocationSearchUrlRequest.
     *
     * @param node The RouteNode to query.
     * @return The String for LocationSearchUrlRequest.
     */
    public static String getLocationSearchName(RouteNode node) {
        if (node instanceof BusStop) {
            return ((BusStop) node).getBusCode();
        } else {
            return node.getAddress();
        }
    }

    /**
     * Gets the nearest few BusStops or TrainStations to a given RouteNode.
     *
     * @param model The model object containing information about the user.
     * @param node The RouteNode to check.
     * @return result The neighbours of the RouteNode.
     * @throws OutOfBoundsException If fetching any node fails.
     */
    public static ArrayList<Venue> getNeighbour(Model model, RouteNode node) throws OutOfBoundsException {
        try {
            ArrayList<Venue> result = new ArrayList<>();
            ArrayList<Venue> temp = new ArrayList<>();

            for (Map.Entry mapElement : model.getMap().getBusStopMap().entrySet()) {
                BusStop newNode = ((BusStop) mapElement.getValue());
                if (newNode.getDistance(node) < DISTANCE_THRESHOLD
                        && !newNode.equals(node)) {
                    temp.add(newNode);
                }
            }

            for (Map.Entry mapElement : model.getMap().getTrainMap().entrySet()) {
                TrainStation newNode = ((TrainStation) mapElement.getValue());
                if (newNode.getDistance(node) < DISTANCE_THRESHOLD && !newNode.equals(node)) {
                    temp.add(newNode);
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
    public static ArrayList<String> generateOtherPoints(Route route, RouteNode query, int indexNode) {
        ArrayList<String> points = new ArrayList<>();
        int startIndex = Math.max(0, indexNode - (ROUTE_NEIGHBOUR_MAX_SIZE / 2));
        int endIndex = Math.min(route.size() - 1, startIndex + ROUTE_NEIGHBOUR_MAX_SIZE);

        for (int i = startIndex; i <= endIndex; i++) {
            RouteNode newNode = route.getNode(i);
            if (!newNode.equals(query)) {
                points.add(newNode.getLatitude() + "," + newNode.getLongitude());
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
    public static String generateLineParam(ArrayList<String> points, String rgb)  {
        return ApiParser.generateStaticMapLines(points, rgb, LINE_WIDTH);
    }

    /**
     * Generates the point parameters of a Route.
     *
     * @param route The Route object.
     * @param query The RouteNode being shown.
     * @return result The point parameters.
     */
    public static String generatePointParam(Route route, RouteNode query, ArrayList<Venue> nearbyNodes) {
        StringBuilder result = new StringBuilder();

        int index = 1;
        for (Venue node : nearbyNodes) {
            result.append(ApiParser.generateStaticMapPoint(String.valueOf(node.getLatitude()),
                    String.valueOf(node.getLongitude()), RED_VALUE_NEIGHBOUR, GREEN_VALUE_NEIGHBOUR,
                    BLUE_VALUE_NEIGHBOUR, String.valueOf(index))).append("|");
            index++;
        }

        for (RouteNode node : route.getNodes()) {
            if (!node.equals(query) && isWithinDistance(node, query)) {
                result.append(ApiParser.generateStaticMapPoint(String.valueOf(node.getLatitude()),
                        String.valueOf(node.getLongitude()), RED_VALUE_OTHER, GREEN_VALUE_OTHER, BLUE_VALUE_OTHER,
                        node.getAddress())).append("|");
            } else {
                result.append(ApiParser.generateStaticMapPoint(String.valueOf(node.getLatitude()),
                        String.valueOf(node.getLongitude()), RED_VALUE_QUERY, GREEN_VALUE_QUERY, BLUE_VALUE_QUERY,
                        node.getAddress())).append("|");
            }
        }

        result = new StringBuilder(result.substring(0, result.length() - 1));

        return result.toString();
    }

    /**
     * Checks if a node is close enough to appear in the StaticMap image of the query.
     *
     * @param query The node being checked.
     * @param node The node in the center of the image.
     * @return Whether the node is close enough to be added.
     */
    public static boolean isWithinDistance(RouteNode query, RouteNode node) {
        return (Math.abs(node.getLatitude() - query.getLatitude()) < STATIC_MAP_MAX_DIST
                && Math.abs(node.getLongitude() - query.getLongitude()) < STATIC_MAP_MAX_DIST);
    }
}
