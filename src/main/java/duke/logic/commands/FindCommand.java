package duke.logic.commands;

import duke.model.Meal;
import duke.model.MealList;
import duke.ui.Ui;
import duke.storage.Storage;

import java.util.ArrayList;
import java.util.Scanner;

import duke.model.user.User;

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

    public FindCommand(String description, String date) {
        this.description = description;
        this.currentDate = date;
    }

    /**
     * Executes the FindCommand.
     * @param meals the MealList object in which the meals are supposed to be added
     * @param ui the ui object to display the results of the command to the user
     * @param storage the storage object that handles all reading and writing to files
     * @param user the object that handles all user data
     * @param in the scanner object to handle secondary command IO
     */
    @Override
    public void execute(MealList meals, Ui ui, Storage storage, User user, Scanner in) {
        ArrayList<Meal> matchingMeals = new ArrayList<>();
        ArrayList<Meal> currentMeals = meals.getMealsList(currentDate);
        for (Meal element: currentMeals) {
            String currentTaskString = element.toString();
            if (currentTaskString.contains(description)) {
                matchingMeals.add(element);
            }
        }
        ui.showList(matchingMeals);
    }
}
