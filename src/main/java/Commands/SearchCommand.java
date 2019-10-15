package Commands;


import EPstorage.ProfileCommands;
import MovieUI.Controller;
import MovieUI.MovieHandler;
import MovieUI.MoviePosterController;
import movieRequesterAPI.RetrieveRequest;

import java.util.ArrayList;
import java.util.TreeMap;

import java.io.IOException;
import java.util.ArrayList;


public class SearchCommand extends CommandSuper {


    public SearchCommand(Controller UIController) {
        super(COMMAND_KEYS.search, CommandStructure.cmdStructure.get(COMMAND_KEYS.search), UIController);
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
        if (treeMap.containsKey("-q")) {
            MovieHandler.getAllTheMovie();
        }
        if (treeMap.containsKey("-n")) {
            MovieHandler.showCurrentMovies();
        } else if (treeMap.containsKey("-u")) {
            MovieHandler.showUpcomingMovies();
        } else if (treeMap.containsKey("-t")) {
            MovieHandler.showTrendMovies();
        } else if (treeMap.containsKey("-p")) {
            MovieHandler.showPopMovies();
        } else {
            String payload = getPayload();
            int age = 100;
            if (treeMap.containsKey("-a")) {
                ArrayList<String> getElementsAge = treeMap.get("-a");
                for (int i = 0; i < getElementsAge.size(); i += 1) {
                    System.out.println("checkk" + getElementsAge.get(i));
                }
                if (getElementsAge.size() > 0) {
                    for (int i = 0; i < getElementsAge.size(); i += 1) {
                        String getAge = getElementsAge.get(i);
                        if (getAge.endsWith(" ")) {
                            getAge = getAge.substring(0, getAge.length() - 2);
                        }
                        if (Integer.valueOf(getAge) < age) {
                            age = (Integer.valueOf(getElementsAge.get(i)));

                        }
                    }
                }
            }
            String genreList = "";
            if (treeMap.containsKey("-g")) {
                ArrayList<String> getElementsGenres = treeMap.get("-g");
                if (getElementsGenres.size() > 0) {
                    MovieHandler movieHandler = ((MovieHandler) this.getUIController());
                    ProfileCommands command = new ProfileCommands(movieHandler.getUserProfile());
                    for (int i = 0; i < getElementsGenres.size(); i += 1) {
                        String genre = String.valueOf(command.findGenreID(getElementsGenres.get(i)));
                        genreList += genre;
                        if (i != getElementsGenres.size() - 1) {
                            genreList += ",";

                        }
                    }
                }
            }
            String castList = "";
            if (treeMap.containsKey("-c")) {
                ArrayList<String> getElementsCast = treeMap.get("-c");
                if (getElementsCast.size() > 0) {
                    for (int i = 0; i < getElementsCast.size(); i += 1) {
                        String name = getElementsCast.get(i).replace(' ', ',');
                        String id = RetrieveRequest.getCastID(name);
                        castList += id;
                        if (i != getElementsCast.size() - 1) {
                            castList += ",";
                        }
                    }
                }
            }
            MovieHandler.showSearch(age, genreList, castList, payload);
        }
            /**MovieHandler movieHandler = ((MovieHandler) this.getUIController());
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
             **/
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

