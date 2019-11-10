package entertainment.pro.logic.parsers.commands;

import entertainment.pro.commons.strings.PromptMessages;
import entertainment.pro.commons.exceptions.DuplicateGenreException;
import entertainment.pro.commons.exceptions.GenreDoesNotExistException;
import entertainment.pro.commons.exceptions.InvalidFormatCommandException;
import entertainment.pro.model.UserProfile;
import entertainment.pro.storage.user.ProfileCommands;
import entertainment.pro.commons.exceptions.InvalidGenreNameEnteredException;
import entertainment.pro.storage.utils.EditProfileJson;
import entertainment.pro.ui.Controller;
import entertainment.pro.ui.MovieHandler;
import entertainment.pro.commons.enums.COMMANDKEYS;
import entertainment.pro.logic.parsers.CommandStructure;
import entertainment.pro.logic.parsers.CommandSuper;

import javax.sound.sampled.LineEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is called when user wants to edit preferences.
 * Input entered by the user starts with 'preference'.
 */
public class PreferenceCommand extends CommandSuper {

    private static String GET_NEW_GENRE_PREF = "-g";
    private static String GET_NEW_GENRE_RESTRICT = "-r";
    private static String GET_NEW_SORT = "-s";
    private static String GET_NEW_ADULT_RATING = "-a";
    ArrayList<String> containsPossibleInputs = new ArrayList<>();
    List<String> flagList = Arrays.asList(GET_NEW_GENRE_PREF, GET_NEW_GENRE_RESTRICT, GET_NEW_SORT,
            GET_NEW_ADULT_RATING);
    private static final Logger logger = Logger.getLogger(PreferenceCommand.class.getName());


    /**
     * Constructor for Command Super class.
     *
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
     *
     * @throws InvalidFormatCommandException when the input entered by the user is invalid.
     * @throws IOException
     */
    @Override
    public void executeCommands() throws InvalidFormatCommandException, IOException {
        logger.log(Level.INFO, PromptMessages.EDIT_PREFERENCES_COMMAND);
        setContainsInputs();
        MovieHandler movieHandler = ((MovieHandler) this.getUiController());
        logger.log(Level.INFO, PromptMessages.CHECKING_FLAGS);
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
                logger.log(Level.WARNING, PromptMessages.INVALID_PREFERENCE_FLAGS);
                throw new InvalidFormatCommandException();
            }
        }
        if (getFlagMap().size() == 0) {
            ((MovieHandler) this.getUiController()).setGeneralFeedbackText(PromptMessages.INVALID_FORMAT);
            logger.log(Level.WARNING, PromptMessages.EMPTY_PREFERENCE_FLAGS);
            throw new InvalidFormatCommandException();
        }
        logger.log(Level.WARNING, PromptMessages.SUBROOT_PREFERENCE);
        switch (this.getSubRootCommand()) {
            case ADD:
                logger.log(Level.WARNING, PromptMessages.SUBROOT_PREFERENCE_ADD);
                executeAddPreference(containsPossibleInputs, movieHandler);
                break;
            case REMOVE:
                logger.log(Level.WARNING, PromptMessages.SUBROOT_PREFERENCE_REMOVE);
                executeRemovePreference(containsPossibleInputs, movieHandler);
                break;
            case CLEAR:
                logger.log(Level.WARNING, PromptMessages.SUBROOT_PREFERENCE_CLEAR);
                executeClearPreference(containsPossibleInputs, movieHandler);
                break;
            default:
                logger.log(Level.WARNING, PromptMessages.NO_SUBROOT_PREFERENCE);
                ((MovieHandler) this.getUiController()).setGeneralFeedbackText(PromptMessages.INVALID_FORMAT);
                throw new InvalidFormatCommandException();
        }
        logger.log(Level.WARNING, PromptMessages.UPDATING_INTEREFACE);
        movieHandler.setLabels();
        System.out.println("this is done2");
    }


    /**
     * Called when user wants to add elements to preference categories.
     *
     * @param containsPossibleInputs ArrayList containing the possible categories, user want to add elements.
     * @param movieHandler           MovieHandler class
     * @throws IOException
     * @throws InvalidFormatCommandException when the input entered by the user is invalid.
     */
    private void executeAddPreference(ArrayList<String> containsPossibleInputs, MovieHandler movieHandler)
            throws IOException {
        ProfileCommands command = new ProfileCommands(movieHandler.getUserProfile());
        EditProfileJson editProfileJson = new EditProfileJson();
        UserProfile userProfile = editProfileJson.load();
        for (int i = 0; i < containsPossibleInputs.size(); i += 1) {
            if (getFlagMap().containsKey(containsPossibleInputs.get(i))) {
                System.out.println("ye");
                try {
                    userProfile = command.addPreference(this.getFlagMap(), containsPossibleInputs.get(i));
                    movieHandler.setGeneralFeedbackText(PromptMessages.PREFERENCES_SUCCESS);
                } catch (InvalidFormatCommandException InvalidFormatCommandException) {
                    logger.log(Level.WARNING, PromptMessages.INVALID_FORMAT);
                    ((MovieHandler) this.getUiController()).setGeneralFeedbackText(PromptMessages.INVALID_FORMAT);
                    movieHandler.setGeneralFeedbackText(PromptMessages.INVALID_FORMAT);
                } catch (InvalidGenreNameEnteredException e) {
                    logger.log(Level.WARNING, PromptMessages.INVALID_GENRE_NAME_ERROR);
                    ((MovieHandler) this.getUiController()).setGeneralFeedbackText(PromptMessages.INVALID_GENRE_NAME);
                } catch (DuplicateGenreException e) {
                    logger.log(Level.WARNING, PromptMessages.REPETITVE_GENRE_NAME_ERROR);
                    ((MovieHandler) this.getUiController()).setGeneralFeedbackText(PromptMessages.REPETITVE_GENRE_NAME);
                }
            }
        }
        editProfileJson.updateProfile(userProfile);
        movieHandler.setLabels();
    }

    /**
     * Called when user wants to remove elements from preference categories.
     *
     * @param containsPossibleInputs ArrayList containing the possible categories, user want to remove elements.
     * @param movieHandler           MovieHandler class
     * @throws IOException
     * @throws InvalidFormatCommandException when the input entered by the user is invalid.
     */
    private void executeRemovePreference(ArrayList<String> containsPossibleInputs, MovieHandler movieHandler)
            throws IOException {
        ProfileCommands command = new ProfileCommands(movieHandler.getUserProfile());
        EditProfileJson editProfileJson = new EditProfileJson();
        UserProfile userProfile = editProfileJson.load();
        for (int i = 0; i < containsPossibleInputs.size(); i += 1) {
            if (getFlagMap().containsKey(containsPossibleInputs.get(i))) {
                try {
                    userProfile = command.removePreference(this.getFlagMap(), containsPossibleInputs.get(i));
                    movieHandler.setGeneralFeedbackText(PromptMessages.PREFERENCES_SUCCESS);
                } catch (InvalidFormatCommandException InvalidFormatCommandException) {
                    logger.log(Level.WARNING, PromptMessages.INVALID_FORMAT);
                    ((MovieHandler) this.getUiController()).setGeneralFeedbackText(PromptMessages.INVALID_FORMAT);
                    movieHandler.setGeneralFeedbackText(PromptMessages.INVALID_FORMAT);
                } catch (GenreDoesNotExistException e) {
                    logger.log(Level.WARNING, PromptMessages.GENRE_DOES_NOT_EXIST_ERROR);
                    ((MovieHandler) this.getUiController()).setGeneralFeedbackText(PromptMessages.GENRE_DOES_NOT_EXIST);
                } catch (InvalidGenreNameEnteredException e) {
                    logger.log(Level.WARNING, PromptMessages.INVALID_GENRE_NAME_ERROR);
                    ((MovieHandler) this.getUiController()).setGeneralFeedbackText(PromptMessages.INVALID_GENRE_NAME);
                }
            }
        }
        editProfileJson.updateProfile(userProfile);
        movieHandler.setLabels();
    }

    /**
     * Called when user wants to clear elements from preference categories.
     *
     * @param containsPossibleInputs ArrayList containing the possible categories, user want to clear elements.
     * @param movieHandler           MovieHandler class
     * @throws IOException
     * @throws InvalidFormatCommandException when the input entered by the user is invalid.
     */
    private void executeClearPreference(ArrayList<String> containsPossibleInputs,
                                        MovieHandler movieHandler) throws IOException {
        ProfileCommands command = new ProfileCommands(movieHandler.getUserProfile());
        EditProfileJson editProfileJson = new EditProfileJson();
        UserProfile userProfile = editProfileJson.load();
        for (int i = 0; i < containsPossibleInputs.size(); i += 1) {
            if (getFlagMap().containsKey(containsPossibleInputs.get(i))) {
                try {
                    userProfile = command.clearPreference(this.getFlagMap(), containsPossibleInputs.get(i));
                    movieHandler.setGeneralFeedbackText(PromptMessages.PREFERENCES_SUCCESS);
                } catch (InvalidFormatCommandException InvalidFormatCommandException) {
                    logger.log(Level.WARNING, PromptMessages.INVALID_FORMAT);
                    ((MovieHandler) this.getUiController()).setGeneralFeedbackText(PromptMessages.INVALID_FORMAT);
                    movieHandler.setGeneralFeedbackText(PromptMessages.INVALID_FORMAT);
                }
            }
        }
        editProfileJson.updateProfile(userProfile);
        movieHandler.setLabels();
    }
}