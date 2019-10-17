package duke.logic.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import duke.commons.Messages;
import duke.commons.exceptions.DukeApiException;
import duke.logic.api.requests.LocationSearchUrlRequest;
import duke.commons.exceptions.DukeException;
import duke.logic.api.requests.DataMallHttpRequest;
import duke.logic.api.requests.StaticMapUrlRequest;
import duke.model.locations.BusStop;
import duke.model.locations.Venue;
import duke.model.transports.BusService;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class to handle all API requests.
 */
public class ApiParser {

    /**
     * Return names and coordinates of location search.
     *
     * @param param The query
     * @return result The locations found
     */
    public static Venue getLocationSearch(String param) throws DukeException {
        try {
            LocationSearchUrlRequest req = new LocationSearchUrlRequest(param);
            JsonObject jsonRes = req.execute();
            JsonArray arr = jsonRes.getAsJsonArray("results");

            assert (isFound(jsonRes) && arr.size() != 0);
            return new Venue(arr.get(0).getAsJsonObject().get("ADDRESS").getAsString(),
                    arr.get(0).getAsJsonObject().get("LATITUDE").getAsDouble(),
                    arr.get(0).getAsJsonObject().get("LONGITUDE").getAsDouble(),
                    arr.get(0).getAsJsonObject().get("X").getAsDouble(),
                    arr.get(0).getAsJsonObject().get("Y").getAsDouble());

        } catch (Throwable e) {
            throw new DukeApiException(Messages.DATA_NULL);
        }
    }

    private static boolean isFound(JsonObject jsonRes) {
        return Integer.parseInt(String.valueOf(jsonRes.getAsJsonPrimitive("found"))) > 0;
    }

    /**
     * Return all bus stop in Singapore.
     *
     * @return List of Bus Stop
     */
    public static HashMap<String, BusStop> getBusStop() throws DukeException {
        String path = "BusStops";
        int skip = 0;
        HashMap<String, BusStop> allBus = new HashMap<>();
        while (skip < 5500) {
            DataMallHttpRequest req = new DataMallHttpRequest("BusStops", path, Integer.toString(skip));
            skip += 500;
            JsonObject jsonRes = req.execute();
            JsonArray arr = jsonRes.getAsJsonArray("value");
            for (int i = 0; i < arr.size(); i++) {
                BusStop busstop = new BusStop(
                        arr.get(i).getAsJsonObject().get("BusStopCode").getAsString(),
                        arr.get(i).getAsJsonObject().get("Description").getAsString(),
                        arr.get(i).getAsJsonObject().get("RoadName").getAsString(),
                        arr.get(i).getAsJsonObject().get("Latitude").getAsDouble(),
                        arr.get(i).getAsJsonObject().get("Longitude").getAsDouble());
                allBus.put(arr.get(i).getAsJsonObject().get("BusStopCode").getAsString(), busstop);
            }
        }

        return allBus;
    }

    /**
     * Return all bus route in Singapore.
     *
     * @return bus route
     */
    public static HashMap<String, BusService> getBusRoute() throws DukeException {
        String path = "BusRoutes";
        int skip = 0;
        HashMap<String, BusService> busMap = new HashMap<>();
        while (skip < 26000) {
            DataMallHttpRequest req = new DataMallHttpRequest("BusRoutes", path, Integer.toString(skip));
            skip += 500;
            JsonObject jsonRes = req.execute();
            JsonArray arr = jsonRes.getAsJsonArray("value");
            for (int i = 0; i < arr.size(); i++) {
                String serviceNo = arr.get(i).getAsJsonObject().get("ServiceNo").getAsString();
                if (!busMap.containsKey(serviceNo)) {
                    BusService bus = new BusService(serviceNo);
                    busMap.put(serviceNo, bus);
                    bus.addRoute(arr.get(i).getAsJsonObject().get("BusStopCode").getAsString(),
                            arr.get(i).getAsJsonObject().get("Direction").getAsInt());
                } else {
                    busMap.get(serviceNo).addRoute(arr.get(i).getAsJsonObject().get("BusStopCode").getAsString(),
                            arr.get(i).getAsJsonObject().get("Direction").getAsInt());
                }
            }
        }

        return busMap;
    }

    /**
     * Return Static Map from StaticMap API.
     * @param param String formatted parameters
     * @return result The image from API
     * @throws DukeException Exception thrown by Duke
     */
    public static Image getStaticMap(String param) throws DukeException {
        StaticMapUrlRequest req = new StaticMapUrlRequest("https://developers.onemap.sg/commonapi/staticmap/getStaticImage?",
                param);
        return req.execute();
    }

    /**
     * Generate Param String from given parameters for StaticMapUrlRequest.
     * @param imageLength The length of StaticImage
     * @param imageWidth The width of StaticImage
     * @param zoomLvl The zoom level
     * @param centerLat The latitude coordinates of center
     * @param centerLong The longitude coordinates of center
     * @param polygonRegion The polygon regions to highlight
     * @param lineCoord The line coordinates to highlight
     * @param pointCoord The point coordinates to show
     * @return result The String param to parse
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
     * Create polygonRegion or lineCoord String for StaticMap.
     * @param latitude The latitude
     * @param longitude The longitude
     * @return result The String result
     */
    public static String createStaticMapArea(String latitude, String longitude) {
        return "[" +  latitude + "," + longitude + "]";
    }

    /**
     * Generate String to parse as parameter for polygonRegion or lineCoord in StaticMap.
     * @param points The ArrayList of points in format X,Y .
     * @param rgb The color of the region, in format r,g,b .
     * @return result The String param
     */
    public static String generateStaticMapPolygon(ArrayList<String> points, String rgb) {
        String result = "[";
        for (String point: points) {
            result += "[" + point + "]";
            if (point.equals(points.get(points.size() - 1))) {
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
     * Generate String to parse as parameter for polygonRegion or lineCoord in StaticMap.
     * @param points The ArrayList of points
     * @param rgb The color of the region or line
     * @return result The String param
     */
    public static String generateStaticMapLines(ArrayList<String> points, String rgb) {
        String result = "[";
        for (String point: points) {
            result += "[" + point + "]";
            if (point.equals(points.get(points.size() - 1))) {
                result += ",";
            }
        }

        if (!rgb.isEmpty()) {
            result += ":" + rgb;
        } else {
            result += ":0,0,0";
        }

        return result;
    }

    /**
     * Create Point String for StaticMap.
     * @param latitude The latitude
     * @param longitude The longitude
     * @param r The R value in RGB
     * @param g The G value in RGB
     * @param b The B value in RGB
     * @param label The text label for the point
     * @return result The String result
     */
    public static String createStaticMapPoint(String latitude, String longitude,
                                              String r, String g, String b, String label) {
        return "[" + latitude + "," + longitude + ",\"" + r + "," + g + "," + b + "\",\""
                + (Character.toString(label.charAt(0))).toUpperCase() + "\"]";
    }

    /**
     * Generate String to parse as parameter for polygonRegion or lineCoord in StaticMap.
     * @param points The ArrayList of points
     * @return result The String param
     */
    public static String generateStaticMapPoints(ArrayList<String> points) {
        String result = "[";
        for (String point: points) {
            result += "[" + point + "]";
            if (point.equals(points.get(points.size() - 1))) {
                result += ",";
            }
        }

        return result;
    }
}
