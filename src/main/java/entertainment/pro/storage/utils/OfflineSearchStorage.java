package entertainment.pro.storage.utils;

import entertainment.pro.logic.movieRequesterAPI.RetrieveRequest;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;

/**
 * This class is responsible for fetching appropriate string data for search requests.
 * Called when there is no internet connection and data fetch has to be done offline.
 */
public class OfflineSearchStorage {
    private static String CURRENT_MOVIES_DATA_FILEPATH = "/data/CurrentMovies.json";
    private static String POPULAR_MOVIES_DATA_FILEPATH = "/data/PopularMovies.json";
    private static String TRENDING_MOVIES_DATA_FILEPATH = "/data/TrendingMovies.json";
    private static String TOP_RATED_MOVIES_DATA_FILEPATH = "/data/RatedMovies.json";
    private static String UPCOMING_MOVIES_DATA_FILEPATH = "/data/UpcomingMovies.json";
    private static String CURRENT_TV_DATA_FILEPATH = "/data/CurrentTV.json";
    private static String POPULAR_TV_DATA_FILEPATH = "/data/PopularTV.json";
    private static String TRENDING_TV_DATA_FILEPATH = "/data/TrendingTV.json";
    private static String TOP_RATED_TV_DATA_FILEPATH = "/data/RatedTV.json";
    private static String MOVIES_DATABASE_FILEPATH = "/data/movieData/";

    /**
     * load and fetch appropriate string data for search requests.
     */
    public String load() throws IOException {
        String dataFromJSON = "";
        RetrieveRequest.MoviesRequestType type = RetrieveRequest.getGetType();
        String filename = getFileName(type);
        InputStream inputStream = getClass().getResourceAsStream(filename);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            dataFromJSON += line;
        }
        bufferedReader.close();
        inputStreamReader.close();
        inputStream.close();

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = new JSONObject();
        try {
            //parse into JSONObject.
            jsonObject = (JSONObject) jsonParser.parse(dataFromJSON);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //return jsonObject;
        return dataFromJSON;
    }

    /**
     * Responsible for obtaining and returning the appropriate filename for the respective search requests.
     *
     * @param type Type to specify the respective search request.
     * @return appropriate filename that contains data for the respective search requests.
     */
    private String getFileName(RetrieveRequest.MoviesRequestType type) {
        String filename = "";
        switch (type) {
            case CURRENT_MOVIES:
                filename = CURRENT_MOVIES_DATA_FILEPATH;
                break;
            case UPCOMING_MOVIES:
                filename = UPCOMING_MOVIES_DATA_FILEPATH;
                break;
            case TRENDING_MOVIES:
                filename = TRENDING_MOVIES_DATA_FILEPATH;
                break;
            case TOP_RATED_MOVIES:
                filename = TOP_RATED_MOVIES_DATA_FILEPATH;
                break;
            case POPULAR_MOVIES:
                filename = POPULAR_MOVIES_DATA_FILEPATH;
                break;
            case CURRENT_TV:
                filename = CURRENT_TV_DATA_FILEPATH;
                break;
            case TRENDING_TV:
                filename = TRENDING_TV_DATA_FILEPATH;
                break;
            case TOP_RATED_TV:
                filename = TOP_RATED_TV_DATA_FILEPATH;
                break;
            case POPULAR_TV:
                filename = POPULAR_TV_DATA_FILEPATH;
                break;
            default:
                filename = "";
        }
        return filename;
    }

    /**
     * private String getDataForSearchMovies() throws IOException {
     * // String filename = MOVIES_DATABASE_FILEPATH;
     * String dataForMovies = "";
     * for (int i = 1; i <= 2; i += 1) {
     * String filename = MOVIES_DATABASE_FILEPATH;
     * filename += i + ".json";
     * InputStream inputStream = getClass().getResourceAsStream(filename);
     * System.out.println(filename);
     * InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
     * BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
     * String line = "";
     * int j = 1;
     * String prevline = "";
     * while ((line = bufferedReader.readLine()) != null) {
     * dataForMovies += prevline;
     * if ((i > 1) && (j > 1))  {
     * System.out.println("come on2");
     * prevline = line;
     * //dataForMovies += line;
     * j += 1;
     * } else if (i == 1) {
     * prevline = line;
     * } else if ((i > 1) && (j == 1)) {
     * System.out.println("come on");
     * j += 1;
     * System.out.println(j);
     * }
     * }
     * bufferedReader.close();
     * inputStreamReader.close();
     * inputStream.close();
     * if (i == 2) {
     * dataForMovies += prevline;
     * } else {
     * dataForMovies += ",";
     * }
     * System.out.println(dataForMovies);
     * <p>
     * }
     * return dataForMovies;
     * <p>
     * }
     **/

    public JSONArray getSearchData() {
        JSONArray total = new JSONArray();
        for (int i = 1; i <= 500; i += 1) {
            String filename = "data/movieData/";
            filename += i + ".json";
            System.out.println(filename);
            JSONParser parser = new JSONParser();
            JSONArray jsonArray = new JSONArray();
            try {
                jsonArray = (JSONArray) parser.parse(new FileReader(filename));
            } catch (org.json.simple.parser.ParseException | FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (int j = 0; j < jsonArray.size(); j += 1) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(j);
                total.add(jsonObject);
            }
        }
        return total;
    }
}

