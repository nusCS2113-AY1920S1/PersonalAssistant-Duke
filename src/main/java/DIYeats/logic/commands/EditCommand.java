package DIYeats.logic.commands;

import DIYeats.commons.exceptions.DukeException;
import DIYeats.model.meal.Meal;
import DIYeats.model.meal.MealList;
import DIYeats.model.user.User;
import DIYeats.model.wallet.Wallet;
import DIYeats.storage.Storage;

import java.time.LocalDate;

//@@author HashirZahir
public class EditCommand extends Command {
    private Meal updatedMeal;

    /**
     * Constructor for EditCommand.
     * @param meal The meal object to be edited to
     */
    public EditCommand(Meal meal) {
        this.updatedMeal = meal;
    }

    public EditCommand(boolean flag, String messageStr) {
        this.isFail = true;
        this.errorStr = messageStr;
    }

    /**
     * Executes the EditCommand.
     * @param meals the MealList object in which the meals are supposed to be added
     * @param storage the storage object that handles all reading and writing to files
     * @param user the object that handles all user data
     * @param wallet the wallet object that stores transaction information
     */
    @Override
    public void execute(MealList meals, Storage storage, User user, Wallet wallet) {
        ui.showLine();
        try {
            updatedMeal = meals.updateMeal(updatedMeal);
            LocalDate date = updatedMeal.getDate();
            ui.showUpdated(this.updatedMeal, meals.getMealsList(this.updatedMeal.getDate()), user, date);
            storage.updateFile(meals);
        } catch (DukeException e) {
            ui.showMessage(e.getMessage());
        }
        ui.showLine();
    }
}
