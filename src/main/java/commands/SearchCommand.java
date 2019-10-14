package commands;


import EPstorage.ProfileCommands;
import MovieUI.Controller;
import MovieUI.MovieHandler;

import java.util.ArrayList;
import java.util.TreeMap;

import java.io.IOException;


public class SearchCommand extends CommandSuper {


    public SearchCommand(Controller UIController) {
        super(COMMANDKEYS.search, CommandStructure.cmdStructure.get(COMMANDKEYS.search), UIController);
    }

    @Override
    public void executeCommands() throws IOException {
        switch (this.getSubRootCommand()){
            case movies:
                executeMovieSearch();
                break;
            case tvshows:
                executeTvShowSearch();
                break;
//            case all:
//                executeTvShowSearch();
//                break;
            default:
                break;
        }

    }


    /**
     * search for movie titles using keywords
     * root: search
     * sub: movies
     * payload: <keywords>
     * flag: -g (genre name -- not genre ID) [-g preferences -> to use user's preferred filters]
     */
    private void executeMovieSearch() throws IOException {
        TreeMap<String, ArrayList<String>> treeMap = getFlagMap();
        if (treeMap.containsKey("-c")) {
            MovieHandler.showCurrentMovies();
        } else if (treeMap.containsKey("-u")) {
            MovieHandler.showUpcomingMovies();
        } else if (treeMap.containsKey("-t")) {
            MovieHandler.showTrendMovies();
        } else if (treeMap.containsKey("-p")) {
            MovieHandler.showPopMovies();
        } else {
            MovieHandler movieHandler = ((MovieHandler) this.getUIController());
            if (!this.getFlagMap().containsKey("-g")) {
                ((MovieHandler) this.getUIController()).getAPIRequester().beginMovieSearchRequest(getPayload());
                movieHandler.clearSearchTextField();
            } else {
                ArrayList<Integer> inputGenre = new ArrayList<>(10);
                for (String log : this.getFlagMap().get("-g")) {
                    if (log.equalsIgnoreCase("preferences")) {
                        inputGenre.addAll(movieHandler.getUserProfile().getGenreId());
                    } else {
                        ProfileCommands command = new ProfileCommands(movieHandler.getUserProfile());
                        inputGenre.add(command.findGenreID(log));
                    }
                }
                ((MovieHandler) this.getUIController()).getAPIRequester().beginMovieSearchRequestWithGenre(getPayload(), inputGenre);
                movieHandler.clearSearchTextField();
            }
        }
    }

    private void executeTvShowSearch() {
        TreeMap<String, ArrayList<String>> treeMap = getFlagMap();
        if (treeMap.containsKey("-c")) {
            MovieHandler.showCurrentTV();
            //} else if (treeMap.containsKey("-[u]")){
            //  MovieHandler.showUpcomingTV();
        } else if (treeMap.containsKey("-t")) {
            MovieHandler.showTrendTV();
        } else if (treeMap.containsKey("-p")) {
            MovieHandler.showPopTV();
        }

    }
}

