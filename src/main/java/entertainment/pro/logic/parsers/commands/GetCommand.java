package entertainment.pro.logic.parsers.commands;

import entertainment.pro.commons.exceptions.Exceptions;
import entertainment.pro.ui.Controller;
import entertainment.pro.ui.MovieHandler;
import entertainment.pro.commons.enums.COMMANDKEYS;
import entertainment.pro.logic.parsers.CommandStructure;
import entertainment.pro.logic.parsers.CommandSuper;
import entertainment.pro.model.MovieInfoObject;

import java.util.ArrayList;
import java.io.IOException;

public class GetCommand extends CommandSuper {
    private int constant = 5;

    public GetCommand(Controller uicontroller) {
        super(COMMANDKEYS.get, CommandStructure.cmdStructure.get(COMMANDKEYS.get), uicontroller);
    }

    @Override
    public void executeCommands() {
        try {
            switch (this.getSubRootCommand()) {
            case recommendation:
                //executeRecommendationCommand();
                break;
            default:
                break;
            }
        } catch (Exception e) {
            ((MovieHandler) this.getUiController()).setGeneralFeedbackText("file unable to be found");
        }
    }

    /**
     * prints out a list of recommendations based on the users set preferences.
     * @throws IOException file was not able to be found
     */
    /*
    private void executeRecommendationCommand() throws Exceptions {
        String feedback = "Your recommended movies are: \n";
        MovieHandler movieHandler = ((MovieHandler) this.getUiController());
        ArrayList<Integer> preferenceIndices = movieHandler.getUserProfile().getGenreIdPreference();
        ArrayList<MovieInfoObject>  movies = movieHandler.getAPIRequester()
                .beginSearchGenre(Integer.toString(preferenceIndices.get(0)), movieHandler.getUserProfile().isAdult());
        for (int i = 0; i < constant; i++) {
            feedback += i + 1 + ". " + movies.get(i).getTitle() + "\n";
        }
        movieHandler.setGeneralFeedbackText(feedback);
    }
    */
}