package entertainment.pro.logic.parsers.commands;



import entertainment.pro.commons.exceptions.DateTimeParseExceptions;
import entertainment.pro.commons.exceptions.Exceptions;
import entertainment.pro.logic.movieRequesterAPI.RetrieveRequest;
import entertainment.pro.model.SearchProfile;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import entertainment.pro.logic.movieRequesterAPI.MovieResultFilter;
import entertainment.pro.model.GenreId;
import entertainment.pro.storage.utils.ProfileCommands;
import entertainment.pro.ui.Controller;
import entertainment.pro.ui.MovieHandler;
import entertainment.pro.commons.enums.COMMANDKEYS;
import entertainment.pro.logic.parsers.CommandStructure;
import entertainment.pro.logic.parsers.CommandSuper;

import java.io.FileInputStream;
import java.io.InputStream;
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


    ArrayList<String> genrePreference = new ArrayList<>();
    ArrayList<String> genreRestriction = new ArrayList<>();
    boolean isAdult = false;
    //ArrayList<String> genrePreference = new ArrayList<>();

    /*
    flag legend:
    -> small letter means user have to append more stuff behind
    -> capital letter means user don't have to add on details after flag
    -g  genres that user want the search results to have
    -P  include all user's preferred genre in search
    -R  exclude all user's restricted genre in search
     */
    public SearchCommand(Controller uicontroller) {
        super(COMMANDKEYS.search, CommandStructure.cmdStructure.get(COMMANDKEYS.search), uicontroller);
    }

    @Override
    public void executeCommands() throws Exceptions {
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

    private void executeMovieSearch(String payload, MovieHandler movieHandler, SearchProfile searchProfile) throws Exceptions {
        movieHandler.setSearchProfile(searchProfile);
        if (payload.equals(GET_CURRENT)) {
            movieHandler.getAPIRequester().beginSearchRequest(RetrieveRequest.MoviesRequestType.CURRENT_MOVIES);
        } else if (payload.equals(GET_UPCOMING)) {
            movieHandler.getAPIRequester().beginSearchRequest(RetrieveRequest.MoviesRequestType.UPCOMING_MOVIES);
        } else if (payload.equals(GET_TRENDING)) {
            movieHandler.getAPIRequester().beginSearchRequest(RetrieveRequest.MoviesRequestType.TRENDING_MOVIES);
        } else if (payload.equals(GET_POPULAR)) {
            movieHandler.getAPIRequester().beginSearchRequest(RetrieveRequest.MoviesRequestType.POPULAR_MOVIES);
        } else {
            movieHandler.getAPIRequester().beginSearchRequest(RetrieveRequest.MoviesRequestType.SEARCH_MOVIES);
        }
    }

    private void executeTVSearch(String payload, MovieHandler movieHandler, SearchProfile searchProfile) throws Exceptions {
        movieHandler.setSearchProfile(searchProfile);
        if (payload.equals(GET_CURRENT)) {
            movieHandler.showCurrentTV();
        } else if (payload.equals(GET_TRENDING)) {
            movieHandler.getAPIRequester().beginSearchRequest(RetrieveRequest.MoviesRequestType.TRENDING_TV);
        } else if (payload.equals(GET_POPULAR)) {
            movieHandler.getAPIRequester().beginSearchRequest(RetrieveRequest.MoviesRequestType.POPULAR_TV);
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

    /**
     * search for movie titles using keywords.
     * root: search
     * sub: movies
     * payload: <keywords>
     * flag: -g (genre name -- not genre ID) [-g preferences -> to use user's preferred filters]

    private void executeMovieSearch() throws IOException {
        TreeMap<String, ArrayList<String>> treeMap = getFlagMap();
        MovieHandler movieHandler = ((MovieHandler) this.getUIController());
        //if (getFlagMap().containsKey(GET_PREFERENCE))
        if (this.getFlagMap().containsKey("-q")) {
            movieHandler.getAllTheMovie();
        } else {
            ArrayList<Integer> inputGenrePreference = new ArrayList<>();
            ArrayList<Integer> inputGenreRestriction = new ArrayList<>();
            /**
             * FLAGS DONT DIFFERENTIATE BETWEEN CAPITAL AND SMALL LETTER NOW SO JUST USE SMALL LETTER FIRST RMB TO CHANGE LATER :C -wh

            if (this.getFlagMap().containsKey("-p")) {
                inputGenrePreference.addAll(movieHandler.getUserProfile().getGenreIdPreference());
            }
            if (this.getFlagMap().containsKey("-r")) {
                inputGenreRestriction.addAll(movieHandler.getUserProfile().getGenreIdRestriction());
            }
            if (this.getFlagMap().containsKey("-g")) {
                for (String log : this.getFlagMap().get("-g")) {
                    inputGenrePreference.add(findGenreID(log));
                }
            }
            MovieResultFilter filter = new MovieResultFilter(inputGenrePreference, inputGenreRestriction);
            movieHandler.setFilter(filter);
            if (movieHandler.getUserProfile().isAdult()) {
                ((MovieHandler) this.getUIController()).getAPIRequester()
                        .beginMovieSearchRequest(getPayload(), true);
            } else {
                ((MovieHandler) this.getUIController()).getAPIRequester()
                        .beginMovieSearchRequest(getPayload(), false);
>>>>>>> upstream/master
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
<<<<<<< HEAD
=======

//        else if (!this.getFlagMap().containsKey("-g")) {
//                if (movieHandler.getUserProfile().isAdult()) {
//                    ((MovieHandler) this.getUIController()).getAPIRequester()
//                            .beginMovieSearchRequest(getPayload() ,  true);
//                } else {
//                    ((MovieHandler) this.getUIController()).getAPIRequester()
//                            .beginMovieSearchRequest(getPayload() ,  false);
//                }
//                ((MovieHandler) this.getUIController()).clearSearchTextField();
//            } else {
//                ArrayList<Integer> inputGenrePreference = new ArrayList<>();
//                ArrayList<Integer> inputGenreRestriction = new ArrayList<>();
//
//                for (String log : this.getFlagMap().get("-g")) {
//                    if (log.equalsIgnoreCase("preferences")) {
//                        inputGenrePreference.addAll(movieHandler.getUserProfile().getGenreIdPreference());
//                    } else if (log.equalsIgnoreCase("restrictions")) {
//                        inputGenreRestriction.addAll(movieHandler.getUserProfile().getGenreIdRestriction());
//                    } else {
//                        ProfileCommands command = new ProfileCommands(movieHandler.getUserProfile());
//                        inputGenrePreference.add(command.findGenreID(log));
//                    }
//                }
//                if (movieHandler.getUserProfile().isAdult()) {
//                    ((MovieHandler) this.getUIController()).getAPIRequester()
//                            .beginMovieSearchRequestWithPreference(getPayload(), inputGenrePreference, inputGenreRestriction, true);
//                } else {
//                    ((MovieHandler) this.getUIController()).getAPIRequester()
//                            .beginMovieSearchRequestWithPreference(getPayload(), inputGenrePreference, inputGenreRestriction, false);
//                }
//                ((MovieHandler) this.getUIController()).clearSearchTextField();
//            }
//        }
>>>>>>> upstream/master

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

**/


    private Integer findGenreID(String genreName) throws IOException {
        genreName = genreName.trim();
        ObjectMapper mapper = new ObjectMapper();
        InputStream inputStream = new FileInputStream("EPdata/GenreId.json");
        TypeReference<ArrayList<GenreId>> typeReference = new TypeReference<ArrayList<GenreId>>() {};
        ArrayList<GenreId> genreIds = mapper.readValue(inputStream, typeReference);
        for (GenreId log : genreIds){
            if (log.getGenre().equalsIgnoreCase(genreName)){
                inputStream.close();
                return log.getId();
            }
        }
        inputStream.close();
        return 0;
    }
}