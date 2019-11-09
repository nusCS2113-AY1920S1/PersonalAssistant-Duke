package entertainment.pro.logic.parsers.commands;

import entertainment.pro.commons.PromptMessages;
import entertainment.pro.commons.exceptions.InvalidFormatCommandException;
import entertainment.pro.storage.utils.ProfileCommands;
import entertainment.pro.ui.Controller;
import entertainment.pro.ui.MovieHandler;
import entertainment.pro.commons.enums.COMMANDKEYS;
import entertainment.pro.logic.parsers.CommandStructure;
import entertainment.pro.logic.parsers.CommandSuper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * This class is called when user wants to edit preferences.
 * Input entered by the user starts with 'preference'
 */
public class PreferenceCommand extends CommandSuper {

    private static String GET_NEW_GENRE_PREF = "-g";
    private static String GET_NEW_GENRE_RESTRICT = "-r";
    private static String GET_NEW_SORT = "-s";
    private static String GET_NEW_ADULT_RATING = "-a";
    ArrayList<String> containsPossibleInputs = new ArrayList<>();
    List<String> flagList = Arrays.asList( GET_NEW_GENRE_PREF, GET_NEW_GENRE_RESTRICT, GET_NEW_SORT, GET_NEW_ADULT_RATING);


    /**
     * Constructor for Command Super class.
     * @param uiController Controller Class.
     */
    public PreferenceCommand(Controller uiController) {
        super(COMMANDKEYS.PREFERENCE, CommandStructure.cmdStructure.get(COMMANDKEYS.PREFERENCE), uiController);
    }

    /**
     * Responsible for adding possible valid user inputs to the arraylist.
     */
    private void setContainsInputs() {
        containsPossibleInputs.addAll(flagList);
    }

    /**
     * Called when user wants to edit preferences set on the app.
     * @throws InvalidFormatCommandException when the input entered by the user is invalid.
     * @throws IOException
     */
    @Override
    public void executeCommands() throws InvalidFormatCommandException, IOException {
        setContainsInputs();
        MovieHandler movieHandler = ((MovieHandler) this.getUiController());
        for (Map.Entry<String, ArrayList<String>> entry : getFlagMap().entrySet()) {
            boolean isValidFlag = false;
            String k = entry.getKey();
            for (int i = 0; i < containsPossibleInputs.size(); i += 1) {
                if (k.equals(containsPossibleInputs.get(i))) {
                    isValidFlag = true;
                    break;
                }
            }
            if (!isValidFlag) {
                ((MovieHandler) this.getUiController()).setGeneralFeedbackText(PromptMessages.INVALID_FORMAT);
                throw new InvalidFormatCommandException();
            }
        }
        switch (this.getSubRootCommand()) {
        case ADD:
            executeAddPreference(containsPossibleInputs, movieHandler);
            break;
        case REMOVE:
            executeRemovePreference(containsPossibleInputs, movieHandler);
            break;
        case CLEAR:
            executeClearPreference(containsPossibleInputs, movieHandler);
            break;
        default:
            ((MovieHandler) this.getUiController()).setGeneralFeedbackText(PromptMessages.INVALID_FORMAT);
            throw new InvalidFormatCommandException();
        }
        movieHandler.clearSearchTextField();
        System.out.println("this is done1");
        movieHandler.setLabels();
        System.out.println("this is done2");
    }


    /**
     * Called when user wants to add elements to preference categories.
     * @param containsPossibleInputs ArrayList containing the possible categories, user want to add elements.
     * @param movieHandler MovieHandler class
     * @throws IOException
     * @throws InvalidFormatCommandException when the input entered by the user is invalid.
     */
    private void executeAddPreference(ArrayList<String> containsPossibleInputs, MovieHandler movieHandler)
            throws IOException {
        ProfileCommands command = new ProfileCommands(movieHandler.getUserProfile());
        for (int i = 0; i < containsPossibleInputs.size(); i += 1) {
            if (getFlagMap().containsKey(containsPossibleInputs.get(i))) {
                try {
                    command.addPreference(this.getFlagMap(), containsPossibleInputs.get(i));
                    movieHandler.setGeneralFeedbackText(PromptMessages.PREFERENCES_SUCCESS);
                } catch (InvalidFormatCommandException InvalidFormatCommandException) {
                    ((MovieHandler) this.getUiController()).setGeneralFeedbackText(PromptMessages.INVALID_FORMAT);
                    movieHandler.setGeneralFeedbackText(PromptMessages.INVALID_FORMAT);
                }
            }
        }
    }

    /**
     * Called when user wants to remove elements from preference categories.
     * @param containsPossibleInputs ArrayList containing the possible categories, user want to remove elements.
     * @param movieHandler MovieHandler class
     * @throws IOException
     * @throws InvalidFormatCommandException when the input entered by the user is invalid.
     */
    private void executeRemovePreference(ArrayList<String> containsPossibleInputs, MovieHandler movieHandler)
            throws IOException {
        ProfileCommands command = new ProfileCommands(movieHandler.getUserProfile());
        for (int i = 0; i < containsPossibleInputs.size(); i += 1) {
            if (getFlagMap().containsKey(containsPossibleInputs.get(i))) {
                try {
                    command.removePreference(this.getFlagMap(), containsPossibleInputs.get(i));
                    movieHandler.setGeneralFeedbackText(PromptMessages.PREFERENCES_SUCCESS);
                } catch (InvalidFormatCommandException InvalidFormatCommandException) {
                    ((MovieHandler) this.getUiController()).setGeneralFeedbackText(PromptMessages.INVALID_FORMAT);
                    movieHandler.setGeneralFeedbackText(PromptMessages.INVALID_FORMAT);
                }
            }
        }
    }

    /**
     * Called when user wants to clear elements from preference categories.
     * @param containsPossibleInputs ArrayList containing the possible categories, user want to clear elements.
     * @param movieHandler MovieHandler class
     * @throws IOException
     * @throws InvalidFormatCommandException when the input entered by the user is invalid.
     */
    private void executeClearPreference(ArrayList<String> containsPossibleInputs,
                                        MovieHandler movieHandler) throws IOException {
        ProfileCommands command = new ProfileCommands(movieHandler.getUserProfile());
        for (int i = 0; i < containsPossibleInputs.size(); i += 1) {
            if (getFlagMap().containsKey(containsPossibleInputs.get(i))) {
                try {
                    command.clearPreference(this.getFlagMap(), containsPossibleInputs.get(i));
                    movieHandler.setGeneralFeedbackText(PromptMessages.PREFERENCES_SUCCESS);
                } catch (InvalidFormatCommandException InvalidFormatCommandException) {
                    ((MovieHandler) this.getUiController()).setGeneralFeedbackText(PromptMessages.INVALID_FORMAT);
                    movieHandler.setGeneralFeedbackText(PromptMessages.INVALID_FORMAT);
                }
            }
        }
    }
}