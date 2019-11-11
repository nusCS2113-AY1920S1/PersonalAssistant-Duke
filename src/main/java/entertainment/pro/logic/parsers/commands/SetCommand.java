package entertainment.pro.logic.parsers.commands;

import entertainment.pro.commons.exceptions.InvalidFormatCommandException;
import entertainment.pro.commons.exceptions.InvalidParameterException;
import entertainment.pro.commons.exceptions.NoPermissionException;
import entertainment.pro.commons.exceptions.logic.SetExceptions;
import entertainment.pro.commons.strings.PromptMessages;
import entertainment.pro.model.UserProfile;
import entertainment.pro.storage.utils.EditProfileJson;
import entertainment.pro.storage.user.ProfileCommands;
import entertainment.pro.ui.Controller;
import entertainment.pro.ui.MovieHandler;
import entertainment.pro.storage.user.WatchlistHandler;
import entertainment.pro.commons.enums.CommandKeys;
import entertainment.pro.logic.parsers.CommandStructure;
import entertainment.pro.logic.parsers.CommandSuper;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SetCommand extends CommandSuper {
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public SetCommand(Controller uiController) {
        super(CommandKeys.SET, CommandStructure.cmdStructure.get(CommandKeys.SET), uiController);
    }

    @Override
    public void executeCommands() throws IOException {
        switch (this.getSubRootCommand()) {
        case NAME:
            System.out.println("enter");
            executeSetName();
            break;
        case AGE:
            executeSetAge();
            break;
        case PREFERENCE:
            executeSetPreference();
            break;
        case WATCHLIST:
            System.out.println("enter");
            executeTaskDone();
            break;
        case RESTRICTION:
            executeSetRestriction();
            break;
        case SORT:
            executeSort();
            break;
        default:
            break;
        }
    }

    private void executeSort() {
        MovieHandler movieHandler = ((MovieHandler) this.getUiController());
        try {
            String getQuery = getPayload();
            SetExceptions.checkSortCommand(getQuery);
            ProfileCommands commands = new ProfileCommands(movieHandler.getUserProfile());
            UserProfile userProfile = movieHandler.getUserProfile();
            if (getQuery.equals("1")) {
                userProfile = commands.setSort(true, false, false);
            } else if (getQuery.equals("2")) {
                userProfile = commands.setSort(false, true, false);
            } else if (getQuery.equals("3")) {
                userProfile = commands.setSort(false, false, true);
            }
            new EditProfileJson().updateProfile(userProfile);
            movieHandler.setLabels();
            logger.log(Level.INFO, PromptMessages.SORT_SET);
        } catch (InvalidParameterException | IOException e) {
            movieHandler.setGeneralFeedbackText(e.getMessage());
            logger.log(Level.WARNING, PromptMessages.SORT_SET_ERROR);
        }
    }


    /**
     * set user's name.
     * root: set
     * sub: name
     * payload: user's name
     * flag: none
     */
    private void executeSetName() {
        MovieHandler movieHandler = ((MovieHandler) this.getUiController());
        try {
            SetExceptions.checkNameCommand(this.getPayload());
            ProfileCommands command = new ProfileCommands(movieHandler.getUserProfile());
            UserProfile userProfile = movieHandler.getUserProfile();
            userProfile = command.setName(this.getPayload());
            new EditProfileJson().updateProfile(userProfile);
            movieHandler.setLabels();
            logger.log(Level.INFO, PromptMessages.NAME_SET);
        } catch (InvalidFormatCommandException | IOException e) {
            movieHandler.setGeneralFeedbackText(e.getMessage());
            logger.log(Level.WARNING, PromptMessages.NAME_SET_ERROR);
        }
        movieHandler.clearSearchTextField();
    }

    /**
     * set user's age.
     * root: set
     * sub: age
     * payload: user's age
     * flag: none
     */
    private void executeSetAge() {
        MovieHandler movieHandler = ((MovieHandler) this.getUiController());
        try {
            SetExceptions.checkAgeCommand(this.getPayload());
            ProfileCommands command = new ProfileCommands(movieHandler.getUserProfile());
            UserProfile userProfile = movieHandler.getUserProfile();
            userProfile = command.setAge(this.getPayload());
            new EditProfileJson().updateProfile(userProfile);
            movieHandler.setLabels();
            logger.log(Level.INFO, PromptMessages.AGE_SET);
        } catch (InvalidParameterException | InvalidFormatCommandException | IOException e) {
            movieHandler.setGeneralFeedbackText(e.getMessage());
            logger.log(Level.WARNING, PromptMessages.AGE_SET_ERROR);
        }
        movieHandler.clearSearchTextField();
    }

    /**
     * set user's preferences.
     * root: set
     * sub: preference
     * payload: none
     * flag: -g (genre name -- not genre ID)
     *       -a (adult -- yes to allow adult content, no to restrict, set to yes by default)
     */
    private void executeSetPreference() throws IOException {
        MovieHandler movieHandler = ((MovieHandler) this.getUiController());
        try {
            UserProfile userProfile = movieHandler.getUserProfile();
            SetExceptions.checkPreferenceCommand(this.getFlagMap(), userProfile);
            ProfileCommands command = new ProfileCommands(userProfile);
            userProfile = command.setPreference(this.getFlagMap());
            new EditProfileJson().updateProfile(userProfile);
            movieHandler.setLabels();
            logger.log(Level.INFO, PromptMessages.PREFERENCE_SET);
        } catch (InvalidParameterException | NoPermissionException | InvalidFormatCommandException e) {
            movieHandler.setGeneralFeedbackText(e.getMessage());
            logger.log(Level.WARNING, PromptMessages.PREFERENCE_SET_ERROR);
        }
        movieHandler.clearSearchTextField();
    }

    /**
     * set user's restrictions.
     * root: set
     * sub: restriction
     * payload: none
     * flag: -g (genre name -- not genre ID)
     */
    private void executeSetRestriction() throws IOException {
        MovieHandler movieHandler = ((MovieHandler)this.getUiController());
        try {
            UserProfile userProfile = movieHandler.getUserProfile();
            SetExceptions.checkRestrictionCommand(this.getFlagMap(), userProfile);
            ProfileCommands command = new ProfileCommands(movieHandler.getUserProfile());
            userProfile = command.setRestriction(this.getFlagMap());
            new EditProfileJson().updateProfile(userProfile);
            movieHandler.setLabels();
            logger.log(Level.INFO, PromptMessages.RESTRICTION_SET);
        } catch (InvalidFormatCommandException | InvalidParameterException e) {
            movieHandler.setGeneralFeedbackText(e.getMessage());
            logger.log(Level.WARNING, PromptMessages.RESTRICTION_SET_ERROR);
        }
        movieHandler.clearSearchTextField();
    }

    /**
     * set the duke.task on the watchlist as done.
     * root: set
     * sub: watchlist
     * payload: none
     * flag: -d (index of the element in the watchlist to be marked as done)
     */
    private void executeTaskDone()  {
        try {
            String index = this.getFlagMap().get("-d").get(0);
            index = index.strip();
            int i = Integer.valueOf(index);
            System.out.println(i);
            WatchlistHandler.markIndexAsDone(i, (MovieHandler)(this.getUiController()));
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            ((MovieHandler)(this.getUiController())).setGeneralFeedbackText("please enter a valid duke.task number");
        }
    }
}