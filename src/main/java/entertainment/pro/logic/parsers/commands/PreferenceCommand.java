package entertainment.pro.logic.parsers.commands;

import entertainment.pro.storage.utils.ProfileCommands;
import entertainment.pro.ui.Controller;
import entertainment.pro.ui.MovieHandler;
import entertainment.pro.commons.enums.COMMANDKEYS;
import entertainment.pro.logic.parsers.CommandStructure;
import entertainment.pro.logic.parsers.CommandSuper;

import java.io.IOException;
import java.util.ArrayList;

public class PreferenceCommand extends CommandSuper {

    private static String GET_NEW_GENRE_PREF = "-g";
    private static String GET_NEW_GENRE_RESTRICT = "-r";
    private static String GET_NEW_SORT = "-s";
    private static String GET_NEW_ADULT_RATING = "-a";
    ArrayList<String> containsPossibleInputs = new ArrayList<>();


    public PreferenceCommand(Controller uiController) {
        super(COMMANDKEYS.preference, CommandStructure.cmdStructure.get(COMMANDKEYS.preference), uiController);
    }

    private void setContainsInputs() {
        containsPossibleInputs.add(GET_NEW_GENRE_PREF);
        containsPossibleInputs.add(GET_NEW_GENRE_RESTRICT);
        containsPossibleInputs.add(GET_NEW_ADULT_RATING);
        containsPossibleInputs.add(GET_NEW_SORT);
    }

    @Override
    public void executeCommands() throws IOException {
        setContainsInputs();
        MovieHandler movieHandler = ((MovieHandler) this.getUiController());
        switch (this.getSubRootCommand()) {
        case add:
            executeAddPreference(containsPossibleInputs, movieHandler);
            break;
        case remove:
            executeRemovePreference(containsPossibleInputs, movieHandler);
            break;
        case clear:
            executeClearPreference(containsPossibleInputs, movieHandler);
            break;
        default:
            break;
        }
        movieHandler.clearSearchTextField();
        System.out.println("this is done1");
        movieHandler.setLabels();
        System.out.println("this is done2");
    }


    /**
     * add to user preference.
     * root: preference
     * sub: add
     * payload: none
     * flag: -g (genre name -- not genre ID)
     */
    private void executeAddPreference(ArrayList<String> containsPossibleInputs,
                                      MovieHandler movieHandler) throws IOException {
        ProfileCommands command = new ProfileCommands(movieHandler.getUserProfile());
        for (int i = 0; i < containsPossibleInputs.size(); i += 1) {
            if (getFlagMap().containsKey(containsPossibleInputs.get(i))) {
                command.addPreference(this.getFlagMap(), containsPossibleInputs.get(i));
            }
        }
    }

    /**
     * remove from user preference.
     * root: preference
     * sub: remove
     * payload: none
     * flag: -g (genre name -- not genre ID)
     */
    private void executeRemovePreference(ArrayList<String> containsPossibleInputs,
                                         MovieHandler movieHandler) throws IOException {
        ProfileCommands command = new ProfileCommands(movieHandler.getUserProfile());
        for (int i = 0; i < containsPossibleInputs.size(); i += 1) {
            if (getFlagMap().containsKey(containsPossibleInputs.get(i))) {
                command.removePreference(this.getFlagMap(), containsPossibleInputs.get(i));
            }
        }

    }

    /**
     * clear all preference that was set previously.
     * root: preference
     * sub: clear
     * payload: none
     * flag: -g (genre name -- not genre ID)
     *       -a (adult -- yes to allow adult content, no to restrict, set to yes by default)
     */
    private void executeClearPreference(ArrayList<String> containsPossibleInputs,
                                        MovieHandler movieHandler) throws IOException {
        ProfileCommands command = new ProfileCommands(movieHandler.getUserProfile());
        for (int i = 0; i < containsPossibleInputs.size(); i += 1) {
            if (getFlagMap().containsKey(containsPossibleInputs.get(i))) {
                command.clearPreference(this.getFlagMap(), containsPossibleInputs.get(i));
            }
        }

    }
}