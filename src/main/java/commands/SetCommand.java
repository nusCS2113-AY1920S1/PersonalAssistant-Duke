package commands;

import EPstorage.ProfileCommands;
import MovieUI.Controller;
import MovieUI.MovieHandler;
import ListCommands.WatchlistHandler;
import java.io.IOException;

public class SetCommand extends CommandSuper {
    public SetCommand(Controller uicontroller) {
        super(COMMANDKEYS.set, CommandStructure.cmdStructure.get(COMMANDKEYS.set) , uicontroller);
    }

    @Override
    public void executeCommands() throws IOException {
        switch (this.getSubRootCommand()){
            case name:
                executeSetName();
                break;
            case age:
                System.out.println("enter");
                executeSetAge();
                break;
            case preference:
                executeSetPreference();
                break;
            case watchlist:
                executeTaskDone();
            case restriction:
                executeSetRestriction();
                break;
            default:
                break;
        }
    }

    /**
     * set user's name.
     * root: set
     * sub: name
     * payload: <user's name>
     * flag: none
     */
    private void executeSetName() throws IOException {
        MovieHandler movieHandler = ((MovieHandler) this.getUIController());
        ProfileCommands command = new ProfileCommands(movieHandler.getUserProfile());
        command.setName(this.getPayload());
        movieHandler.clearSearchTextField();
        movieHandler.setLabels();
    }

    /**
     * set user's age.
     * root: set
     * sub: age
     * payload: <user's age>
     * flag: none
     */
    private void executeSetAge() throws IOException {
        MovieHandler movieHandler = ((MovieHandler) this.getUIController());
        ProfileCommands command = new ProfileCommands(movieHandler.getUserProfile());
        command.setAge(this.getPayload());
        movieHandler.clearSearchTextField();
        movieHandler.setLabels();
    }

    /**
     * set user's preferences.
     * root: set
     * sub: preference
     * payload: none
     * flag: -g (genre name -- not genre ID) -a (adult -- yes to allow adult content, no to restrict, set to yes by default)
     */
    private void executeSetPreference() throws IOException {
        MovieHandler movieHandler = ((MovieHandler) this.getUIController());
        ProfileCommands command = new ProfileCommands(movieHandler.getUserProfile());
        command.setPreference(this.getFlagMap());
        movieHandler.clearSearchTextField();
        movieHandler.setLabels();
    }

    /**
     * set user's restrictions
     * root: set
     * sub: restriction
     * payload: none
     * flag: -g (genre name -- not genre ID)
     */
    private void executeSetRestriction() throws IOException {
        MovieHandler movieHandler = ((MovieHandler)this.getUIController());
        ProfileCommands command = new ProfileCommands(movieHandler.getUserProfile());
        command.setRestriction(this.getFlagMap());
        movieHandler.clearSearchTextField();
        movieHandler.setLabels();
    }
    private void executeTaskDone()  {
        try {
            String index = this.getFlagMap().get("-d").get(0);
            index = index.strip();
            int i = Integer.valueOf(index);
            System.out.println(i);
            WatchlistHandler.markAsDone(i);
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            ((MovieHandler)(this.getUIController())).setFeedbackText("please enter a valid task number");
        }
    }
}
