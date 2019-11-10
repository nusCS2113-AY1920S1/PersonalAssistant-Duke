package entertainment.pro.storage.utils;

import entertainment.pro.commons.PromptMessages;
import entertainment.pro.commons.exceptions.Exceptions;
import entertainment.pro.commons.exceptions.ParseExceptionInExtraction;
import entertainment.pro.logic.movieRequesterAPI.RetrieveRequest;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is responsible for fetching appropriate string data for search requests.
 * Called when there is no internet connection and data fetch has to be done offline.
 */
public class OfflineSearchStorage {
    private static final int MAX_FILE_NO_FOR_MOVIES = 1055;
    private static final int MAX_FILE_NO_FOR_TV = 150;
    private static final String CURRENT_MOVIES_DATA_FILEPATH = "/data/CurrentMovies.json";
    private static final String POPULAR_MOVIES_DATA_FILEPATH = "/data/PopularMovies.json";
    private static final String TRENDING_MOVIES_DATA_FILEPATH = "/data/TrendingMovies.json";
    private static final String TOP_RATED_MOVIES_DATA_FILEPATH = "/data/RatedMovies.json";
    private static final String UPCOMING_MOVIES_DATA_FILEPATH = "/data/UpcomingMovies.json";
    private static final String CURRENT_TV_DATA_FILEPATH = "/data/CurrentTV.json";
    private static final String POPULAR_TV_DATA_FILEPATH = "/data/PopularTV.json";
    private static final String TRENDING_TV_DATA_FILEPATH = "/data/TrendingTV.json";
    private static final String TOP_RATED_TV_DATA_FILEPATH = "/data/RatedTV.json";
    private static final String MOVIES_DATABASE_FILEPATH = "/data/movieData/";
    private static final String TV_DATABASE_FILEPATH = "/data/tvData/";
    private static final String KEYWORD_FOR_SEARCH_REQUESTS = "results";
    private static final Logger logger = Logger.getLogger(OfflineSearchStorage.class.getName());

    /**
     * Load and fetch appropriate string data for search requests.
     *
     * @return JSONArray that consist of all the required data for the search request.
     * @throws Exceptions when encounter a failed/interrupted I/O operation.
     */
    public static JSONArray load() throws Exceptions {
        String dataFromJSON = "";
        JSONArray searchData = new JSONArray();
        RetrieveRequest.MoviesRequestType type = RetrieveRequest.getGetType();
        String filename = getFileName(type);
        if ((type.equals(RetrieveRequest.MoviesRequestType.SEARCH_MOVIES)) ||
                (type.equals(RetrieveRequest.MoviesRequestType.SEARCH_TV))) {
            searchData = getSearchData(type);
            return searchData;
        }
        dataFromJSON = getData(filename);
        System.out.println(filename);
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = (JSONObject) jsonParser.parse(dataFromJSON);
            searchData = (JSONArray) jsonObject.get(KEYWORD_FOR_SEARCH_REQUESTS);
        } catch (ParseException e) {
            logger.log(Level.SEVERE, PromptMessages.PARSE_EXCEPTION_IN_EXTRACTION);
            throw new ParseExceptionInExtraction();
        }
        return searchData;
    }

    /**
     * Responsible for fetching the data from the file.
     *
     * @param filename The name of the file that consists the data.
     * @return String that consists all the data fetched from the file.
     * @throws Exceptions when encounter a failed/interrupted I/O operation.
     */
    public static String getData(String filename) throws Exceptions {
        logger.log(Level.INFO, PromptMessages.EXTRACTING_FROM_FILE);
        InputStream inputStream = OfflineSearchStorage.class.getResourceAsStream(filename);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line = "";
        String dataFromJSON = "";
        try {
            while ((line = bufferedReader.readLine()) != null) {
                dataFromJSON += line;
            }
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            logger.log(Level.INFO, PromptMessages.EXTRACTED_FROM_FILE);
        } catch (IOException e) {
            logger.log(Level.WARNING, PromptMessages.IO_EXCEPTION_IN_OFFLINE);
            throw new Exceptions(PromptMessages.IO_EXCEPTION_IN_OFFLINE);
        }
        return dataFromJSON;
    }

    /**
     * Responsible for obtaining and returning the appropriate filename for the respective search requests.
     *
     * @param type Type to specify the respective search request.
     * @return appropriate filename that contains data for the respective search requests.
     */
    public static String getFileName(RetrieveRequest.MoviesRequestType type) {
        String filename = "";
        switch (type) {
            case CURRENT_MOVIES:
                filename = CURRENT_MOVIES_DATA_FILEPATH;
                ;
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
     * Responsible for fetching data for search by name request.
     *
     * @return JSONArray that consist of all the required data for the search request.
     * @throws Exceptions when encounter a failed/interrupted I/O operation.
     */
    public static JSONArray getSearchData(RetrieveRequest.MoviesRequestType type) throws Exceptions {
        JSONArray searchResults = new JSONArray();
        String fileType = "";
        int maxFileNo = 0;
        if (type.equals(RetrieveRequest.MoviesRequestType.SEARCH_MOVIES)) {
            fileType = MOVIES_DATABASE_FILEPATH;
            maxFileNo = MAX_FILE_NO_FOR_MOVIES;
        } else {
            fileType = TV_DATABASE_FILEPATH;
            maxFileNo = MAX_FILE_NO_FOR_TV;
        }
        logger.log(Level.INFO, PromptMessages.EXTRACTING_FROM_FILE);
        for (int i = 1; i <= maxFileNo; i += 1) {
            String filename = fileType;
            filename += i + ".json";
            System.out.println(filename);
            String dataFromJSON = getData(filename);
            JSONParser jsonParser = new JSONParser();
            JSONArray jsonArray = new JSONArray();
            try {
                jsonArray = (JSONArray) jsonParser.parse(dataFromJSON);
            } catch (ParseException e) {
                logger.log(Level.SEVERE, PromptMessages.PARSE_EXCEPTION_IN_EXTRACTION);
                throw new ParseExceptionInExtraction();
            }
            System.out.println(filename);
            for (int j = 0; j < jsonArray.size(); j += 1) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(j);
                searchResults.add(jsonObject);
            }
        }
        logger.log(Level.INFO, PromptMessages.EXTRACTED_FROM_FILE);
        return searchResults;
    }
}

