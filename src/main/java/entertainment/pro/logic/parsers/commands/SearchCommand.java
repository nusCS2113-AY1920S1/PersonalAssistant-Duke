package entertainment.pro.logic.parsers.commands;


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
import java.util.TreeMap;

import java.io.IOException;


public class SearchCommand extends CommandSuper {

    private static String GET_CURRENT = "/current";
    private static String GET_UPCOMING = "/upcoming";
    private static String GET_TRENDING = "/trend";
    private static String GET_POPULAR = "/popular";
    private static String GET_PRE = "-p";
    private static String GET_NEW_GENRE_PREF = "-g";
    private static String GET_NEW_GENRE_RESTRICT = "-r";
    private static String GET_NEW_SORT = "-s";
    private static String GET_NEW_ADULT_RATING = "-a";
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
    public void executeCommands() throws IOException {
        switch (this.getSubRootCommand()) {
            case movies:
                executeMovieSearch();
                break;
            case tvshows:
                executeTvShowSearch();
                break;
            default:
                break;
        }

    }

    /**
     * search for movie titles using keywords.
     * root: search
     * sub: movies
     * payload: <keywords>
     * flag: -g (genre name -- not genre ID) [-g preferences -> to use user's preferred filters]
     */
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
             */
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
            }
        }
    }

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

    private void executeTvShowSearch() {
        MovieHandler movieHandler = new MovieHandler();

        TreeMap<String, ArrayList<String>> treeMap = getFlagMap();
        if (treeMap.containsKey("-c")) {
            movieHandler.showCurrentTV();
            //} else if (treeMap.containsKey("-[u]")){
            //  MovieHandler.showUpcomingTV();
        } else if (treeMap.containsKey("-t")) {
            movieHandler.showTrendTV();
        } else if (treeMap.containsKey("-p")) {
            movieHandler.showPopTV();
        }

    }

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