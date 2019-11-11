package diyeats.logic.commands;

import diyeats.commons.exceptions.ProgramException;
import diyeats.model.meal.MealList;
import diyeats.model.undo.Undo;
import diyeats.model.user.Goal;
import diyeats.model.user.User;
import diyeats.model.wallet.Wallet;
import diyeats.storage.Storage;

import java.util.HashMap;

//@@author Fractalisk

/**
 * AddGoalCommand is a public class that inherits from abstract class Command.
 * An AddGoalCommand object encapsulates the goal object that is to be added to user.
 * average kg loss per day should NOT be more than 0.13607787283kg (>0.3 pounds)
 * average calorie loss per day should NOT exceed 40% base calorie intake,
 * balanced at around 20% (1kg = 7700cal = 7.7kcal)
 */
public class AddGoalCommand extends Command {
    private Goal goal;

    /**
     * Constructor for addGoalCommand.
     * @param argumentsMap argumentsMap for instantiating a goal object
     */
    public AddGoalCommand(HashMap<String, String> argumentsMap) {
        goal = new Goal(argumentsMap);
    }

    // This constructor is called if there are issues parsing user input.
    public AddGoalCommand(boolean isFail, String messageStr) {
        this.isFail = true;
        this.errorStr = messageStr;
    }

    /**
     * Executes AddGoalCommand.
     * @param meals the MealList object in which the meals are supposed to be added
     * @param storage the storage object that handles all reading and writing to files
     * @param user the object that handles all user data
     * @param wallet the wallet object that stores transaction information
     */
    @Override
    public void execute(MealList meals, Storage storage, User user, Wallet wallet, Undo undo) {
        try {
            user.setGoal(goal);
            ui.showMessage("The set goal Command is successful!");
            storage.writeGoal(user);
            stage++;
        } catch (ProgramException e) {
            ui.showMessage(e.getMessage());
        }
    }
}
