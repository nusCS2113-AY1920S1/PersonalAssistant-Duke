package entertainment.pro.logic.parsers.commands;

import entertainment.pro.commons.exceptions.DuplicateGenreException;
import entertainment.pro.commons.exceptions.Exceptions;
import entertainment.pro.commons.exceptions.InvalidFormatCommandException;
import entertainment.pro.commons.exceptions.InvalidGenreNameEnteredException;
import entertainment.pro.logic.movierequesterapi.RetrieveRequest;
import entertainment.pro.logic.parsers.CommandParser;
import entertainment.pro.model.SearchProfile;
import entertainment.pro.ui.MovieHandler;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class SearchCommandTest extends MovieHandler {
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
        MovieHandler movieHandler = new MovieHandler();
        RetrieveRequest retrieveRequest = new RetrieveRequest(movieHandler);
        mMovieRequest = retrieveRequest;
        movieHandler.setSearchProfile(searchProfile);
        assertThrows(Exceptions.class, () -> {
            CommandParser.parseCommands(command1, this);
        });

        String command2 = "search tvshows";
        assertThrows(Exceptions.class, () -> {
            CommandParser.parseCommands(command2, this);
        });
    }

    @Test
    public void executeCommands_returns_invalid_format() throws Exceptions {
        String command1 = "search movies /current -g ";
        MovieHandler movieHandler = new MovieHandler();
        RetrieveRequest retrieveRequest = new RetrieveRequest(movieHandler);
        mMovieRequest = retrieveRequest;
        movieHandler.setSearchProfile(searchProfile);
        assertThrows(InvalidFormatCommandException.class, () -> {
            CommandParser.parseCommands(command1, this);
        });

        String command2 = "search tvshows /trend -g action -r  ";
        assertThrows(InvalidFormatCommandException.class, () -> {
            CommandParser.parseCommands(command2, this);
        });
    }

    @Test
    public void executeCommands_returns_invalid_genre() throws Exceptions {
        String command1 = "search movies /current -g actio ";
        MovieHandler movieHandler = new MovieHandler();
        RetrieveRequest retrieveRequest = new RetrieveRequest(movieHandler);
        mMovieRequest = retrieveRequest;
        movieHandler.setSearchProfile(searchProfile);
        assertThrows(InvalidGenreNameEnteredException.class, () -> {
            CommandParser.parseCommands(command1, this);
        });
        String command2 = "search tvshows /trend -g action -r advent ";
        assertThrows(InvalidGenreNameEnteredException.class, () -> {
            CommandParser.parseCommands(command2, this);
        });
        String command3 = "search tvshows /trend -a true -s 1 -g action -r adventure horror ";
        assertThrows(InvalidGenreNameEnteredException.class, () -> {
            CommandParser.parseCommands(command3, this);
        });
    }

    @Test
    public void executeCommands_returns_repetitve_genre() throws Exceptions {
        String command1 = "search movies /current -g action, action";
        MovieHandler movieHandler = new MovieHandler();
        RetrieveRequest retrieveRequest = new RetrieveRequest(movieHandler);
        mMovieRequest = retrieveRequest;
        movieHandler.setSearchProfile(searchProfile);
        assertThrows(DuplicateGenreException.class, () -> {
            CommandParser.parseCommands(command1, this);
        });

        String command2 = "search tvshows /trend -g action,adventure -r adventure, horror ";
        assertThrows(DuplicateGenreException.class, () -> {
            CommandParser.parseCommands(command2, this);
        });

        String command3 = "search tvshows /trend -g action, adventure -r horror -g horror ";
        assertThrows(DuplicateGenreException.class, () -> {
            CommandParser.parseCommands(command3, this);
        });
    }

    @Test
    public void executeCommands_returns_invalid_adult() throws Exceptions {
        String command1 = "search movies /current -g action -a yes";
        MovieHandler movieHandler = new MovieHandler();
        RetrieveRequest retrieveRequest = new RetrieveRequest(movieHandler);
        mMovieRequest = retrieveRequest;
        movieHandler.setSearchProfile(searchProfile);
        assertThrows(InvalidFormatCommandException.class, () -> {
            CommandParser.parseCommands(command1, this);
        });

        String command2 = "search tvshows /popular -g action, adventure -r horror -a true false ";
        assertThrows(InvalidFormatCommandException.class, () -> {
            CommandParser.parseCommands(command2, this);
        });
        String command3 = "search tvshows /current -a true, false ";
        assertThrows(InvalidFormatCommandException.class, () -> {
            CommandParser.parseCommands(command3, this);
        });

        String command4 = "search tvshows /current -a true -g action -a true ";
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
        MovieHandler movieHandler = new MovieHandler();
        RetrieveRequest retrieveRequest = new RetrieveRequest(movieHandler);
        mMovieRequest = retrieveRequest;
        movieHandler.setSearchProfile(searchProfile);
        assertThrows(InvalidFormatCommandException.class, () -> {
            CommandParser.parseCommands(command1, this);
        });

        String command2 = "search tvshows /popular -g action, adventure -r horror -a false -s 0 ";
        assertThrows(InvalidFormatCommandException.class, () -> {
            CommandParser.parseCommands(command2, this);
        });

        String command3 = "search tvshows /current -a true -s 1, 2";
        assertThrows(InvalidFormatCommandException.class, () -> {
            CommandParser.parseCommands(command3, this);
        });


        String command4 = "search tvshows /current -a true -s 2 -g action -s 3";
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

