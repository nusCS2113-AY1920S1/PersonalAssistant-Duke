package entertainment.pro.logic.cinemaRequesterAPI;

import entertainment.pro.commons.PromptMessages;
import entertainment.pro.commons.exceptions.Exceptions;
import entertainment.pro.logic.movieRequesterAPI.RequestListener;
import entertainment.pro.logic.movieRequesterAPI.URLRetriever;
import entertainment.pro.model.CinemaInfoObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Class that handles fetching results from The Google API service and then parsing them into CinemaInfoObjects.
 */
public class CinemaRetrieveRequest implements CinemaInfoFetcher {
    private RequestListener variableListener;
    private ArrayList<CinemaInfoObject> parsedCinemas;
    private static final String MAIN_URL = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=cinemas+near+";
    private static final String API_KEY = "AIzaSyBocJpxC7ChqlrS_mq6L-GpgudmXCzcXig";

    /**
     * constructor for cinema retrieve request class.
     * @param variableListener calls the thread to execute the API
     */
    public CinemaRetrieveRequest(RequestListener variableListener) {
        this.variableListener = variableListener;
        parsedCinemas = new ArrayList<>();
    }

    /**
     * finds the nearest cinemas upon entering a desired location.
     * @param location area to search
     * @return an array_list of cinemas with their info contained inside the CinemaInfoObject Class
     */
    public ArrayList<CinemaInfoObject> searchNearestCinemas(String location) throws Exceptions {
        try {
            String[] token = location.split(" ");
            String result = "";
            for (int i = 0; i < token.length; i++) {
                result += token[i];
                if (i != token.length - 1) result += "%20";
            }
            String url = MAIN_URL + result + "&key=" + API_KEY;
            URLRetriever retrieve = new URLRetriever();
            String json = retrieve.readURLAsString(new URL(url));
            fetchedCinemasJSON(json);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return parsedCinemas;
    }

    /**
     * parses the results from json into a CinemaInfoObject.
     * @param json result from the api request
     */
    @Override
    public void fetchedCinemasJSON(String json) {
        if (json == null) {
            variableListener.requestTimedOut(PromptMessages.NO_RESULTS_FOUND);
            return;
        }
        JSONParser parser = new JSONParser();
        JSONObject cinemaData = new JSONObject();
        try {
            cinemaData = (JSONObject) parser.parse(json);
            JSONArray cinemas;
            cinemas = (JSONArray)cinemaData.get("results");
            parsedCinemas.clear();
            for (int i = 0; i < cinemas.size(); i++) {
                parsedCinemas.add(parseCinemaJSON((JSONObject)(cinemas.get(i))));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * extracts out the data of each cinema from JSONObject to a MovieInfoObject.
     * @param cinemaData JSONObject to be parsed
     * @return CinemaInfoObject of the desired cinema
     */
    public CinemaInfoObject parseCinemaJSON(JSONObject cinemaData) {
        String name = (String)(cinemaData.get("name"));
        double rating;
        try {
            rating = (double)(cinemaData.get("rating"));
        } catch (ClassCastException e) {
            long doubleRating = (long)(cinemaData.get("rating"));
            rating = (double)(doubleRating);
        }
        String address = (String)(cinemaData.get("formatted_address"));
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
