package diyeats.logic.commands;

import diyeats.commons.exceptions.ProgramException;
import diyeats.model.meal.Meal;
import diyeats.model.meal.MealList;
import diyeats.model.user.User;
import diyeats.model.wallet.Wallet;
import diyeats.storage.Storage;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

//@@author HashirZahir
/**
 * DeleteCommand is a public class that inherits from abstract class Command.
 * A DeleteCommand object encapsulates the index of meal and date of the meal that is to be deleted.
 */
public class DeleteCommand extends Command {
    private int index;
    private LocalDate deleteDate;

    /**
     * Constructor for DeleteCommand.
     * @param index the index of meal on the date to be deleted.
     * @param deleteDate Date of meal to be deleted.
     */
    public DeleteCommand(int index, LocalDate deleteDate) {
        this.index = index;
        this.deleteDate = deleteDate;
    }

    /**
     * Constructor for DeleteCommand.
     * @param index the index of meal to be deleted.
     */
    public DeleteCommand(int index) {
        this.index = index;
    }

    public DeleteCommand(boolean flag, String messageStr) {
        this.isFail = true;
        this.errorStr = messageStr;
    }

    /**
     * Executes the DeleteCommand.
     * @param meals the MealList object in which the meals are supposed to be added
     * @param storage the storage object that handles all reading and writing to files
     * @param user the object that handles all user data
     * @param wallet the wallet object that stores transaction information
     */
    @Override
    public void execute(MealList meals, Storage storage, User user, Wallet wallet) {
        ui.showLine();
        if (index < 0 || index >= meals.getMealsList(deleteDate).size()) {
            String errorMsg = "Index provided out of bounds for list of meals on the indicated date. ";
            if (meals.getMealsList(deleteDate).size() == 0) {
                errorMsg += "No meals on " + deleteDate + " to delete";
            } else {
                errorMsg += "Valid index is from 1 to " + meals.getMealsList(deleteDate).size();
            }
            ui.showMessage(errorMsg);
        } else {
            Meal currentMeal = meals.delete(deleteDate, index);
            ui.showDeleted(currentMeal, meals.getMealsList(deleteDate));
            try {
                storage.writeFile(meals);
            } catch (ProgramException e) {
                ui.showMessage(e.getMessage());
            }
        }
        ui.showLine();
    }

}
