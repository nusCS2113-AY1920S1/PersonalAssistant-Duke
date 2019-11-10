package entertainment.pro.logic.cinemarequesterapi;

import entertainment.pro.commons.strings.PromptMessages;
import entertainment.pro.commons.exceptions.Exceptions;
import entertainment.pro.logic.movierequesterapi.RequestListener;
import entertainment.pro.logic.movierequesterapi.UrlRetriever;
import entertainment.pro.model.CinemaInfoObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Class that handles fetching results from The Google API service and then parsing them into CinemaInfoObjects.
 */
public class CinemaRetrieveRequest implements CinemaInfoFetcher {
    private RequestListener variableListener;
    private ArrayList<CinemaInfoObject> parsedCinemas;
    private static final String MAIN_URL = "https://maps.googleapis.com/maps/api/place"
            + "/textsearch/json?query=cinemas+near+";
    private static final String API_KEY = "AIzaSyBocJpxC7ChqlrS_mq6L-GpgudmXCzcXig";

    /**
     * constructor for cinema retrieve request class.
     *
     * @param variableListener calls the thread to execute the API
     */
    public CinemaRetrieveRequest(RequestListener variableListener) {
        this.variableListener = variableListener;
        parsedCinemas = new ArrayList<>();
    }

    /**
     * finds the nearest cinemas upon entering a desired location.
     *
     * @param location area to search
     * @return an array_list of cinemas with their info contained inside the CinemaInfoObject Class
     */
    public ArrayList<CinemaInfoObject> searchNearestCinemas(String location) throws Exceptions {
        try {
            String[] token = location.split(" ");
            String result = "";
            for (int i = 0; i < token.length; i++) {
                result += token[i];
                if (i != token.length - 1) {
                    result += "%20";
                }
            }
            String url = MAIN_URL + result + "&key=" + API_KEY;
            UrlRetriever retrieve = new UrlRetriever();
            String json = retrieve.readUrlAsString(new URL(url));
            fetchedCinemasJson(json);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return parsedCinemas;
    }

    /**
     * parses the results from json into a CinemaInfoObject.
     *
     * @param json result from the api request
     */
    @Override
    public void fetchedCinemasJson(String json) {
        if (json == null) {
            variableListener.requestTimedOut(PromptMessages.NO_RESULTS_FOUND);
            return;
        }
        JSONParser parser = new JSONParser();
        JSONObject cinemaData = new JSONObject();
        try {
            cinemaData = (JSONObject) parser.parse(json);
            JSONArray cinemas;
            cinemas = (JSONArray) cinemaData.get("results");
            parsedCinemas.clear();
            for (int i = 0; i < cinemas.size(); i++) {
                parsedCinemas.add(parseCinemaJson((JSONObject) (cinemas.get(i))));
            }
            Collections.sort(parsedCinemas, Comparator.comparingDouble(CinemaInfoObject::getRating));
            Collections.reverse(parsedCinemas);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * extracts out the data of each cinema from JSONObject to a MovieInfoObject.
     *
     * @param cinemaData JSONObject to be parsed
     * @return CinemaInfoObject of the desired cinema
     */
    public CinemaInfoObject parseCinemaJson(JSONObject cinemaData) {
        String name = (String) (cinemaData.get("name"));
        double rating;
        try {
            rating = (double) (cinemaData.get("rating"));
        } catch (ClassCastException e) {
            long doubleRating = (long) (cinemaData.get("rating"));
            rating = (double) (doubleRating);
        }
        String address = (String) (cinemaData.get("formatted_address"));
        CinemaInfoObject cinema = new CinemaInfoObject(name, rating, address);
        return cinema;
    }

    /**
     * The function is called when the fetcher reported a connection time out.
     * Notify the request listener.
     */
    @Override
    public void connectionTimedOut() {
        variableListener.requestTimedOut(PromptMessages.NO_RESULTS_FOUND);
    }
}
