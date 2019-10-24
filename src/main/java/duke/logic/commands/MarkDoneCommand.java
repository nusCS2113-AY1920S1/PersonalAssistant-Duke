package duke.logic.commands;

import duke.commons.exceptions.DukeException;
import duke.model.Meal;
import duke.model.MealList;
import duke.model.TransactionList;
import duke.ui.Ui;
import duke.storage.Storage;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import duke.model.user.User;

/**
 * MarkDoneCommand is a public class that inherits form abstract class Command.
 * A MarkDoneCommand object encapsulates the index of meal to be marked as done.
 */
public class MarkDoneCommand extends Command {
    private int index;
    private final String helpText = "Please follow: done <index> /date <date> or "
            + "done <index> to mark done the indexed meal for current day.";

    /**
     * This is a constructor for MarkDoneCommand with the date specified.
     * @param indexStr the index of meal on the date to be marked as done.
     * @param date the date which meals are to be marked as done.
     */
    public MarkDoneCommand(String indexStr, String date) throws DukeException {
        this(indexStr);
        Date parsedDate;
        try {
            parsedDate = dateFormat.parse(date);
        } catch (ParseException e) {
            throw new DukeException("Unable to parse input" + date + " as a date. " + helpText);
        }
        this.currentDate = dateFormat.format(parsedDate);
    }

    /**
     * This is a constructor for MarkDoneCommand with the date unspecified.
     * @param indexStr the index of meal on the today to be marked as done.
     * @throws DukeException when parseInt is unable to parse the index.
     */
    public MarkDoneCommand(String indexStr) throws DukeException {
        try {
            this.index = Integer.parseInt(indexStr.trim());
        } catch (NumberFormatException e) {
            throw new DukeException("Unable to parse input " + indexStr + " as integer index. " + helpText);
        }
    }

    /**
     * The object will execute the "mark done" command, updating the current meals, ui, and storage in the process.
     * @param mealList the MealList object to be marked done
     * @param ui the ui object to display the user interface of an "mark done" command
     * @param storage the storage object that stores the list of meals
     * @param in the scanner object to handle secondary command IO
     */
    @Override
    public void execute(MealList mealList, Ui ui, Storage storage, User user,
                        Scanner in, TransactionList transactions) throws DukeException {
        if (index <= 0 || index > mealList.getMealsList(currentDate).size()) {
            throw new DukeException("Index provided out of bounds for list of meals on " + currentDate);
        }
        Meal currentMeal = mealList.markDone(currentDate, index);
        storage.updateFile(mealList);
        ui.showDone(currentMeal, mealList.getMealsList(currentDate));
        ArrayList<Meal> currentMeals = mealList.getMealsList(currentDate);
        ui.showCaloriesLeft(currentMeals, user, currentDate);
    }
}
