package duke.logic.commands;

import duke.logic.suggestion.ExerciseSuggestionHandler;
import duke.model.meal.MealList;
import duke.model.user.User;
import duke.model.wallet.Wallet;
import duke.storage.Storage;

import java.time.LocalDate;
import java.util.HashMap;


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
        exerciseSuggestionHandler = new ExerciseSuggestionHandler(meals.getExerciseList(), user);
        isDone = false;
        switch (stage) {
            case 0:
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

    public void execute_stage_0(MealList meals, User user) {
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
            ui.showExerciseRequired(calorieToElevateActivityLevel);
            HashMap<String, Integer> exerciseHashMap = exerciseSuggestionHandler.compute(calorieToElevateActivityLevel);
            ui.showExerciseOptions(exerciseHashMap);
        }
    }

    public void execute_stage_1(MealList meals, Storage storage, User user, Wallet wallet) {
        if (user.getGoal() == null) {
            ui.goalNotFound();
        } else if (user.getGoal().getActivityLevelTarget() == 5) {
            ui.goalNotFound();
        } else {
            int calorieToElevateActivityLevel = (int) (user.getDailyCalorie() * user.getActivityLevelDifference());
            //int calorieToLoseWeight = (int) ((0.5) * user.getAvgCalorieChangeToReachTarget());
            ui.showExerciseRequired(calorieToElevateActivityLevel);
        }
    }
}
