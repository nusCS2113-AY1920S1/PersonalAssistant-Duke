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

    /**
     * Executes AddGoalCommand.
     * @param meals the MealList object in which the meal is supposed to be added
     * @param ui the ui object to display the user interface of an "add" command
     * @param storage the storage object that stores the list of meals
     * @param in the scanner object to handle secondary command IO
     */
    @Override
    public void execute(MealList meals, Storage storage, User user, Wallet wallet) {
        try {
            meals.addGoal(this.goal);
            ui.showAddedGoal(goal);
            storage.updateGoal(meals);
        } catch (DukeException e) {
            ui.showMessage(e.getMessage());
            ui.showLine();
            isDone = false;
        }
    }

    public void execute2(MealList meals, Storage storage, User user, Wallet wallet) {
        if (response.trim().equals("y")  || response.trim().equals("Y")) {
            meals.addGoal(this.goal, true);
            ui.showLine();
            ui.showAddedGoal(goal);
            storage.updateGoal(meals);
        } else if (response.trim().equals("n")  || response.trim().equals("N")) {
            ui.showLine();
            ui.showMessage("The set goal command has been canceled");
        } else {
            ui.showLine();
            ui.showMessage("An unknown response has been recorded \n"
                    + "     The set goal command has been canceled");
        }
    }
}
