package entertainment.pro.logic.parsers.commands;

import entertainment.pro.storage.utils.ProfileCommands;
import entertainment.pro.ui.Controller;
import entertainment.pro.ui.MovieHandler;
import entertainment.pro.commons.enums.COMMANDKEYS;
import entertainment.pro.logic.parsers.CommandStructure;
import entertainment.pro.logic.parsers.CommandSuper;

import java.io.IOException;

public class PreferenceCommand extends CommandSuper {
    public PreferenceCommand(Controller uicontroller) {
        super(COMMANDKEYS.preference, CommandStructure.cmdStructure.get(COMMANDKEYS.preference) , uicontroller);
    }

    @Override
    public void executeCommands() throws IOException {
        switch (this.getSubRootCommand()) {
            case add:
                executeAddPreference();
                break;
            case remove:
                executeRemovePreference();
                break;
            case clear:
                executeClearPreference();
                break;
            default:
                break;
        }
    }

    /**
     * add to user preference.
     * root: preference
     * sub: add
     * payload: none
     * flag: -g (genre name -- not genre ID)
     */
    private void executeAddPreference() throws IOException {
        MovieHandler movieHandler = ((MovieHandler) this.getUIController());
        ProfileCommands command = new ProfileCommands(movieHandler.getUserProfile());
        command.addPreference(this.getFlagMap());
        movieHandler.clearSearchTextField();
        movieHandler.setLabels();
    }

    /**
     * remove from user preference.
     * root: preference
     * sub: remove
     * payload: none
     * flag: -g (genre name -- not genre ID)
     */
    private void executeRemovePreference() throws IOException {
        MovieHandler movieHandler = ((MovieHandler) this.getUIController());
        ProfileCommands command = new ProfileCommands(movieHandler.getUserProfile());
        command.removePreference(this.getFlagMap());
        movieHandler.clearSearchTextField();
        movieHandler.setLabels();
    }

    /**
     * clear all preference that was set previously
     * root: preference
     * sub: clear
     * payload: none
     * flag: -g (genre name -- not genre ID) -a (adult -- yes to allow adult content, no to restrict, set to yes by default)
     */
    private void executeClearPreference() throws IOException {
        MovieHandler movieHandler = ((MovieHandler)this.getUIController());
        ProfileCommands command = new ProfileCommands(movieHandler.getUserProfile());
        command.clearPreference(this.getFlagMap());
        movieHandler.clearSearchTextField();
        movieHandler.setLabels();
    }
}