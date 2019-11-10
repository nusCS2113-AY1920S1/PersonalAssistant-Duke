package entertainment.pro.logic.parsers.commands;

import entertainment.pro.commons.exceptions.DuplicateGenreException;
import entertainment.pro.commons.exceptions.Exceptions;
import entertainment.pro.commons.exceptions.InvalidFormatCommandException;
import entertainment.pro.commons.exceptions.InvalidGenreNameEnteredException;
import entertainment.pro.commons.exceptions.InvalidParameterException;
import entertainment.pro.commons.strings.PromptMessages;
import entertainment.pro.logic.movierequesterapi.RetrieveRequest;
import entertainment.pro.model.SearchProfile;
import entertainment.pro.storage.user.ProfileCommands;
import entertainment.pro.ui.Controller;
import entertainment.pro.ui.MovieHandler;
import entertainment.pro.commons.enums.COMMANDKEYS;
import entertainment.pro.logic.parsers.CommandStructure;
import entertainment.pro.logic.parsers.CommandSuper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is called when user enters the command for a search request.
 */
public class SearchCommand extends CommandSuper {

    private static final String GET_CURRENT = "/current";
    private static final String GET_UPCOMING = "/upcoming";
    private static final String GET_TRENDING = "/trend";
    private static final String GET_POPULAR = "/popular";
    private static final String GET_RATED = "/rated";
    private static final String GET_PREF = "-p";
    private static final String GET_NEW_GENRE_PREF = "-g";
    private static final String GET_NEW_GENRE_RESTRICT = "-r";
    private static final String GET_NEW_SORT = "-s";
    private static final String GET_NEW_ADULT_RATING = "-a";
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
     * @param uiController The UI controller.
     */
    public SearchCommand(Controller uiController) {
        super(COMMANDKEYS.SEARCH, CommandStructure.cmdStructure.get(COMMANDKEYS.SEARCH), uiController);
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
        logger.log(Level.INFO, PromptMessages.STARTING_SEARCH_MESSAGE);
        genreSet.clear();
        String payload = getPayload();
        MovieHandler movieHandler = ((MovieHandler) this.getUiController());
        logger.log(Level.INFO, PromptMessages.RETRIEVING_SEARCH_PROFILE);
        SearchProfile searchProfile = getPreferences(movieHandler, payload, isMovie);
        switch (this.getSubRootCommand()) {
        case MOVIES:
            isMovie = true;
            logger.log(Level.INFO, PromptMessages.SEARCH_TYPE_IS_MOVIES);
            searchProfile = searchProfile.setMovie(true);
            executeMovieSearch(payload, movieHandler, searchProfile);
            break;
        case TVSHOWS:
            logger.log(Level.INFO, PromptMessages.SEARCH_TYPE_IS_TV);
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
    private SearchProfile getPreferences(MovieHandler movieHandler, String searchEntryName, boolean isMovie)
            throws InvalidFormatCommandException, InvalidGenreNameEnteredException, DuplicateGenreException {
        SearchProfile searchProfile = movieHandler.getSearchProfile();
        searchProfile = searchProfile.iniitalizeBackSearchProfile();
        if (!(getPayload().isEmpty() || getPayload().isBlank())) {
            searchProfile = searchProfile.setName(getPayload());
        }
        if (this.getFlagMap().containsKey(GET_PREF)) {
            this.getFlagMap().remove(GET_PREF);
            if (this.getFlagMap().isEmpty()) {
                searchProfile = searchProfile.setFromUserPreference(searchEntryName,
                        isMovie, movieHandler.getUserProfile());
            } else {
                movieHandler.setGeneralFeedbackText(PromptMessages.INVALID_COMBI_OF_FLAGS);
                logger.log(Level.WARNING, PromptMessages.INVALID_FORMAT);
                throw new InvalidFormatCommandException();
            }
        } else {
            if (this.getFlagMap().containsKey(GET_NEW_GENRE_PREF)) {
                try {
                    searchProfile = getGenresPrefForSearch(searchProfile);
                } catch (DuplicateGenreException e) {
                    logger.log(Level.WARNING, PromptMessages.REPETITVE_GENRE_NAME_ERROR);
                    movieHandler.setGeneralFeedbackText(PromptMessages.REPETITVE_GENRE_NAME);
                    throw new DuplicateGenreException();
                }
            }
            if (this.getFlagMap().containsKey(GET_NEW_GENRE_RESTRICT)) {
                try {
                    searchProfile = getGenresRestrictForSearch(searchProfile);
                } catch (DuplicateGenreException e) {
                    logger.log(Level.WARNING, PromptMessages.REPETITVE_GENRE_NAME_ERROR);
                    movieHandler.setGeneralFeedbackText(PromptMessages.REPETITVE_GENRE_NAME);
                    throw new DuplicateGenreException();
                }
            }
            if (this.getFlagMap().containsKey(GET_NEW_ADULT_RATING)) {
                searchProfile = searchProfile.setAdult(getAdultPrefForSearch());
            }
            if (this.getFlagMap().containsKey(GET_NEW_SORT)) {
                ArrayList<String> getUserSortPref = getFlagMap().get(GET_NEW_SORT);
                ArrayList<String> getParams = getFlagMap().get(GET_NEW_SORT);
                if (getParams.size() != 1) {
                    logger.log(Level.WARNING, PromptMessages.INVALID_FORMAT);
                    ((MovieHandler) this.getUiController()).setGeneralFeedbackText(PromptMessages.INVALID_FORMAT);
                    throw new InvalidFormatCommandException();
                }
                String sortOption = getUserSortPref.get(0);
                int sortOptionConvertToInt;
                try {
                    sortOptionConvertToInt = Integer.parseInt(sortOption);
                    if (sortOptionConvertToInt <= 0 || sortOptionConvertToInt > 3) {
                        logger.log(Level.WARNING, PromptMessages.INVALID_FORMAT);
                        movieHandler.setGeneralFeedbackText(PromptMessages.INVALID_FORMAT);
                        throw new InvalidFormatCommandException();
                    }
                } catch (NumberFormatException e) {
                    logger.log(Level.WARNING, PromptMessages.INVALID_FORMAT);
                    throw new InvalidFormatCommandException();
                }
                searchProfile = searchProfile.setSortByAlphabetical(getAlphaSortForSearch(getUserSortPref.get(0)));
                searchProfile = searchProfile.setSortByLatestRelease(getDatesSortForSearch(getUserSortPref.get(0)));
                searchProfile = searchProfile.setSortByHighestRating(getRatingSortForSearch(getUserSortPref.get(0)));
            }
        }
        return searchProfile;
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
    private SearchProfile getGenresPrefForSearch(SearchProfile searchProfile) throws InvalidFormatCommandException,
            InvalidGenreNameEnteredException, DuplicateGenreException {
        ArrayList<String> getParams = getFlagMap().get(GET_NEW_GENRE_PREF);
        if (getParams.size() == 0) {
            ((MovieHandler) this.getUiController()).setGeneralFeedbackText(PromptMessages.INVALID_FORMAT);
            logger.log(Level.WARNING, PromptMessages.INVALID_FORMAT);
            throw new InvalidFormatCommandException();
        }
        ArrayList<Integer> genrePreference = searchProfile.getGenreIdPreference();
        for (String log : getFlagMap().get(GET_NEW_GENRE_PREF)) {
            try {
                if (log.isBlank() || log.isEmpty()) {
                    ((MovieHandler) this.getUiController()).setGeneralFeedbackText(PromptMessages.INVALID_FORMAT);
                    logger.log(Level.WARNING, PromptMessages.INVALID_FORMAT);
                    throw new InvalidFormatCommandException();
                }
                int genre = ProfileCommands.findGenreID(log);
                if (genre == 0) {
                    ((MovieHandler) this.getUiController()).setGeneralFeedbackText(PromptMessages.INVALID_GENRE_NAME);
                    logger.log(Level.WARNING, PromptMessages.INVALID_GENRE_NAME_ERROR);
                    throw new InvalidGenreNameEnteredException();
                }
                if (genreSet.contains(genre)) {
                    logger.log(Level.WARNING, PromptMessages.REPETITVE_GENRE_NAME_ERROR);
                    throw new DuplicateGenreException();
                }
                genreSet.add(genre);
                genrePreference.add(genre);
            } catch (IOException e) {
                logger.log(Level.WARNING, PromptMessages.INVALID_GENRE_NAME_ERROR);
                ((MovieHandler) this.getUiController()).setGeneralFeedbackText(PromptMessages.INVALID_GENRE_NAME);
            }
        }
        return searchProfile.setGenreIdPreference(genrePreference);
    }

    /**
     * Responsible for setting user genre restrictions for the particular search request.
     * Sets these genre restrictions into SearchProfile object.
     *
     * @param searchProfile Object that contains all the preferences for the particular search request.
     */
    private SearchProfile getGenresRestrictForSearch(SearchProfile searchProfile)
            throws InvalidFormatCommandException, InvalidGenreNameEnteredException, DuplicateGenreException {
        ArrayList<String> getParams = getFlagMap().get(GET_NEW_GENRE_RESTRICT);
        if (getParams.size() == 0) {
            logger.log(Level.WARNING, PromptMessages.INVALID_FORMAT);
            ((MovieHandler) this.getUiController()).setGeneralFeedbackText(PromptMessages.INVALID_FORMAT);
            throw new InvalidFormatCommandException();
        }
        ArrayList<Integer> genreRestriction = searchProfile.getGenreIdRestriction();
        for (String log : getFlagMap().get(GET_NEW_GENRE_RESTRICT)) {
            try {
                if (log.isBlank() || log.isEmpty()) {
                    logger.log(Level.WARNING, PromptMessages.INVALID_FORMAT);
                    ((MovieHandler) this.getUiController()).setGeneralFeedbackText(PromptMessages.INVALID_FORMAT);
                    throw new InvalidFormatCommandException();
                }
                int genre = ProfileCommands.findGenreID(log);
                if (genre == 0) {
                    logger.log(Level.WARNING, PromptMessages.INVALID_GENRE_NAME_ERROR);
                    ((MovieHandler) this.getUiController()).setGeneralFeedbackText(PromptMessages.INVALID_GENRE_NAME);
                    throw new InvalidGenreNameEnteredException();
                }
                if (genreSet.contains(genre)) {
                    logger.log(Level.WARNING, PromptMessages.REPETITVE_GENRE_NAME_ERROR);
                    throw new DuplicateGenreException();
                }
                genreSet.add(genre);
                genreRestriction.add(genre);
            } catch (IOException e) {
                logger.log(Level.WARNING, PromptMessages.INVALID_GENRE_NAME_ERROR);
                ((MovieHandler) this.getUiController()).setGeneralFeedbackText(PromptMessages.INVALID_GENRE_NAME);
            }
        }
        return searchProfile.setGenreIdRestriction(genreRestriction);
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
            logger.log(Level.WARNING, PromptMessages.INVALID_FORMAT);
            ((MovieHandler) this.getUiController()).setGeneralFeedbackText(PromptMessages.INVALID_FORMAT);
            throw new InvalidFormatCommandException();
        }
        if (getFlagMap().get(GET_NEW_ADULT_RATING).contains(USER_PREF_FOR_ADULT_TRUE)) {
            return true;
        } else if (getFlagMap().get(GET_NEW_ADULT_RATING).contains(USER_PREF_FOR_ADULT_FALSE)) {
            return false;
        } else {
            logger.log(Level.WARNING, PromptMessages.INVALID_FORMAT);
            ((MovieHandler) this.getUiController()).setGeneralFeedbackText(PromptMessages.INVALID_FORMAT);
            throw new InvalidFormatCommandException();
        }
    }
}