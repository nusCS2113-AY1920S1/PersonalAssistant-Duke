package diyeats.logic.commands;

import diyeats.logic.suggestion.MealSuggestionAnalytics;
import diyeats.model.meal.Meal;
import diyeats.model.meal.MealList;
import diyeats.model.user.User;
import diyeats.model.wallet.Wallet;
import diyeats.storage.Storage;

import java.time.LocalDate;
import java.util.ArrayList;

//@@author HashirZahir
/**
 * Class to handle suggestion command arguments from the user and pass them to the analytic module.
 */
public class SuggestMealCommand extends Command {

    private int maxMealsToSuggest;
    private String mealSuggestionTypeStr;
    private MealSuggestionAnalytics mealSuggestionAnalytics;
    private AddCommand addCommand;
    private ArrayList<Meal> suggestedMealList;
    private LocalDate suggestionDate;

    // TODO: Support for meal types (eg: lunch, dinner) from user so that only relevant meals suggested.

    /**
     * Constructor of suggestion command.
     * @param suggestionDate Date on which meal suggestion is required.
     * @param maxMealsToSuggest Maximum number of suggested meals to be shown to the user.
     */
    public SuggestMealCommand(LocalDate suggestionDate, int maxMealsToSuggest, String mealTypeStr) {
        this.suggestionDate = suggestionDate;
        this.maxMealsToSuggest = maxMealsToSuggest;
        this.mealSuggestionTypeStr = mealTypeStr;
    }

    // Constructor called when parser fails to parse arguments
    public SuggestMealCommand(boolean flag, String messageStr) {
        this.isFail = flag;
        this.errorStr = messageStr;
    }

    private int getCalorieLimit(User user, ArrayList<Meal> meals) {
        int totalConsume = 0;
        for (int i = 0; i < meals.size(); i += 1) {
            // add all meals regardless whether it is done or not.
            totalConsume += meals.get(i).getNutritionalValue().get("calorie");
        }

        return user.getDailyCalorie() - totalConsume;
    }

    @Override
    public void execute(MealList meals, Storage storage, User user, Wallet wallet) {
        switch (stage) {
            case 0:
                execute_stage_0(meals, storage, user, wallet);
                stage++;
                break;
            case 1:
                execute_stage_1(meals, storage, user, wallet);
                break;
            default:
                isDone = true;
        }
    }

    public void execute_stage_0(MealList meals, Storage storage, User user, Wallet wallet) {
        mealSuggestionAnalytics = new MealSuggestionAnalytics();
        int calorieLimit = getCalorieLimit(user, meals.getMealsList(currentDate));
        suggestedMealList = mealSuggestionAnalytics.getMealSuggestions(meals, suggestionDate, calorieLimit,
                                                                        maxMealsToSuggest, mealSuggestionTypeStr);

        if (suggestedMealList.size() > 0) {
            ui.showSuggestedMealList(suggestedMealList, currentDate);
            // Allow followup user action after meals are suggested.
            isDone = false;
        } else {
            ui.showMessage("No meals could be suggested by DIYeats");
            ui.showLine();
            isDone = true;
        }

    }

    // second stage user input execution
    public void execute_stage_1(MealList meals, Storage storage, User user, Wallet wallet) {
        int mealSelectedIndex;
        try {
            mealSelectedIndex = Integer.parseInt(this.responseStr);
        } catch (NumberFormatException e) {
            ui.showMessage("Could not parse " + responseStr + " as a number. Please input an integer.");
            return;
        }

        if (mealSelectedIndex == 0) {
            ui.showMessage("Declining suggestions.");
            ui.showLine();
            isDone = true;
            return;
        } else if (1 > mealSelectedIndex || mealSelectedIndex > suggestedMealList.size()) {
            ui.showMessage("Index out of bounds. Please try again and enter index (inclusive)"
                    + " between 1 and " + suggestedMealList.size());
            return;
        }

        Meal chosenMeal = suggestedMealList.get(mealSelectedIndex - 1);
        // TODO: Fix cost of meal
        addCommand = new AddCommand(chosenMeal);
        addCommand.execute(meals, storage, user, wallet);
        isDone = true;
    }
}
