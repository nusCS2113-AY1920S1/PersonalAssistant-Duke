package commands;

import EPstorage.ProfileCommands;
import MovieUI.Controller;
import MovieUI.MovieHandler;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import java.io.IOException;

public class GetCommand extends CommandSuper {
    public GetCommand(Controller uicontroller) {
        super(COMMANDKEYS.get, CommandStructure.cmdStructure.get(COMMANDKEYS.get), uicontroller);
    }
    @Override
    public void executeCommands() {
        try {
            switch(this.getSubRootCommand()) {
                case recommendation:
                    ExecuteRecommendationCommand();
                    break;
                default:
                    break;
            }
        } catch (IOException e) {
            ((MovieHandler) this.getUIController()).setFeedbackText("file unable to be found");
        }
    }

    public void ExecuteRecommendationCommand() throws IOException {
        MovieHandler movieHandler = ((MovieHandler) this.getUIController());
        ArrayList<Integer> p_indices = movieHandler.getUserProfile().getGenreIdPreference();
        ProfileCommands command = new ProfileCommands(movieHandler.getUserProfile());
        ArrayList<String> preferences = new ArrayList<>();
        for (Integer i: p_indices) {
            preferences.add(command.findGenreName(i));
        }

    }
}
