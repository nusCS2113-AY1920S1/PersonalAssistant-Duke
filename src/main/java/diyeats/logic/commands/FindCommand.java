package diyeats.logic.commands;

import diyeats.model.meal.Meal;
import diyeats.model.meal.MealList;
import diyeats.model.user.User;
import diyeats.model.wallet.Wallet;
import diyeats.storage.Storage;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * The FindCommand is a public class that extends from the abstract class Command.
 * It encapsulates the String to find in the current MealList
 */
public class FindCommand extends Command {
    private String description;

    /**
     * FindCommand is a constructor that store the string to find.
     * @param description the description of the string to find
     */
    public FindCommand(String description) {
        this.description = description;
    }

    public FindCommand(String descriptionStr, String dateStr) {
        this(descriptionStr);
        if (!dateStr.isBlank()) {
            this.currentDate = LocalDate.parse(dateStr, dateFormat);
        }
    }

    public FindCommand(boolean flag, String messageStr) {
        this.isFail = true;
        this.errorStr = messageStr;
    }

    /**
     * Executes the FindCommand.
     * @param meals the MealList object in which the meals are supposed to be added
     * @param storage the storage object that handles all reading and writing to files
     * @param user the object that handles all user data
     * @param wallet the wallet object that stores transaction information
     */
    @Override
    public void execute(MealList meals, Storage storage, User user, Wallet wallet) {
        ui.showLine();
        ArrayList<Meal> matchingMeals = new ArrayList<>();
        ArrayList<Meal> currentMeals = meals.getMealsList(currentDate);
        for (Meal element: currentMeals) {
            String currentTaskString = element.toString();
            if (currentTaskString.contains(description)) {
                matchingMeals.add(element);
            }
        }
        ui.showMealList(matchingMeals);
        ui.showLine();
    }
}
