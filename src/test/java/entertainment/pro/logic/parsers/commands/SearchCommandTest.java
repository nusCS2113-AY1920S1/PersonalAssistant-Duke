package entertainment.pro.logic.parsers.commands;

import entertainment.pro.commons.exceptions.*;
import entertainment.pro.logic.movierequesterapi.RetrieveRequest;
import entertainment.pro.logic.parsers.CommandParser;
import entertainment.pro.model.SearchProfile;
import entertainment.pro.ui.MovieHandler;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class SearchCommandTest extends MovieHandler{
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
    public SearchProfile searchProfile = new SearchProfile(name, age, genrePreference, genreRestriction, isAdultEnabled,
            playlist, sortByAlphaOrder, sortByRating, sortByReleaseDate, searchEntryName, isMovie);

    @Test
    public void executeCommands_returns_empty_param() throws Exceptions {
        String command1 = "search movies";
        String command2 = "search tvshows";
        MovieHandler movieHandler = new MovieHandler();
        RetrieveRequest retrieveRequest = new RetrieveRequest(movieHandler);
        mMovieRequest = retrieveRequest;
        movieHandler.setSearchProfile(searchProfile);
        assertThrows(Exceptions.class, () -> {
            CommandParser.parseCommands(command1, this);
        });
        assertThrows(Exceptions.class, () -> {
            CommandParser.parseCommands(command2, this);
        });
    }

    @Test
    public void executeCommands_returns_invalid_format() throws Exceptions {
        String command1 = "search movies /current -g ";
        String command2 = "search tvshows /trend -g action -r  ";
        MovieHandler movieHandler = new MovieHandler();
        RetrieveRequest retrieveRequest = new RetrieveRequest(movieHandler);
        mMovieRequest = retrieveRequest;
        movieHandler.setSearchProfile(searchProfile);
        assertThrows(InvalidFormatCommandException.class, () -> {
            CommandParser.parseCommands(command1, this);
        });
        assertThrows(InvalidFormatCommandException.class, () -> {
            CommandParser.parseCommands(command2, this);
        });
    }

    @Test
    public void executeCommands_returns_invalid_genre() throws Exceptions {
        String command1 = "search movies /current -g actio ";
        String command2 = "search tvshows /trend -g action -r advent ";
        String command3 = "search tvshows /trend -a true -s 1 -g action -r adventure horror ";
        MovieHandler movieHandler = new MovieHandler();
        RetrieveRequest retrieveRequest = new RetrieveRequest(movieHandler);
        mMovieRequest = retrieveRequest;
        movieHandler.setSearchProfile(searchProfile);
        assertThrows(InvalidGenreNameEnteredException.class, () -> {
            CommandParser.parseCommands(command1, this);
        });
        assertThrows(InvalidGenreNameEnteredException.class, () -> {
            CommandParser.parseCommands(command2, this);
        });
        assertThrows(InvalidGenreNameEnteredException.class, () -> {
            CommandParser.parseCommands(command3, this);
        });
    }

    @Test
    public void executeCommands_returns_repetitve_genre() throws Exceptions {
        String command1 = "search movies /current -g action, action";
        String command2 = "search tvshows /trend -g action,adventure -r adventure, horror ";
        String command3 = "search tvshows /trend -g action, adventure -r horror -g horror ";
        MovieHandler movieHandler = new MovieHandler();
        RetrieveRequest retrieveRequest = new RetrieveRequest(movieHandler);
        mMovieRequest = retrieveRequest;
        movieHandler.setSearchProfile(searchProfile);
        assertThrows(DuplicateGenreException.class, () -> {
            CommandParser.parseCommands(command1, this);
        });
        assertThrows(DuplicateGenreException.class, () -> {
            CommandParser.parseCommands(command2, this);
        });
        assertThrows(DuplicateGenreException.class, () -> {
            CommandParser.parseCommands(command3, this);
        });
    }

    @Test
    public void executeCommands_returns_invalid_adult() throws Exceptions {
        String command1 = "search movies /current -g action -a yes";
        String command2 = "search tvshows /popular -g action, adventure -r horror -a true false ";
        String command3 = "search tvshows /current -a true, false ";
        String command4 = "search tvshows /current -a true -g action -a true ";
        MovieHandler movieHandler = new MovieHandler();
        RetrieveRequest retrieveRequest = new RetrieveRequest(movieHandler);
        mMovieRequest = retrieveRequest;
        movieHandler.setSearchProfile(searchProfile);
        assertThrows(InvalidFormatCommandException.class, () -> {
            CommandParser.parseCommands(command1, this);
        });
        assertThrows(InvalidFormatCommandException.class, () -> {
            CommandParser.parseCommands(command2, this);
        });
        assertThrows(InvalidFormatCommandException.class, () -> {
            CommandParser.parseCommands(command3, this);
        });
        assertThrows(InvalidFormatCommandException.class, () -> {
            CommandParser.parseCommands(command4, this);
        });
    }

    @Test
    public void executeCommands_returns_empty_adult() throws Exceptions {
        String command1 = "search movies /current -g action -a ";
        MovieHandler movieHandler = new MovieHandler();
        RetrieveRequest retrieveRequest = new RetrieveRequest(movieHandler);
        mMovieRequest = retrieveRequest;
        movieHandler.setSearchProfile(searchProfile);
        assertThrows(InvalidFormatCommandException.class, () -> {
            CommandParser.parseCommands(command1, this);
        });
    }

    @Test
    public void executeCommands_returns_invalid_sort() throws Exceptions {
        String command1 = "search movies /current -g action -a true -s 12";
        String command2 = "search tvshows /popular -g action, adventure -r horror -a false -s 0 ";
        String command3 = "search tvshows /current -a true -s 1, 2";
        String command4 = "search tvshows /current -a true -s 2 -g action -s 3";
        MovieHandler movieHandler = new MovieHandler();
        RetrieveRequest retrieveRequest = new RetrieveRequest(movieHandler);
        mMovieRequest = retrieveRequest;
        movieHandler.setSearchProfile(searchProfile);
        assertThrows(InvalidFormatCommandException.class, () -> {
            CommandParser.parseCommands(command1, this);
        });
        assertThrows(InvalidFormatCommandException.class, () -> {
            CommandParser.parseCommands(command2, this);
        });
        assertThrows(InvalidFormatCommandException.class, () -> {
            CommandParser.parseCommands(command3, this);
        });
        assertThrows(InvalidFormatCommandException.class, () -> {
            CommandParser.parseCommands(command4, this);
        });
    }

    @Test
    public void executeCommands_returns_empty_sort() throws Exceptions {
        String command1 = "search movies /current -g action -a true -s ";
        MovieHandler movieHandler = new MovieHandler();
        RetrieveRequest retrieveRequest = new RetrieveRequest(movieHandler);
        mMovieRequest = retrieveRequest;
        movieHandler.setSearchProfile(searchProfile);
        assertThrows(InvalidFormatCommandException.class, () -> {
            CommandParser.parseCommands(command1, this);
        });
    }
}

