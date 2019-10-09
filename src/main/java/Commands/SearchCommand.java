package Commands;


import Contexts.SearchResultContext;
import MovieUI.Controller;
import MovieUI.MovieHandler;


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
        SearchResultContext.clearResults();
        ((MovieHandler)this.getUIController()).getAPIRequester().beginSearchRequest(getPayload());
    }

    private void executeTvShowSearch(){

    }


}
