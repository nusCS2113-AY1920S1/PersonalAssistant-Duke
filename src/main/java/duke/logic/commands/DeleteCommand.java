package duke.logic.commands;

import duke.commons.exceptions.DukeException;
import duke.model.meal.Meal;
import duke.model.meal.MealList;
import duke.model.wallet.TransactionList;
import duke.model.wallet.Wallet;
import duke.ui.Ui;
import duke.storage.Storage;
import duke.model.user.User;

import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;

/**
 * DeleteCommand is a public class that inherits from abstract class Command.
 * A DeleteCommand object encapsulates the index of meal and date of the meal that is to be deleted.
 */
public class DeleteCommand extends Command {
    private int index;
    private final String helpText = "Please follow: delete <index> /date <date> or "
            + "delete <index> to delete for current day.";

    /**
     * Constructor for DeleteCommand.
     * @param indexStr the index of meal on the date to be deleted.
     * @param date Date of meal to be deleted.
     */
    public DeleteCommand(String indexStr, String date) throws DukeException {
        this(indexStr);
        Date parsedDate;
        try {
            parsedDate = dateFormat.parse(date);
        } catch (ParseException e) {
            throw new DukeException("Unable to parse input " + date + " as a date. " + helpText);
        }
        currentDate = dateFormat.format(parsedDate);
    }

    /**
     * Constructor for DeleteCommand.
     * @param indexStr the index of meal to be deleted.
     */
    public DeleteCommand(String indexStr) throws DukeException {
        try {
            this.index = Integer.parseInt(indexStr.trim());
        } catch (NumberFormatException nfe) {
            throw new DukeException("Unable to parse input " + indexStr + " as integer index. " + helpText);
        }
    }

    /**
     * Executes the DeleteCommand.
     * @param meals the MealList object in which the meals are supposed to be added
     * @param storage the storage object that handles all reading and writing to files
     * @param user the object that handles all user data
     * @throws DukeException when the index of the object to be deleted is out of bounds
     */
    @Override
    public void execute(MealList meals, Storage storage, User user, Wallet wallet) {
        ui.showLine();
        if (index <= 0 || index > meals.getMealsList(currentDate).size()) {
            ui.showMessage("Index provided out of bounds for list of meals on " + currentDate);
        } else {
            Meal currentMeal = meals.delete(currentDate, index);
            ui.showDeleted(currentMeal, meals.getMealsList(currentDate));
            storage.updateFile(meals);
        }
        ui.showLine();
    }

    public void execute2(MealList meals, Storage storage, User user, Wallet wallet) {
    }
}
