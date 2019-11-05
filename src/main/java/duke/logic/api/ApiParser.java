package duke.logic.api;

import duke.commons.exceptions.ApiException;
import duke.logic.api.requests.LocationSearchUrlRequest;
import duke.logic.api.requests.StaticMapUrlRequest;
import duke.model.locations.Venue;
import javafx.scene.image.Image;

import java.util.ArrayList;

/**
 * Handles all API requests.
 */
public class ApiParser {

    private static final int MAX_BUS_STOP_DATA_SIZE = 5500;
    private static final int DATA_SIZE_PER_REQUEST = 500;

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
     * Create polygonRegion or lineCoord in String format for StaticMap.
     *
     * @param latitude The latitude.
     * @param longitude The longitude.
     * @return result The String result.
     */
    public static String generateStaticMapArea(String latitude, String longitude) {
        return "[" +  latitude + "," + longitude + "]";
    }

    /**
     * Generate parameters in String format for polygonRegion or lineCoord in StaticMap.
     *
     * @param points The ArrayList of points in format X,Y .
     * @param rgb The color of the region, in format r,g,b .
     * @return result The String param.
     */
    public static String generateStaticMapPolygon(ArrayList<String> points, String rgb) {
        String result = "[";
        for (String point: points) {
            result += "[" + point + "]";
            if (!point.equals(points.get(points.size() - 1))) {
                result += ",";
            }
        }

        if (!rgb.isEmpty()) {
            result += ":" + rgb + ":2";
        } else {
            result += ":0,0,0:2";
        }

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
        String result = "";
        if (points.size() > 0) {
            result = "[";
            for (String point : points) {
                result += "[" + point + "]";
                result += ",";
            }
            result = result.substring(0, result.length() - 1) + "]";

            if (!rgb.isEmpty()) {
                result += ":" + rgb + ":" + lineWidth;
            } else {
                result += ":0,0,0:" + lineWidth;
            }
        }
        return result;
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
     * Generates parameters in String format for polygonRegion or lineCoord in StaticMap.
     *
     * @param points The ArrayList of points.
     * @return result The String param.
     */
    public static String generateStaticMapPoints(ArrayList<String> points) {
        String result = "[";
        for (String point: points) {
            result += "[" + point + "]" + "|";
        }
        result = result.substring(0, result.length() - 1);

        return result;
    }
}
