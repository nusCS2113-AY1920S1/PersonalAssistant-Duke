package duke.logic.commands;

import duke.ui.InputHandler;
import duke.commons.exceptions.DukeException;
import duke.model.Meal;
import duke.model.MealList;
import duke.model.TransactionList;
import duke.ui.Ui;
import duke.storage.Storage;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import duke.model.user.User;

/**
 * MarkDoneCommand is a public class that inherits form abstract class Command.
 * A MarkDoneCommand object encapsulates the index of meal to be marked as done.
 */
public class MarkDoneCommand extends Command {
    private int index;

    /**
     * Constructor for MarkDoneCommand.
     * @param indexStr the index of meal on the date to be marked as done.
     * @param date the date which meals are to be marked as done.
     */
    public MarkDoneCommand(String indexStr, String date) throws DukeException {
        this(indexStr);
        if (!date.isBlank()) {
            Date parsedDate;
            try {
                parsedDate = dateFormat.parse(date);
            } catch (ParseException e) {
                throw new DukeException("Unable to parse input" + date + " as a date.");
            }
            this.currentDate = dateFormat.format(parsedDate);
        }
    }

    /**
     * Constructor for MarkDoneCommand.
     * @param indexStr the index of meal on the today to be marked as done.
     * @throws DukeException when parseInt is unable to parse the index.
     */
    public MarkDoneCommand(String indexStr) throws DukeException {
        try {
            this.index = Integer.parseInt(indexStr.trim());
        } catch (NumberFormatException e) {
            throw new DukeException("Unable to parse input " + indexStr + " as integer index.");
        }
    }

    /**
     * Executes the MarkDoneCommand.
     * @param meals the MealList object in which the meals are supposed to be added
     * @param ui the ui object to display the results of the command to the user
     * @param storage the storage object that handles all reading and writing to files
     * @param user the object that handles all user data
     * @param in the scanner object to handle secondary command IO
     * @throws DukeException when the index of the meal to be marked done is invalid
     */
    @Override
    public void execute(MealList meals, Ui ui, Storage storage, User user,
                        InputHandler in, TransactionList transactions) throws DukeException {
        if (index <= 0 || index > meals.getMealsList(currentDate).size()) {
            throw new DukeException("Index provided out of bounds for list of meals on " + currentDate);
        }
        Meal currentMeal = meals.markDone(currentDate, index);
        storage.updateFile(meals);
        ui.showDone(currentMeal, meals.getMealsList(currentDate));
        ArrayList<Meal> currentMeals = meals.getMealsList(currentDate);
        ui.showCaloriesLeft(currentMeals, user, currentDate);
    }
}
