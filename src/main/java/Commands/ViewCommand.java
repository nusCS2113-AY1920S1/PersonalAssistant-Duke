package Commands;

import MovieUI.Controller;
import MovieUI.MovieHandler;
import object.MovieInfoObject;

import java.io.IOException;
import java.util.ArrayList;

public class ViewCommand extends CommandSuper {
    public ViewCommand(Controller UIController) {
        super(COMMAND_KEYS.view, CommandStructure.cmdStructure.get(COMMAND_KEYS.view), UIController);
    }

    @Override
    public void executeCommands() throws IOException {
        switch (this.getSubRootCommand()){
            case info:
                executeViewInfo();
                break;
            default:
                break;
        }
    }

    /**
     * clear all preference that was set previously
     * root: preference
     * sub: clear
     * payload: none
     * flag: -g (genre name -- not genre ID) -a (adult -- yes to allow adult content, no to restrict, set to yes by default)
     */
    private void executeViewInfo() throws IOException {
        MovieHandler movieHandler = ((MovieHandler)this.getUIController());
        int index = Integer.parseInt(this.getPayload());
        movieHandler.showMovie(movieHandler.getmMovies().get(--index));
        movieHandler.clearSearchTextField();
        movieHandler.initialize();
    }
}
