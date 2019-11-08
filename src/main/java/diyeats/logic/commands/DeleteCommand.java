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
    private final String helpText = "Please follow: delete <index> /date <date> or "
            + "delete <index> to delete for current day.";
    private LocalDate parsedDate;

    /**
     * Constructor for DeleteCommand.
     * @param indexStr the index of meal on the date to be deleted.
     * @param dateStrArg Date of meal to be deleted.
     */
    public DeleteCommand(String indexStr, String dateStrArg) {
        this(indexStr);
        try {
            parsedDate = LocalDate.parse(dateStrArg, dateFormat);
        } catch (DateTimeParseException e) {
            ui.showMessage("Unable to parse input " + dateStrArg + " as a date. " + helpText);
        }
    }

    /**
     * Constructor for DeleteCommand.
     * @param indexStr the index of meal to be deleted.
     */
    public DeleteCommand(String indexStr) {
        try {
            this.index = Integer.parseInt(indexStr.trim());
            parsedDate = currentDate;
        } catch (NumberFormatException nfe) {
            ui.showMessage("Unable to parse input " + indexStr + " as integer index. " + helpText);
        }
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
        if (index <= 0 || index > meals.getMealsList(parsedDate).size()) {
            ui.showMessage("Index provided out of bounds for list of meals on the indicated date");
        } else {
            Meal currentMeal = meals.delete(parsedDate, index);
            ui.showDeleted(currentMeal, meals.getMealsList(parsedDate));
            try {
                storage.updateFile(meals);
            } catch (ProgramException e) {
                ui.showMessage(e.getMessage());
            }

        }
        ui.showLine();
    }

}
