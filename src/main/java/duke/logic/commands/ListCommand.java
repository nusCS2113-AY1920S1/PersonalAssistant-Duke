package duke.logic.commands;

import duke.model.Meal;
import duke.model.MealList;
import duke.model.TransactionList;
import duke.ui.Ui;
import duke.storage.Storage;
import duke.commons.exceptions.DukeException;
import duke.model.user.User;

import java.text.ParseException;
import java.util.Date;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * ListCommand is a public class that inherits from abstract class Command.
 */
public class ListCommand extends Command {

    private String date;

    public ListCommand() {
    }

    public ListCommand(String date) throws DukeException {
        Date temp;
        try {
            temp = dateFormat.parse(date);
        } catch (ParseException e) {
            throw new DukeException(e.getMessage());
        }
        currentDate = dateFormat.format(temp);
    }

    /**
     * The object will execute the "list" command.
     * @param tasks the MealList object in which the meal(s) is supposed to be listed
     * @param ui the ui object to display the user interface of an "list" command
     * @param storage the storage object that stores the list of meals
     * @param in the scanner object to handle secondary command IO
     */
    @Override
    public void execute(MealList tasks, Ui ui, Storage storage, User user,
                        Scanner in, TransactionList transactions) throws DukeException {
        ui.showCalorie(user);
        ArrayList<Meal> currentMeals = tasks.getMealsList(currentDate);
        if (!tasks.checkDate(currentDate)) {
            throw new DukeException("There isn't any food on " + currentDate);
        }
        ui.showList(currentMeals);
        ui.showCaloriesLeft(currentMeals, user, currentDate);
        //ui.showRemainingCalorie(currentMeals, user, tasks.caloriesAvgToGoal());
    }
}
