package duke.logic.commands;

import duke.model.meal.MealList;
import duke.model.user.User;
import duke.model.wallet.Wallet;
import duke.storage.Storage;


/**
 * TODO: analyze the list of SuggestExercise objects as well as the current calorie goal of the
 * user, the date provided and the user meal parameters provided to get the best exercise suggestion.
 * TONOTE: calories burned from exercise is directly related to BMI, eg, a 200kg man running for 10 mins will burn
 * as many calories as a 100kg man running for 20 mins
 * Exercise needed to elevate activity level = user.getDailyCalorie * user.gerActivityLevelDifference();
 * Additional exercise needed to burn calories = user.checkCalorieLossRatio() * user.getAvgCalorieChangeToReachTarget();
 * Exercise is defined in metabolic unit at rest(MET), eg, 1 hr of running which is 14.5 MET
 * is equal to 14.5 hours of being alive
 */
public class SuggestExerciseCommand extends Command {

    public SuggestExerciseCommand() {
    }

    public SuggestExerciseCommand(boolean flag, String message) {
        this.isFail = true;
        this.errorStr = message;
    }

    @Override
    public void execute(MealList meals, Storage storage, User user, Wallet wallet) {
        if (user.getGoal() == null) {
            ui.goalNotFound();
        } else if (user.getGoal().getActivityLevelTarget() == 5) {
            ui.goalNotFound();
        } else {
            int a = (int) (user.getDailyCalorie() * user.getActivityLevelDifference());
            int b = (int) ((0.5) * user.getAvgCalorieChangeToReachTarget()); //0.5 is temporary
            ui.showExerciseRequired(a + b);
        }
    }
}
