package diyeats.logic.commands;

import diyeats.commons.datatypes.Pair;
import diyeats.commons.exceptions.ProgramException;
import diyeats.logic.suggestion.ExerciseSuggestionHandler;
import diyeats.model.meal.MealList;
import diyeats.model.user.User;
import diyeats.model.wallet.Wallet;
import diyeats.storage.Storage;

import java.time.LocalDate;
import java.util.ArrayList;

import static diyeats.commons.constants.DateConstants.LOCAL_DATE_FORMATTER;


//@@author Fractalisk

/**
 * TODO: analyze the list of SuggestExercise objects as well as the current calorie goal of the
 * user, the date provided and the user meal parameters provided to get the best exercise suggestion.
 * TONOTE: calories burned from exercise is directly related to BMI, eg, a 200kg man running for 10 mins will burn
 * as many calories as a 100kg man running for 20 mins
 * Exercise needed to elevate activity level = user.getDailyCalorie * user.gerActivityLevelDifference();
 * Exercise is defined in metabolic unit at rest(MET), eg, 1 hr of running which is 14.5 MET
 * is equal to 14.5 hours of static calorie loss.
 */
public class SuggestExerciseCommand extends Command {
    private LocalDate date;
    private String keyword = null;
    private ExerciseSuggestionHandler exerciseSuggestionHandler;

    public SuggestExerciseCommand(LocalDate date, String keyword) {
        this.date = date;
        this.keyword = keyword;
    }

    public SuggestExerciseCommand(boolean flag, String message) {
        this.isFail = true;
        this.errorStr = message;
    }

    @Override
    public void execute(MealList meals, Storage storage, User user, Wallet wallet) {
        isDone = false;
        switch (stage) {
            case 0:
                exerciseSuggestionHandler = new ExerciseSuggestionHandler(user);
                execute_stage_0(meals, user);
                stage++;
                break;
            case 1:
                execute_stage_1(meals, storage);
                break;
            default:
                isDone = true;
        }
    }

    private void execute_stage_0(MealList meals, User user) {
        if (user.getGoal() == null) {
            ui.goalNotFound();
            isDone = true;
        } else if (user.getGoal().getActivityLevelTarget() == 5) {
            ui.goalNotFound();
            isDone = true;
        } else {
            int calorieToElevateActivityLevel = (int) (user.getDailyCalorie() * user.getActivityLevelDifference());
            int excessCalorie = user.getCalorieBalance() - meals.getCalorieBalanceOnDate(date);
            if (excessCalorie < 0) {
                //subtract current day's deficit of calories to the amount required for exercise
                calorieToElevateActivityLevel -= excessCalorie;
            }
            ui.showExerciseRequired(calorieToElevateActivityLevel, date);
            ArrayList<Pair> exerciseArrayList = this.exerciseSuggestionHandler.compute(meals,
                    calorieToElevateActivityLevel, keyword);

            ui.showExerciseOptions(exerciseArrayList);
            ui.showMessage("Input 0 to cancel selection");
        }
    }

    private void execute_stage_1(MealList meals, Storage storage) {
        int exerciseIdx;
        try {
            exerciseIdx = Integer.parseInt(this.responseStr);

        } catch (NumberFormatException e) {
            ui.showMessage("Could not parse " + responseStr + " as a number. Please input an integer.");
            return;
        }

        if (exerciseIdx == 0) {
            ui.showMessage("The suggestexercise command has been canceled");
            isDone = true;
            return;
        }

        if (exerciseIdx < 0 || exerciseIdx > exerciseSuggestionHandler.getSize()) {

            ui.showMessage(responseStr + " is out of bounds. Please input a valid index.");
            return;
        }
        exerciseSuggestionHandler.addChosenExercise(exerciseIdx - 1, meals, date);
        ui.showMessage("Got it!, I have set the chosen exercise for the date "
                + date.format(LOCAL_DATE_FORMATTER) + ".");

        try {
            storage.updateExercises(meals);
        } catch (ProgramException e) {
            ui.showMessage(e.getMessage());
        }
        isDone = true;
    }
}
