package duke.logic.commands;

import duke.logic.suggestion.MealSuggestionAnalytics;
import duke.model.meal.Meal;
import duke.model.meal.MealList;
import duke.model.user.User;
import duke.model.wallet.Wallet;
import duke.storage.Storage;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * Class to handle suggestion command arguments from the user and pass them to the analytic module.
 */
public class SuggestCommand extends Command {

    private int maxMealsToSuggest;
    private MealSuggestionAnalytics mealSuggestionAnalytics;
    private AddCommand addCommand;
    private ArrayList<Meal> suggestedMealList;

    // TODO: Support for meal types (eg: lunch, dinner) from user so that only relevant meals suggested.

    /**
     * Constructor of suggestion command.
     * @param suggestionDate Date on which meal suggestion is required.
     * @param maxMealsToSuggest Maximum number of suggested meals to be shown to the user.
     */
    public SuggestCommand(Calendar suggestionDate, int maxMealsToSuggest) {
        this.calendarDate = suggestionDate;
        this.currentDateStr = dateFormat.format(this.calendarDate.getTime());
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

        mealSuggestionAnalytics = new MealSuggestionAnalytics();
        suggestedMealList = mealSuggestionAnalytics.getMealSuggestions(meals, calendarDate, maxMealsToSuggest);

        if (suggestedMealList.size() > 0) {
            ui.showSuggestedMealList(suggestedMealList, currentDateStr);
            // Allow followup user action after meals are suggested.
            isDone = false;
        } else {
            ui.showMessage("No meals could be suggested by DIYeats");
            ui.showLine();
            isDone = true;
        }

    }

    @Override
    public void execute2(MealList meals, Storage storage, User user, Wallet wallet) {
        int mealSelectedIndex;
        try {
            mealSelectedIndex = Integer.parseInt(this.responseStr);
        } catch (NumberFormatException e) {
            ui.showMessage("Could not parse " + responseStr + " as a number. Please input an integer.");
            return;
        }

        if (1 > mealSelectedIndex || mealSelectedIndex > suggestedMealList.size()) {
            ui.showMessage("Index out of bounds. Please try again and enter index (inclusive)" +
                    " between 1 and " + suggestedMealList.size());
            return;
        }

        Meal chosenMeal = suggestedMealList.get(mealSelectedIndex + 1);
        // TODO: Fix cost of meal
        addCommand = new AddCommand(chosenMeal, 0);
        addCommand.execute(meals, storage, user, wallet);
    }
}
