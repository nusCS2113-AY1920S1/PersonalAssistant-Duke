package duke.logic.commands;

import duke.logic.suggestion.MealSuggestionAnalytics;
import duke.model.meal.MealList;
import duke.model.user.User;
import duke.model.wallet.Wallet;
import duke.storage.Storage;

public class SuggestMealCommand extends Command {
    @Override
    public void execute(MealList meals, Storage storage, User user, Wallet wallet) {
        /*
        TODO: analyze the list of SuggestMeal objects as well as the current calorie goal of the
              user, the date provided and the user meal parameters provided to get the best meal
              suggestion.
        */
        MealSuggestionAnalytics mealSuggestionAnalytics = new MealSuggestionAnalytics();
    }
}
