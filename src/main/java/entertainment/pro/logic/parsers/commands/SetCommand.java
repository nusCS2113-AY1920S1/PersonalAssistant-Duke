package entertainment.pro.logic.parsers.commands;

import entertainment.pro.commons.exceptions.SetExceptions;
import entertainment.pro.storage.utils.ProfileCommands;
import entertainment.pro.ui.Controller;
import entertainment.pro.ui.MovieHandler;
import entertainment.pro.storage.user.WatchlistHandler;
import entertainment.pro.commons.enums.COMMANDKEYS;
import entertainment.pro.logic.parsers.CommandStructure;
import entertainment.pro.logic.parsers.CommandSuper;

import java.io.IOException;

public class SetCommand extends CommandSuper {
    public SetCommand(Controller uicontroller) {
        super(COMMANDKEYS.set, CommandStructure.cmdStructure.get(COMMANDKEYS.set), uicontroller);
    }

    @Override
    public void executeCommands() throws IOException {
        switch (this.getSubRootCommand()) {
        case name:
            System.out.println("enter");
            executeSetName();
            break;
        case age:
            executeSetAge();
            break;
        case preference:
            executeSetPreference();
            break;
        case watchlist:
            System.out.println("enter");
            executeTaskDone();
            break;
        case restriction:
            executeSetRestriction();
            break;
        case sort:
            executeSort();
            break;
        default:
            break;
        }
    }

    private void executeSort() throws IOException {
        MovieHandler movieHandler = ((MovieHandler) this.getUiController());
        String getQuery = getPayload();
        ProfileCommands commands = new ProfileCommands(movieHandler.getUserProfile());
        if (getQuery.equals("1")) {
            commands.setSort(true, false, false);
        } else if (getQuery.equals("2")) {
            commands.setSort(false, true, false);
        } else if (getQuery.equals("3")) {
            commands.setSort(false, false, true);
        }
    }


    /**
     * set user's name.
     * root: set
     * sub: name
     * payload: user's name
     * flag: none
     */
    private void executeSetName() throws IOException {
        MovieHandler movieHandler = ((MovieHandler) this.getUiController());
        ProfileCommands command = new ProfileCommands(movieHandler.getUserProfile());
        command.setName(this.getPayload());
        movieHandler.clearSearchTextField();
        movieHandler.setLabels();
    }

    /**
     * set user's age.
     * root: set
     * sub: age
     * payload: user's age
     * flag: none
     */
    private void executeSetAge() throws IOException {
        MovieHandler movieHandler = ((MovieHandler) this.getUiController());
        ProfileCommands command = new ProfileCommands(movieHandler.getUserProfile());
        try {
            SetExceptions.checkAgeInput(this.getPayload());
            command.setAge(this.getPayload());
            movieHandler.setLabels();
        } catch (SetExceptions e) {
            System.out.println(e);
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
        ProfileCommands command = new ProfileCommands(movieHandler.getUserProfile());
        command.setPreference(this.getFlagMap());
        movieHandler.clearSearchTextField();
        movieHandler.setLabels();
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
        ProfileCommands command = new ProfileCommands(movieHandler.getUserProfile());
        command.setRestriction(this.getFlagMap());
        movieHandler.clearSearchTextField();
        movieHandler.setLabels();
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