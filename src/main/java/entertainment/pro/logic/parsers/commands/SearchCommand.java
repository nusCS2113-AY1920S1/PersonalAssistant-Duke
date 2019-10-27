package entertainment.pro.logic.parsers.commands;


import entertainment.pro.commons.exceptions.DateTimeParseExceptions;
import entertainment.pro.logic.movieRequesterAPI.RetrieveRequest;
import entertainment.pro.model.SearchProfile;
import entertainment.pro.storage.utils.ProfileCommands;
import entertainment.pro.ui.Controller;
import entertainment.pro.ui.MovieHandler;
import entertainment.pro.commons.enums.COMMANDKEYS;
import entertainment.pro.logic.parsers.CommandStructure;
import entertainment.pro.logic.parsers.CommandSuper;

import java.util.ArrayList;

import java.io.IOException;


public class SearchCommand extends CommandSuper {

    private static String GET_CURRENT = "/current";
    private static String GET_UPCOMING = "/upcoming";
    private static String GET_TRENDING = "/trend";
    private static String GET_POPULAR = "/popular";
    private static String GET_PREF = "-p";
    private static String GET_NEW_GENRE_PREF = "-g";
    private static String GET_NEW_GENRE_RESTRICT = "-r";
    private static String GET_NEW_SORT = "-s";
    private static String GET_NEW_ADULT_RATING = "-a";
    private boolean isMovie = false;


    public SearchCommand(Controller uicontroller) {
        super(COMMANDKEYS.search, CommandStructure.cmdStructure.get(COMMANDKEYS.search), uicontroller);
    }

    @Override
    public void executeCommands() throws IOException {
        String payload = getPayload();
        MovieHandler movieHandler = ((MovieHandler) this.getUIController());
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
                executeTVSearch(payload, movieHandler, searchProfile);
                break;
            default:
                break;
        }

    }

    private void executeMovieSearch(String payload, MovieHandler movieHandler, SearchProfile searchProfile) {
        movieHandler.setSearchProfile(searchProfile);
        if (payload.equals(GET_CURRENT)) {
            movieHandler.getAPIRequester().beginMovieRequest(RetrieveRequest.MoviesRequestType.CURRENT_MOVIES);
        } else if (payload.equals(GET_UPCOMING)) {
            movieHandler.getAPIRequester().beginMovieRequest(RetrieveRequest.MoviesRequestType.UPCOMING_MOVIES);
        } else if (payload.equals(GET_TRENDING)) {
            movieHandler.getAPIRequester().beginMovieRequest(RetrieveRequest.MoviesRequestType.TRENDING_MOVIES);
        } else if (payload.equals(GET_POPULAR)) {
            movieHandler.getAPIRequester().beginMovieRequest(RetrieveRequest.MoviesRequestType.POPULAR_MOVIES);
        } else {
            movieHandler.getAPIRequester().beginMovieRequest(RetrieveRequest.MoviesRequestType.SEARCH_MOVIES);
        }
    }

    private void executeTVSearch(String payload, MovieHandler movieHandler, SearchProfile searchProfile) {
        movieHandler.setSearchProfile(searchProfile);
        if (payload.equals(GET_CURRENT)) {
            movieHandler.showCurrentTV();
        } else if (payload.equals(GET_UPCOMING)) {
            movieHandler.getAPIRequester().beginMovieRequest(RetrieveRequest.MoviesRequestType.NEW_TV);
        } else if (payload.equals(GET_TRENDING)) {
            movieHandler.getAPIRequester().beginMovieRequest(RetrieveRequest.MoviesRequestType.TRENDING_TV);
        } else if (payload.equals(GET_POPULAR)) {
            movieHandler.getAPIRequester().beginMovieRequest(RetrieveRequest.MoviesRequestType.POPULAR_TV);
        }
    }



    private void getPreferences(MovieHandler movieHandler, SearchProfile searchProfile, String searchEntryName,
                                boolean isMovie) {
        if (!(getPayload().isEmpty() || getPayload().isBlank())) {
           searchProfile.setName(getPayload());
        }
        if (this.getFlagMap().containsKey(GET_PREF)) {
            this.getFlagMap().remove(GET_PREF);
            if (this.getFlagMap().isEmpty()) {
               searchProfile.setFromUserPreference(searchProfile, searchEntryName, isMovie,
                        movieHandler.getUserProfile());
            } else {
                try {
                    throw new DateTimeParseExceptions();
                } catch (DateTimeParseExceptions dateTimeParseExceptions) {
                    dateTimeParseExceptions.printStackTrace();
                }
            }
        } else {
            if (this.getFlagMap().containsKey(GET_NEW_GENRE_PREF)) {
               getGenresPrefForSearch(searchProfile);
            }
            if (this.getFlagMap().containsKey(GET_NEW_GENRE_RESTRICT)) {
               getGenresRestrictForSearch(searchProfile);
            }
            if (this.getFlagMap().containsKey(GET_NEW_ADULT_RATING)) {
                searchProfile.setAdult(getAdultPrefForSearch());
            }
            if (this.getFlagMap().containsKey(GET_NEW_SORT)) {
                ArrayList<String> getUserSortPref = getFlagMap().get(GET_NEW_SORT);
                searchProfile.setSortByAlphabetical(getAlphaSortForSearch(getUserSortPref.get(0)));
                searchProfile.setSortByLatestRelease(getDatesSortForSearch(getUserSortPref.get(0)));
                searchProfile.setSortByHighestRating(getRatingSortForSearch(getUserSortPref.get(0)));

            }

        }
    }

    private boolean getRatingSortForSearch(String userPref) {
        if (userPref.equals("3")) {
            return true;
        } else {
            return false;
        }
    }

    private boolean getDatesSortForSearch(String userPref) {
        if (userPref.equals("2")) {
            return true;
        } else {
            return false;
        }
    }

    private boolean getAlphaSortForSearch(String userPref) {
        if (userPref.equals("1")) {
            return true;
        } else {
            return false;
        }
    }


    private void getGenresPrefForSearch(SearchProfile searchProfile) {
        for (String log : getFlagMap().get(GET_NEW_GENRE_PREF)) {
            try {
                searchProfile.getGenreIdPreference().add(ProfileCommands.findGenreID(log));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void getGenresRestrictForSearch(SearchProfile searchProfile) {
        for (String log : getFlagMap().get(GET_NEW_GENRE_RESTRICT)) {
            try {
                searchProfile.getGenreIdRestriction().add(ProfileCommands.findGenreID(log));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean getAdultPrefForSearch() {
        System.out.println(getFlagMap().get(GET_NEW_ADULT_RATING));
        if (getFlagMap().get(GET_NEW_ADULT_RATING).contains("true")) {
            return true;
        } else {
            return false;
        }
    }
}