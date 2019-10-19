package duke.logic.commands;

import duke.model.Meal;
import duke.model.MealList;
import duke.ui.Ui;
import duke.storage.Storage;

import java.util.ArrayList;
import java.util.Scanner;

import duke.model.user.User;

/**
 * MarkDoneCommand is a public class that inherits form abstract class Command.
 * A MarkDoneCommand object encapsulates the index of meal to be marked as done.
 * @author Ivan Andika Lie
 */
public class MarkDoneCommand extends Command {
    private String type;

    /**
     * This is a constructor for MarkDoneCommand.
     * @param type the type of meal
     * @param date the date which meals are to be marked as done
     */
    public MarkDoneCommand(String type, String date) {
        this.type = type.substring(0, 1).toUpperCase();
        this.currentDate = date;
    }

    /**
     * The object will execute the "mark done" command, updating the current meals, ui, and storage in the process.
     * @param tasks the MealList object to be marked done
     * @param ui the ui object to display the user interface of an "mark done" command
     * @param storage the storage object that stores the list of meals
     * @param in the scanner object to handle secondary command IO
     */
    @Override
    public void execute(MealList tasks, Ui ui, Storage storage, User user, Scanner in) {
        ArrayList<Meal> matchingMeals = new ArrayList<>();
        ArrayList<Meal> currentMeals = tasks.getMealsList(currentDate);
        for (Meal element: currentMeals) {
            if (element.getType().equals(type)) {
                element.markAsDone();
                matchingMeals.add(element);
            }
        }
        storage.updateFile(tasks);
        ui.showDone(matchingMeals);
        ui.showRemainingCalorie(currentMeals, user, tasks.caloriesAvgToGoal());
    }
}
