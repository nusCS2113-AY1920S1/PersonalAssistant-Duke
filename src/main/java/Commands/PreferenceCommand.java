package Commands;

import EPstorage.ProfileCommands;
import MovieUI.Controller;
import MovieUI.MovieHandler;

import java.io.IOException;

public class PreferenceCommand extends CommandSuper{
    public PreferenceCommand(Controller UIController) {
        super(COMMAND_KEYS.preference, CommandStructure.cmdStructure.get(COMMAND_KEYS.preference) , UIController);
    }

    @Override
    public void executeCommands() throws IOException {
        switch (this.getSubRootCommand()){
            case add:
                executeAddPreference();
                break;
            case remove:
                executeRemovePreference();
                break;
            default:
                break;
        }
    }

    /**
     * add to user preference
     * root: preference
     * sub: add
     * payload: none
     * flag: -g (genre name -- not genre ID)
     */
    private void executeAddPreference() throws IOException {
        MovieHandler movieHandler = ((MovieHandler)this.getUIController());
        ProfileCommands command = new ProfileCommands(movieHandler.getUserProfile());
        command.addPreference(this.getFlagMap());
        movieHandler.clearSearchTextField();
        movieHandler.initialize();
    }

    /**
     * remove from user preference
     * root: preference
     * sub: remove
     * payload: none
     * flag: -g (genre name -- not genre ID)
     */
    private void executeRemovePreference() throws IOException {
        MovieHandler movieHandler = ((MovieHandler)this.getUIController());
        ProfileCommands command = new ProfileCommands(movieHandler.getUserProfile());
        command.removePreference(this.getFlagMap());
        movieHandler.clearSearchTextField();
        movieHandler.initialize();
    }
}
