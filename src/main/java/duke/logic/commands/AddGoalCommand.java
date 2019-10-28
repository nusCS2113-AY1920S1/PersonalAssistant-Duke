package duke.logic.commands;

import duke.commons.exceptions.DukeException;
import duke.model.wallet.TransactionList;
import duke.model.wallet.Wallet;
import duke.storage.Storage;
import duke.model.Goal;
import duke.model.meal.MealList;
import duke.ui.Ui;
import duke.model.user.User;

import java.util.Scanner;

/**
 * AddGoalCommand is a public class that inherits from abstract class Command.
 * An AddGoalCommand object encapsulates the goal object that is to be added.
 * average kg loss per day should NOT be more than 0.13607787283kg (>0.3 pounds)
 * average calorie loss per day should NOT exceed 40% base calorie intake,
 * balanced at around 20% (1kg = 7700cal = 7.7kcal)
 */
public class AddGoalCommand extends Command {
    private Goal goal;

    /**
     * Constructor for AddGoalCommand.
     * @param goal The goal to be added.
     */
    public AddGoalCommand(Goal goal) {
        this.goal = goal;
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
        ui.showLine();
        try {
            meals.addGoal(this.goal);
            ui.showAddedGoal(goal);
            storage.updateGoal(meals);
        } catch (DukeException e) {
            ui.showMessage(e.getMessage());
            ui.showLine();
            isDone = false;
        }
        ui.showLine();
    }

    public void execute2(MealList meals, Storage storage, User user, Wallet wallet) {
        ui.showLine();
        if (response.trim().equals("y")  || response.trim().equals("Y")) {
            meals.addGoal(this.goal, true);
            ui.showLine();
            ui.showAddedGoal(goal);
            try {
                storage.updateGoal(meals);
            } catch (DukeException e) {
                ui.showMessage(e.getMessage());
            }
        } else if (response.trim().equals("n")  || response.trim().equals("N")) {
            ui.showLine();
            ui.showMessage("The set goal command has been canceled");
        } else {
            ui.showLine();
            ui.showMessage("An unknown response has been recorded \n"
                    + "     The set goal command has been canceled");
        }
        ui.showLine();
    }
}
