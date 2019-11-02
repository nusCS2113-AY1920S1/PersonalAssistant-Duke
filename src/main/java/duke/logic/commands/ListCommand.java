package duke.logic.commands;

import duke.model.meal.Meal;
import duke.model.meal.MealList;
import duke.model.user.User;
import duke.model.wallet.Wallet;
import duke.storage.Storage;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

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
     * @param date The date of the data to List
     */

    public ListCommand(LocalDate date) {
        if (date != null) {
            try {
                currentDate = date;
            } catch (DateTimeParseException e) {
                ui.showMessage("Unable to parse \"" + date.toString() + "\" as a date. Showing list of "
                        + currentDate.format(dateFormat) + " instead.");
            }
        }
    }

    public ListCommand(boolean flag, String messageStr) {
        this.isFail = flag;
        this.errorStr = messageStr;

        // If un-parseble user input date, default to current date.
        if (!this.isFail) {
            ui.showMessage(messageStr);
            ui.showMessage("Showing list of meals from " + currentDate.format(dateFormat) + " instead.");
        }
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
        ArrayList<Meal> currentMeals = meals.getMealsList(currentDate);
        if (!meals.checkDate(currentDate)) {
            ui.showMessage("There isn't any food on " + currentDate.format(dateFormat));
        }

        ui.showMealList(currentMeals);
        ui.showCaloriesLeft(currentMeals, user, currentDate);
        ui.showLine();
    }
}
