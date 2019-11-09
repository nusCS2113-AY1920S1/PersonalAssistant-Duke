package entertainment.pro.logic.parsers.commands;

import entertainment.pro.commons.PromptMessages;
import entertainment.pro.commons.exceptions.*;
import entertainment.pro.logic.movieRequesterAPI.RetrieveRequest;
import entertainment.pro.model.SearchProfile;
import entertainment.pro.storage.utils.ProfileCommands;
import entertainment.pro.ui.Controller;
import entertainment.pro.ui.MovieHandler;
import entertainment.pro.commons.enums.COMMANDKEYS;
import entertainment.pro.logic.parsers.CommandStructure;
import entertainment.pro.logic.parsers.CommandSuper;

import javax.print.DocFlavor;
import java.util.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is called when user enters the command for a search request.
 */
public class SearchCommand extends CommandSuper {

    private static String GET_CURRENT = "/current";
    private static String GET_UPCOMING = "/upcoming";
    private static String GET_TRENDING = "/trend";
    private static String GET_POPULAR = "/popular";
    private static String GET_RATED = "/rated";
    private static String GET_PREF = "-p";
    private static String GET_NEW_GENRE_PREF = "-g";
    private static String GET_NEW_GENRE_RESTRICT = "-r";
    private static String GET_NEW_SORT = "-s";
    private static String GET_NEW_ADULT_RATING = "-a";
    private boolean isMovie = false;
    private String USER_PREF_FOR_ALPHA_SORT = "1";
    private String USER_PREF_FOR_RELEASE_DATES_SORT = "2";
    private String USER_PREF_FOR_RATING_SORT = "3";
    private String USER_PREF_FOR_ADULT_TRUE = "true";
    private String USER_PREF_FOR_ADULT_FALSE = "false";
    private static final Logger logger = Logger.getLogger(SearchCommand.class.getName());
    Set<Integer> genreSet = new HashSet<Integer>();



    /**
     * Constructor for each Command Super class.
     *
     * @param uicontroller The UI controller.
     */
    public SearchCommand(Controller uicontroller) {
        super(COMMANDKEYS.search, CommandStructure.cmdStructure.get(COMMANDKEYS.search), uicontroller);
    }

    /**
     * Responsible for extracting user preferences from the command and storing it in a SearchProfile object.
     * Also responsible for extracting whether the search request is for movies or TV shows.
     * And then call the approriate function to further extract the exact search request.
     *
     * @throws Exceptions
     */
    @Override
    public void executeCommands() throws Exceptions {
        genreSet.clear();
        String payload = getPayload();
        MovieHandler movieHandler = ((MovieHandler) this.getUiController());
        SearchProfile searchProfile = movieHandler.getSearchProfile();
        searchProfile.iniitalizeBackSearchProfile(searchProfile);
        getPreferences(movieHandler, searchProfile, payload, isMovie);
        switch (this.getSubRootCommand()) {
            case movies:
                isMovie = true;
                searchProfile.setMovie(true);
                executeMovieSearch(payload, movieHandler, searchProfile);
                break;
            case tvshows:
                executeTvSearch(payload, movieHandler, searchProfile);
                break;
            default:
                movieHandler.setGeneralFeedbackText(PromptMessages.INVALID_FORMAT);
                logger.log(Level.SEVERE, PromptMessages.INVALID_PARAM_IN_SEARCH);
                throw new InvalidParameterException(PromptMessages.INVALID_PARAM_IN_SEARCH);
        }
    }

    /**
     * Responsible for extracting the exact movie related search request.
     * Calls appropriate function to execute it.
     *
     * @param payload       String consisting of a movie name entered by user if any.
     * @param movieHandler  MovieHandler class used to later call the appropriate function.
     * @param searchProfile Object that contains all the user preferences for the search request.
     * @throws Exceptions when user input is invalid or empty.
     */
    private void executeMovieSearch(String payload, MovieHandler movieHandler,
                                    SearchProfile searchProfile) throws Exceptions {
        movieHandler.setSearchProfile(searchProfile);
        if (payload.isEmpty() || payload.isBlank()) {
            movieHandler.setGeneralFeedbackText(PromptMessages.EMPTY_PARAM_IN_SEARCH);
            logger.log(Level.SEVERE, PromptMessages.EMPTY_PARAM_IN_SEARCH);
            throw new Exceptions(PromptMessages.EMPTY_PARAM_IN_SEARCH);
        }

            if (payload.equals(GET_CURRENT)) {
            movieHandler.getAPIRequester().beginSearchRequest(RetrieveRequest.MoviesRequestType.CURRENT_MOVIES);
        } else if (payload.equals(GET_UPCOMING)) {
            movieHandler.getAPIRequester().beginSearchRequest(RetrieveRequest.MoviesRequestType.UPCOMING_MOVIES);
        } else if (payload.equals(GET_TRENDING)) {
            movieHandler.getAPIRequester().beginSearchRequest(RetrieveRequest.MoviesRequestType.TRENDING_MOVIES);
        } else if (payload.equals(GET_POPULAR)) {
            movieHandler.getAPIRequester().beginSearchRequest(RetrieveRequest.MoviesRequestType.POPULAR_MOVIES);
        } else if (payload.equals(GET_RATED)) {
            movieHandler.getAPIRequester().beginSearchRequest(RetrieveRequest.MoviesRequestType.TOP_RATED_MOVIES);
        } else {
            movieHandler.getAPIRequester().beginSearchRequest(RetrieveRequest.MoviesRequestType.SEARCH_MOVIES);
        }
    }

    /**
     * Responsible for extracting the exact TV show related search request.
     * Calls appropriate function to execute it.
     *
     * @param payload       String consisting of a TV show name entered by user if any.
     * @param movieHandler  MovieHandler class used to later call the appropriate function.
     * @param searchProfile Object that contains all the user preferences for the search request.
     * @throws Exceptions when user input is blank or empty.
     */
    private void executeTvSearch(String payload, MovieHandler movieHandler,
                                 SearchProfile searchProfile) throws Exceptions {
        movieHandler.setSearchProfile(searchProfile);
        if (payload.isEmpty() || payload.isBlank()) {
            movieHandler.setGeneralFeedbackText(PromptMessages.EMPTY_PARAM_IN_SEARCH);
            logger.log(Level.SEVERE, PromptMessages.EMPTY_PARAM_IN_SEARCH);
            throw new Exceptions(PromptMessages.EMPTY_PARAM_IN_SEARCH);
        }
        if (payload.equals(GET_CURRENT)) {
            movieHandler.getAPIRequester().beginSearchRequest(RetrieveRequest.MoviesRequestType.CURRENT_TV);
        } else if (payload.equals(GET_TRENDING)) {
            movieHandler.getAPIRequester().beginSearchRequest(RetrieveRequest.MoviesRequestType.TRENDING_TV);
        } else if (payload.equals(GET_POPULAR)) {
            movieHandler.getAPIRequester().beginSearchRequest(RetrieveRequest.MoviesRequestType.POPULAR_TV);
        } else if (payload.equals(GET_RATED)) {
            movieHandler.getAPIRequester().beginSearchRequest(RetrieveRequest.MoviesRequestType.TOP_RATED_TV);
        } else {
            movieHandler.getAPIRequester().beginSearchRequest(RetrieveRequest.MoviesRequestType.SEARCH_TV);
        }
    }


    /**
     * Responsible for getting the user preferences for the particular search request.
     * Sets these preferences into SearchProfile Object.
     *
     * @param movieHandler    MovieHandler class to call appropriate function if needed
     * @param searchProfile   Object that contains all the preferences of the search request.
     * @param searchEntryName name of movie/TV show that user want search result to be based on, if any.
     * @param isMovie         whether the search request is movie or TV shows related.
     * @throws InvalidFormatCommandException when user input is invalid.
     */
    private void getPreferences(MovieHandler movieHandler, SearchProfile searchProfile, String searchEntryName,
                                boolean isMovie) throws InvalidFormatCommandException, InvalidGenreNameEnteredException, DuplicateGenreException {
        if (!(getPayload().isEmpty() || getPayload().isBlank())) {
            searchProfile.setName(getPayload());
        }
        if (this.getFlagMap().containsKey(GET_PREF)) {
            this.getFlagMap().remove(GET_PREF);
            if (this.getFlagMap().isEmpty()) {
                searchProfile.setFromUserPreference(searchProfile, searchEntryName, isMovie,
                        movieHandler.getUserProfile());
            } else {
                movieHandler.setGeneralFeedbackText(PromptMessages.INVALID_COMBI_OF_FLAGS);
                throw new InvalidFormatCommandException();
            }
        } else {
            if (this.getFlagMap().containsKey(GET_NEW_GENRE_PREF)) {
                try {
                    getGenresPrefForSearch(searchProfile);
                } catch (DuplicateGenreException e) {
                    movieHandler.setGeneralFeedbackText(PromptMessages.REPETITVE_GENRE_NAME);
                    throw new DuplicateGenreException();
                }
            }
            if (this.getFlagMap().containsKey(GET_NEW_GENRE_RESTRICT)) {
                try {
                    getGenresRestrictForSearch(searchProfile);
                } catch (DuplicateGenreException e) {
                    movieHandler.setGeneralFeedbackText(PromptMessages.REPETITVE_GENRE_NAME);
                    throw new DuplicateGenreException();
                }
            }
            if (this.getFlagMap().containsKey(GET_NEW_ADULT_RATING)) {
                searchProfile.setAdult(getAdultPrefForSearch());
            }
            if (this.getFlagMap().containsKey(GET_NEW_SORT)) {
                ArrayList<String> getUserSortPref = getFlagMap().get(GET_NEW_SORT);
                ArrayList<String> getParams = getFlagMap().get(GET_NEW_SORT);
                if (getParams.size() != 1) {
                    ((MovieHandler) this.getUiController()).setGeneralFeedbackText(PromptMessages.INVALID_FORMAT);
                    throw new InvalidFormatCommandException();
                }
                String sortOption = getUserSortPref.get(0);
                int sortOptionConvertToInt;
                try {
                    sortOptionConvertToInt = Integer.parseInt(sortOption);
                    if (sortOptionConvertToInt <= 0 || sortOptionConvertToInt > 3) {
                        movieHandler.setGeneralFeedbackText(PromptMessages.INVALID_FORMAT);
                        throw new InvalidFormatCommandException();
                    }
                } catch (NumberFormatException e) {
                    throw new InvalidFormatCommandException();
                }
                searchProfile.setSortByAlphabetical(getAlphaSortForSearch(getUserSortPref.get(0)));
                searchProfile.setSortByLatestRelease(getDatesSortForSearch(getUserSortPref.get(0)));
                searchProfile.setSortByHighestRating(getRatingSortForSearch(getUserSortPref.get(0)));
            }

        }
    }

    /**
     * Responsible for returning whether user wants the search request results to sorted based on ratings.
     *
     * @param userPref String containing user preference.
     * @return true if user wants the search request results to sorted based on ratings and false otherwise.
     */
    private boolean getRatingSortForSearch(String userPref) {
        if (userPref.equals(USER_PREF_FOR_RATING_SORT)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Responsible for returning whether user wants the search request results to sorted based on release dates.
     *
     * @param userPref String containing user preference.
     * @return true if user wants the search request results to sorted based on release dates and false otherwise.
     */
    private boolean getDatesSortForSearch(String userPref) {
        if (userPref.equals(USER_PREF_FOR_RELEASE_DATES_SORT)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Responsible for returning whether user wants the search request results to sorted based on alphabetical order.
     *
     * @param userPref String containing user preference.
     * @return true if user wants the search request results to sorted based on alphabetical order and false otherwise.
     */
    private boolean getAlphaSortForSearch(String userPref) {
        if (userPref.equals(USER_PREF_FOR_ALPHA_SORT)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * Responsible for setting user genre preferences for the particular search request.
     * Sets these genre preferences into SearchProfile object.
     *
     * @param searchProfile Object that contains all the preferences for the particular search request.
     */
    private void getGenresPrefForSearch(SearchProfile searchProfile) throws InvalidFormatCommandException, InvalidGenreNameEnteredException, DuplicateGenreException {
        ArrayList<String> getParams = getFlagMap().get(GET_NEW_GENRE_PREF);
        if (getParams.size() == 0) {
            ((MovieHandler) this.getUiController()).setGeneralFeedbackText(PromptMessages.INVALID_FORMAT);
            throw new InvalidFormatCommandException();
        }
        for (String log : getFlagMap().get(GET_NEW_GENRE_PREF)) {
            try {
                if (log.isBlank() || log.isEmpty()) {
                    ((MovieHandler) this.getUiController()).setGeneralFeedbackText(PromptMessages.INVALID_FORMAT);
                    throw new InvalidFormatCommandException();
                }
                int genre = ProfileCommands.findGenreID(log);
                if (genre == 0) {
                    ((MovieHandler) this.getUiController()).setGeneralFeedbackText(PromptMessages.INVALID_GENRE_NAME);
                    throw new InvalidGenreNameEnteredException();
                }
                if (genreSet.contains(genre)) {
                    throw new DuplicateGenreException();
                }
                genreSet.add(genre);
                searchProfile.getGenreIdPreference().add(genre);
            } catch (IOException e) {
                ((MovieHandler) this.getUiController()).setGeneralFeedbackText(PromptMessages.INVALID_GENRE_NAME);
            }
        }

    }

    /**
     * Responsible for setting user genre restrictions for the particular search request.
     * Sets these genre restrictions into SearchProfile object.
     *
     * @param searchProfile Object that contains all the preferences for the particular search request.
     */
    private void getGenresRestrictForSearch(SearchProfile searchProfile) throws InvalidFormatCommandException, InvalidGenreNameEnteredException, DuplicateGenreException {
        ArrayList<String> getParams = getFlagMap().get(GET_NEW_GENRE_PREF);
        if (getParams.size() == 0) {
            ((MovieHandler) this.getUiController()).setGeneralFeedbackText(PromptMessages.INVALID_FORMAT);
            throw new InvalidFormatCommandException();
        }
        for (String log : getFlagMap().get(GET_NEW_GENRE_RESTRICT)) {
            try {
                if (log.isBlank() || log.isEmpty()) {
                    ((MovieHandler) this.getUiController()).setGeneralFeedbackText(PromptMessages.INVALID_FORMAT);
                    throw new InvalidFormatCommandException();
                }
                int genre = ProfileCommands.findGenreID(log);
                if (genre == 0) {
                    ((MovieHandler) this.getUiController()).setGeneralFeedbackText(PromptMessages.INVALID_GENRE_NAME);
                    throw new InvalidGenreNameEnteredException();
                }
                if (genreSet.contains(genre)) {
                    throw new DuplicateGenreException();
                }
                genreSet.add(genre);
                searchProfile.getGenreIdRestriction().add(genre);
            } catch (IOException e) {
                ((MovieHandler) this.getUiController()).setGeneralFeedbackText(PromptMessages.INVALID_GENRE_NAME);
            }
        }
    }

    /**
     * Responsible for returning whether user prefers adult content to be included inside search request results..
     *
     * @return true if user prefers adult content to be included inside search request results and false otherwise.
     * @throws InvalidFormatCommandException when user input is invalid.
     */
    private boolean getAdultPrefForSearch() throws InvalidFormatCommandException {
        System.out.println(getFlagMap().get(GET_NEW_ADULT_RATING));
        ArrayList<String> getParams = getFlagMap().get(GET_NEW_ADULT_RATING);
        if (getParams.size() != 1) {
            ((MovieHandler) this.getUiController()).setGeneralFeedbackText(PromptMessages.INVALID_FORMAT);
            throw new InvalidFormatCommandException();
        }
        if (getFlagMap().get(GET_NEW_ADULT_RATING).contains(USER_PREF_FOR_ADULT_TRUE)) {
            return true;
        } else if (getFlagMap().get(GET_NEW_ADULT_RATING).contains(USER_PREF_FOR_ADULT_FALSE)) {
            return false;
        } else {
            ((MovieHandler) this.getUiController()).setGeneralFeedbackText(PromptMessages.INVALID_FORMAT);
            throw new InvalidFormatCommandException();
        }
    }

}