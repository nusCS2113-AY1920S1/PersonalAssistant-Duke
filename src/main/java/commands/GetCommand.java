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

    /**
     * prints out a list of recommendations based on the users preferences
     * @throws IOException: file was not able to be found
     */
    public void ExecuteRecommendationCommand() throws IOException{
        String feedback = "Your recommended movies are: \n";
        MovieHandler movieHandler = ((MovieHandler) this.getUIController());
        ArrayList<Integer> p_indices = movieHandler.getUserProfile().getGenreIdPreference();
        ProfileCommands command = new ProfileCommands(movieHandler.getUserProfile());
        ArrayList<String> preferences = new ArrayList<>();
        int index = 0;
        for (Integer i : p_indices) {
            preferences.add(command.findGenreName(i));
            System.out.println(i);
            String s_result = movieHandler.getAPIRequester().beginSearchGenre(Integer.toString(i), movieHandler.getUserProfile().isAdult());
            feedback += index + 1 + ". " + s_result + "\n";
        }
        /*
        System.out.println(preferences.get(0));
        System.out.println(preferences.size());
        for (String i : preferences) {
            String s_result = movieHandler.getAPIRequester().beginSearchGenre(i, movieHandler.getUserProfile().isAdult());
            feedback += index + 1 + ". " + s_result + "\n";
        }
        */
        movieHandler.setFeedbackText(feedback);
    }
}
