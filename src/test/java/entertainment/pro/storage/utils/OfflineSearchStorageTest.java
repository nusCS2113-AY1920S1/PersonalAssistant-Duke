package entertainment.pro.storage.utils;

import entertainment.pro.commons.exceptions.Exceptions;
import entertainment.pro.logic.movieRequesterAPI.RetrieveRequest;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.annotation.Target;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OfflineSearchStorageTest {
    private static final String CURRENT_MOVIES_DATA_FILEPATH = "/data/CurrentMovies.json";
    private static final String TRENDING_MOVIES_DATA_FILEPATH = "/data/TrendingMovies.json";
    private static final String CURRENT_TV_DATA_FILEPATH = "/data/CurrentTV.json";
    private static final String TRENDING_TV_DATA_FILEPATH = "/data/TrendingTV.json";
    private RetrieveRequest.MoviesRequestType currentMovieType = RetrieveRequest.MoviesRequestType.CURRENT_MOVIES;
    private RetrieveRequest.MoviesRequestType trendingMovieType = RetrieveRequest.MoviesRequestType.TRENDING_MOVIES;
    private RetrieveRequest.MoviesRequestType searchMovieType = RetrieveRequest.MoviesRequestType.SEARCH_MOVIES;
    private RetrieveRequest.MoviesRequestType currentTVType = RetrieveRequest.MoviesRequestType.CURRENT_TV;
    private RetrieveRequest.MoviesRequestType trendTVType = RetrieveRequest.MoviesRequestType.TRENDING_TV;


    @Test
    public void getFileNameTest() {
        assertEquals(CURRENT_MOVIES_DATA_FILEPATH, OfflineSearchStorage.getFileName(currentMovieType));
        assertEquals(TRENDING_MOVIES_DATA_FILEPATH, OfflineSearchStorage.getFileName(trendingMovieType));
        assertEquals("", OfflineSearchStorage.getFileName(searchMovieType));
        assertEquals(CURRENT_TV_DATA_FILEPATH, OfflineSearchStorage.getFileName(currentTVType));
        assertEquals(TRENDING_TV_DATA_FILEPATH, OfflineSearchStorage.getFileName(trendTVType));
    }

}
