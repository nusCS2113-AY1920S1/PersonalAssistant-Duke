package duke.logic.commands;

import duke.logic.suggestion.MealSuggestionAnalytics;
import duke.model.meal.MealList;
import duke.model.user.User;
import duke.model.wallet.Wallet;
import duke.storage.Storage;
import java.util.Calendar;


/**
 * Class to handle suggestion command arguments from the user and pass them to the analytic module.
 */
public class SuggestCommand extends Command {

    private int maxMealsToSuggest;

    // TODO: Support for meal types (eg: lunch, dinner) from user so that only relevant meals suggested.

    /**
     * Constructor of suggestion command.
     * @param suggestionDate Date on which meal suggestion is required.
     * @param maxMealsToSuggest Maximum number of suggested meals to be shown to the user.
     */
    public SuggestCommand(Calendar suggestionDate, int maxMealsToSuggest) {
        this.calendarDate = suggestionDate;
        this.maxMealsToSuggest = maxMealsToSuggest;
    }

    // Constructor called when parser fails to parse arguments
    public SuggestCommand(boolean flag, String messageStr) {
        this.isFail = flag;
        this.errorStr = messageStr;
    }

    @Override
    public void execute(MealList meals, Storage storage, User user, Wallet wallet) {
        /*
        TODO: analyze the list of SuggestMeal objects as well as the current calorie goal of the
              user, the date provided and the user meal parameters provided to get the best meal
              suggestion.
        */
        // Allow followup user action after meals are suggested.
        // isDone = false;
        MealSuggestionAnalytics mealSuggestionAnalytics = new MealSuggestionAnalytics();
    }

    @Override
    public void execute2(MealList meals, Storage storage, User user, Wallet wallet) {

    }
}
