package DIYeats.logic.commands;

import DIYeats.logic.suggestion.ExerciseSuggestionHandler;
import DIYeats.model.meal.MealList;
import DIYeats.model.user.User;
import DIYeats.model.wallet.Wallet;
import DIYeats.storage.Storage;
import javafx.util.Pair;

import java.time.LocalDate;
import java.util.ArrayList;

import static DIYeats.commons.constants.DateConstants.LOCAL_DATE_FORMATTER;


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
    private ExerciseSuggestionHandler exerciseSuggestionHandler;

    public SuggestExerciseCommand(LocalDate date) {
        this.date = date;
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
                execute_stage_1(meals, storage, user, wallet);
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
            ArrayList<Pair<String, Integer>> exerciseArrayList =
                    this.exerciseSuggestionHandler.compute(meals, calorieToElevateActivityLevel);
            ui.showExerciseOptions(exerciseArrayList);
        }
    }

    private void execute_stage_1(MealList meals, Storage storage, User user, Wallet wallet) {
        int exerciseIdx;
        try {
            exerciseIdx = Integer.parseInt(this.responseStr);

        } catch (NumberFormatException e) {
            ui.showMessage("Could not parse " + responseStr + " as a number. Please input an integer.");
            return;
        }

        if (exerciseIdx <= 0 || exerciseIdx > exerciseSuggestionHandler.getSize()) {

            ui.showMessage(responseStr + " is out of bounds. Please input a valid index.");
            return;
        }
        exerciseSuggestionHandler.addChosenExercise(exerciseIdx - 1, meals, date);
        ui.showMessage("Got it!, I have set the exercise for the date " + date.format(LOCAL_DATE_FORMATTER) + ".");
        isDone = true;
    }
}
