package entertainment.pro.logic.parsers.commands;

import entertainment.pro.commons.exceptions.Exceptions;
import entertainment.pro.commons.exceptions.InvalidFormatCommandException;
import entertainment.pro.commons.exceptions.MissingInfoException;
import entertainment.pro.logic.movieRequesterAPI.RequestListener;
import entertainment.pro.logic.movieRequesterAPI.RetrieveRequest;
import entertainment.pro.logic.movieRequesterAPI.RetrieveRequestTest;
import entertainment.pro.logic.parsers.CommandParser;
import entertainment.pro.model.MovieInfoObject;
import entertainment.pro.model.SearchProfile;
import entertainment.pro.ui.MovieHandler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static entertainment.pro.ui.MovieHandler.mMovieRequest;
import static entertainment.pro.ui.MovieHandler.mMovies;
import static org.junit.jupiter.api.Assertions.*;

public class ViewCommandTest extends MovieHandler{
    ArrayList<Integer> genrePreference = new ArrayList<>();
    ArrayList<Integer> genreRestriction = new ArrayList<>();
    ArrayList<String> playlist = new ArrayList<>();
    boolean isAdultEnabled = false;
    boolean sortByAlphaOrder = false;
    boolean sortByRating = false;
    boolean sortByReleaseDate = false;
    boolean isMovie = true;
    String searchEntryName = "";
    String name = "";
    int age = 0;

    /**
     * Initializes the Search Profile.
     */
    public SearchProfile searchProfile = new SearchProfile(name, age, genrePreference, genreRestriction, isAdultEnabled,
            playlist, sortByAlphaOrder, sortByRating, sortByReleaseDate, searchEntryName, isMovie);

    @Test
    public void executeEntryCommandsTest_returns_exceptions() throws Exceptions, IOException {
        MovieHandler movieHandler = new MovieHandler();
        RetrieveRequest retrieveRequest = new RetrieveRequest(movieHandler);
        mMovieRequest = retrieveRequest;
        assertThrows(InvalidFormatCommandException.class, () -> {
            movieHandler.showMovie(1);
        });
        String filename = "/data/movieData/1.json";
        String dataFromJSON = RetrieveRequestTest.getString(filename);
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray = new JSONArray();
        try {
            jsonArray = (JSONArray) jsonParser.parse(dataFromJSON);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        movieHandler.setSearchProfile(searchProfile);
        for (int i = 0; i < jsonArray.size(); i += 1) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            MovieInfoObject movieInfoObject = RetrieveRequest.parseMovieJSON(jsonObject);
            movieHandler.mMovies.add(movieInfoObject);
        }
        assertThrows(InvalidFormatCommandException.class, () -> {
            movieHandler.showMovie(-1);
        });
        int num = mMovies.size() + 1;
        assertThrows(InvalidFormatCommandException.class, () -> {
            movieHandler.showMovie(num);
        });

    }

    @Test
    public void executeBackCommandsTest_returns_exceptions() throws MissingInfoException, Exceptions, IOException {
        String testCommand = "view back";
        String[] commandParse = testCommand.split(" ");
        assertThrows(Exceptions.class, () -> {
            CommandParser.rootCommand(commandParse, testCommand, this);
        });
        String testCommand1 = "search movies /current";
        String[] commandParse1 = testCommand1.split(" ");
        CommandParser.rootCommand(commandParse1, testCommand1, this);
        assertThrows(Exceptions.class, () -> {
            CommandParser.rootCommand(commandParse, testCommand, this);
        });
    }

}
