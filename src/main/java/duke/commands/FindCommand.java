package duke.commands;

import duke.tasks.Meal;
import duke.tasks.MealList;
import duke.ui.Ui;
import duke.storage.Storage;

import java.util.ArrayList;
import duke.user.User;

/**
 * The FindCommand is a public class that extends from the abstract class Command.
 * It encapsulates the String to find in the current TaskList
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

    /**
     * This function will execute the "find" command.
     * @param tasks the TaskList object in which the task is supposed to be found
     * @param ui the ui object to display the user interface of an "find" command
     * @param storage the storage object that stores the list of tasks
     */
    @Override
    public void execute(MealList tasks, Ui ui, Storage storage, User user) {
        ArrayList<Meal> matchingMeals = new ArrayList<>();
        ArrayList<Meal> currentMeals = tasks.getMeals(currentDate);
        for (Meal element: currentMeals) {
            String currentTaskString = element.toString();
            if (currentTaskString.contains(description)) {
                matchingMeals.add(element);
            }
        }
        ui.showList(matchingMeals);
    }
}
