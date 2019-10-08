package Commands;


import MovieUI.Controller;
import MovieUI.MovieHandler;
import movieRequesterAPI.RetrieveRequest;

import java.util.ArrayList;
import java.util.TreeMap;


public class SearchCommand extends CommandSuper {


    public SearchCommand(Controller UIController){
        super(COMMAND_KEYS.search , CommandStructure.cmdStructure.get(COMMAND_KEYS.search) ,UIController);
    }

    @Override
    public void executeCommands() {
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

    private void executeMovieSearch(){
        TreeMap<String, ArrayList<String>> treeMap = getFlagMap();
        if (treeMap.containsKey("-[c]")) {
            MovieHandler.showCurrentMovies();
        } else if (treeMap.containsKey("-[u]")){
            MovieHandler.showUpcomingMovies();
        } else if (treeMap.containsKey("-[t]")) {
            MovieHandler.showTrendMovies();
        } else if (treeMap.containsKey("-[p]")) {
            MovieHandler.showPopMovies();
        } else {
            ((MovieHandler) this.getUIController()).getAPIRequester().beginSearchRequest(getPayload());
        }
    }

    private void executeTvShowSearch(){
        TreeMap<String, ArrayList<String>> treeMap = getFlagMap();
        if (treeMap.containsKey("-[c]")) {
            System.out.println("yesss");
            MovieHandler.showCurrentTV();
        } else if (treeMap.containsKey("-[u]")){
            MovieHandler.showUpcomingTV();
        } else if (treeMap.containsKey("-[t]")) {
            MovieHandler.showTrendTV();
        } else if (treeMap.containsKey("-[p]")) {
            MovieHandler.showPopTV();
        }

    }


}
