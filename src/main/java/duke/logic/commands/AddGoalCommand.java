package duke.logic.commands;

import duke.ui.InputHandler;
import duke.commons.exceptions.DukeException;
import duke.model.wallet.TransactionList;
import duke.model.wallet.Wallet;
import duke.storage.Storage;
import duke.model.Goal;
import duke.model.meal.MealList;
import duke.ui.Ui;
import duke.model.user.User;

/**
 * AddGoalCommand is a public class that inherits from abstract class Command.
 * An AddGoalCommand object encapsulates the goal object that is to be added.
 * average kg loss per day should NOT be more than 0.13607787283kg (>0.3 pounds)
 * average calorie loss per day should NOT exceed 40% base calorie intake,
 * balanced at around 20% (1kg = 7700cal = 7.7kcal)
 */
public class AddGoalCommand extends Command {
    private Goal goal = new Goal();
    private User user;
    private Ui ui;
    private InputHandler in;

    /**
     * Constructor for AddGoalCommand.
     */
    public AddGoalCommand() {
    }

    public AddGoalCommand(boolean flag, String message) {
        this.isFail = true;
        this.error = message;
    }

    /**
     * Executes AddGoalCommand.
     * @param meals the MealList object in which the meals are supposed to be added
     * @param storage the storage object that handles all reading and writing to files
     * @param user the object that handles all user data
     * @param wallet the wallet object that stores transaction information
     */
    @Override
    public void execute(MealList meals, Storage storage, User user, Wallet wallet) {
        isDone = false;
        ui.showLine();
        InputHandler in = new InputHandler(response);
        try {
            switch(stage) {
                case 0:
                    ui.showQueryStartDate();
                    break;
                case 1:
                    String startDate = in.getDate();
                    goal.setStartDate(startDate);
                    ui.showQueryEndDate();
                    break;
                case 2:
                    String endDate = in.getDate();
                    goal.setEndDate(endDate);
                    ui.showQueryTargetWeight();
                    break;
                case 3:
                    double weight = in.getDouble();
                    goal.setWeightTarget(weight);
                    ui.showQueryTargetLifestyle();
                    break;
                case 4:
                    if (in.getApproval()) {
                        ui.showActivityLevel();
                    } else {
                        goal.setLifestyleTarget(5);
                        if (user.setGoal(goal, false)) {
                            ui.succeedSetGoal();
                            isDone = true;
                        } else {
                            ui.queryOverrideExistingGoal();
                            stage++; //skip the next stage
                        }
                    }
                    break;
                case 5:
                    int activityLevel = in.getInt() - 1;
                    if (activityLevel >= 5 || activityLevel < 0) {
                        throw new DukeException("Please enter a valid activity level.");
                    } else {
                        goal.setLifestyleTarget(activityLevel);
                    }
                    if (user.setGoal(goal, false)) {
                        ui.succeedSetGoal();
                        isDone = true;
                    } else {
                        ui.queryOverrideExistingGoal();
                    }
                    break;
                case 6:
                    if (in.getApproval()) {
                        user.setGoal(goal, true);
                        ui.succeedSetGoal();
                        isDone = true;
                    } else {
                        ui.failSetGoal();
                        isDone = true;
                    }
                    break;
                default:
                    isDone = true;
                    throw new DukeException("There is a problem with the setgoal command.");
            }
            ui.showLine();
            stage++;
        } catch (DukeException e) {
            ui.showMessage(e.getMessage());
        }
    }
}
