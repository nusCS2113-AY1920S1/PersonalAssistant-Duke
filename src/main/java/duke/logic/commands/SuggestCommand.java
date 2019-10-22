package duke.logic.commands;

import duke.commons.exceptions.DukeException;
import duke.logic.suggestion.MealSuggestionAnalytics;
import duke.model.MealList;
import duke.model.user.User;
import duke.storage.Storage;
import duke.ui.Ui;

import java.util.Scanner;

public class SuggestCommand extends Command {
    @Override
    public void execute(MealList tasks, Ui ui, Storage storage, User user, Scanner in) throws DukeException {
        /*
        TODO: analyze the list of SuggestMeal objects as well as the current calorie goal of the
              user, the date provided and the user meal parameters provided to get the best meal
              suggestion.
        */
        MealSuggestionAnalytics mealSuggestionAnalytics = new MealSuggestionAnalytics();
    }
}
