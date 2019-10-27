package duke.logic.commands;

import duke.model.meal.Meal;
import duke.model.meal.MealList;
import duke.model.wallet.TransactionList;
import duke.model.wallet.Wallet;
import duke.ui.Ui;
import duke.storage.Storage;
import duke.commons.exceptions.DukeException;
import duke.model.user.User;

import java.text.ParseException;
import java.util.Date;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * ListCommand is a public class that inherits from abstract class Command.
 * It displays all the meals in a relevant day in a list to the user
 */
public class ListCommand extends Command {
    private String date;

    /**
     * Constructor for ListCommand.
     */
    public ListCommand() {
    }

    /**
     * Constructor for ListCommand.
     * @param date The date of the data to List
     */

    public ListCommand(String date) {
        Date temp;
        try {
            temp = dateFormat.parse(date);
            currentDate = dateFormat.format(temp);
        } catch (ParseException e) {
            ui.showMessage(e.getMessage());
        }
    }

    public ListCommand(boolean flag, String message) {
        this.isFail = true;
        this.error = message;
    }

    /**
     * Executes the ListCommand.
     * @param meals the MealList object in which the meal(s) is supposed to be listed
     * @param storage the storage object that stores the list of meals
     */
    @Override
    public void execute(MealList meals,  Storage storage, User user, Wallet wallet) {
        ui.showLine();
        ui.showCalorie(user);
        ArrayList<Meal> currentMeals = meals.getMealsList(currentDate);
        if (!meals.checkDate(currentDate)) {
            ui.showMessage("There isn't any food on " + currentDate);
        }
        ui.showList(currentMeals);
        ui.showCaloriesLeft(currentMeals, user, currentDate);
        //ui.showRemainingCalorie(currentMeals, user, tasks.caloriesAvgToGoal());
        ui.showLine();
    }

    public void execute2(MealList meals, Storage storage, User user, Wallet wallet) {
    }
}
