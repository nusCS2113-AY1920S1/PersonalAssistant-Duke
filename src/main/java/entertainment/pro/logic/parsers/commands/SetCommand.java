package entertainment.pro.logic.parsers.commands;

import entertainment.pro.commons.exceptions.InvalidFormatCommandException;
import entertainment.pro.commons.exceptions.InvalidParameterException;
import entertainment.pro.commons.exceptions.NoPermissionException;
import entertainment.pro.commons.exceptions.logic.SetExceptions;
import entertainment.pro.model.UserProfile;
import entertainment.pro.storage.utils.EditProfileJson;
import entertainment.pro.storage.user.ProfileCommands;
import entertainment.pro.ui.Controller;
import entertainment.pro.ui.MovieHandler;
import entertainment.pro.storage.user.WatchlistHandler;
import entertainment.pro.commons.enums.COMMANDKEYS;
import entertainment.pro.logic.parsers.CommandStructure;
import entertainment.pro.logic.parsers.CommandSuper;

import java.io.IOException;

public class SetCommand extends CommandSuper {
    public SetCommand(Controller uicontroller) {
        super(COMMANDKEYS.SET, CommandStructure.cmdStructure.get(COMMANDKEYS.SET), uicontroller);
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
            if (getQuery.equals("1")) {
                commands.setSort(true, false, false);
            } else if (getQuery.equals("2")) {
                commands.setSort(false, true, false);
            } else if (getQuery.equals("3")) {
                commands.setSort(false, false, true);
            }
            movieHandler.setLabels();
        } catch (InvalidParameterException | IOException e) {
            movieHandler.setGeneralFeedbackText(e.getMessage());
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
            command.setName(this.getPayload());
            movieHandler.setLabels();
        } catch (InvalidFormatCommandException | IOException e) {
            movieHandler.setGeneralFeedbackText(e.getMessage());
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
            command.setAge(this.getPayload());
            movieHandler.setLabels();
        } catch (InvalidParameterException | InvalidFormatCommandException | IOException e) {
            movieHandler.setGeneralFeedbackText(e.getMessage());
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
            UserProfile userProfile = new EditProfileJson().load();
            SetExceptions.checkPreferenceCommand(this.getFlagMap(), userProfile);
            ProfileCommands command = new ProfileCommands(movieHandler.getUserProfile());
            command.setPreference(this.getFlagMap());
            movieHandler.setLabels();
        } catch (InvalidParameterException | NoPermissionException | InvalidFormatCommandException e) {
            movieHandler.setGeneralFeedbackText(e.getMessage());
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
            UserProfile userProfile = new EditProfileJson().load();
            SetExceptions.checkRestrictionCommand(this.getFlagMap(), userProfile);
            ProfileCommands command = new ProfileCommands(movieHandler.getUserProfile());
            command.setRestriction(this.getFlagMap());
            movieHandler.setLabels();
        } catch (InvalidFormatCommandException | InvalidParameterException e) {
            movieHandler.setGeneralFeedbackText(e.getMessage());
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