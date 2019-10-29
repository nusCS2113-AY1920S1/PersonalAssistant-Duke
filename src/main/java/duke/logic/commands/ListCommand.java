package duke.logic.commands;

import duke.model.meal.Meal;
import duke.model.meal.MealList;
import duke.model.user.User;
import duke.model.wallet.Wallet;
import duke.storage.Storage;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

/**
 * ListCommand is a public class that inherits from abstract class Command.
 * It displays all the meals in a relevant day in a list to the user
 */
public class ListCommand extends Command {

    /**
     * Constructor for ListCommand.
     */
    public ListCommand() {
    }

    /**
     * Constructor for ListCommand.
     * @param dateStr The date of the data to List
     */

    public ListCommand(String dateStr) {
        Date temp;
        try {
            temp = dateFormat.parse(dateStr);
            currentDateStr = dateFormat.format(temp);
        } catch (ParseException e) {
            ui.showMessage(e.getMessage());
        }
    }

    public ListCommand(boolean flag, String messageStr) {
        this.isFail = true;
        this.errorStr = messageStr;
    }

    /**
     * Executes the ListCommand.
     * @param meals the MealList object in which the meals are supposed to be added
     * @param storage the storage object that handles all reading and writing to files
     * @param user the object that handles all user data
     * @param wallet the wallet object that stores transaction information
     */
    @Override
    public void execute(MealList meals,  Storage storage, User user, Wallet wallet) {
        ui.showLine();
        ui.showCalorie(user);
        ArrayList<Meal> currentMeals = meals.getMealsList(currentDateStr);
        if (!meals.checkDate(currentDateStr)) {
            ui.showMessage("There isn't any food on " + currentDateStr);
        }

        ui.showMealList(currentMeals);
        ui.showCaloriesLeft(currentMeals, user, currentDateStr);
        ui.showLine();
    }
}
