package duke.logic.commands;

import duke.commons.exceptions.DukeException;
import duke.storage.Storage;
import duke.model.Meal;
import duke.model.MealList;
import duke.ui.Ui;
import duke.model.user.User;

import java.util.Scanner;

public class EditCommand extends Command {
    private Meal updatedMeal;

    /**
     * Constructor for EditCommand.
     * @param meal The meal object to be edited to
     */
    public EditCommand(Meal meal) {
        this.updatedMeal = meal;
    }

    /**
     * Executes the EditCommand.
     * @param meals the MealList object in which the meals are supposed to be added
     * @param ui the ui object to display the results of the command to the user
     * @param storage the storage object that handles all reading and writing to files
     * @param user the object that handles all user data
     * @param in the scanner object to handle secondary command IO
     * @throws DukeException if there is a parsing error for the date
     */
    @Override
    public void execute(MealList meals, Ui ui, Storage storage, User user, Scanner in) throws DukeException {
        updatedMeal = meals.updateMeal(updatedMeal);
        String dateStr = updatedMeal.getDate();
        ui.showUpdated(this.updatedMeal, meals.getMealsList(this.updatedMeal.getDate()), user, dateStr);
        storage.updateFile(meals);
    }
}
