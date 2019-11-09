package duke.commands;

import duke.exceptions.DukeException;
import duke.gui.GUI;
import duke.storage.Storage;
import duke.tasks.MealList;
import duke.ui.Ui;
import duke.user.User;

import java.util.Scanner;

//@@author koushireo-unused

/**
 * Display desired data based on dates
 */
public class GUICommand extends Command {
    private String startDate;
    private String endDate;
    private String type;

    /**
     * This is a constructor for AddCommand which create a new AddCommand object with
     * the meal specified as the instance field meal.
     * @param meal The meal to be added.
     */
    public GUICommand(String type, String startDate, String endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
    }

    /**
     * The object will execute the "add" command, updating the current tasks, ui, and storage in the process.
     * @param meals the MealList object in which the meal is supposed to be added
     * @param ui the ui object to display the user interface of an "add" command
     * @param storage the storage object that stores the list of meals
     * @param in the scanner object to handle secondary command IO
     */
    @Override
    public void execute(MealList meals, Ui ui, Storage storage, User user, Scanner in, GUI gui) throws DukeException {
        gui.setMealList(meals.getMealTracker());
        gui.setWeight(user.getAllWeight());
        gui.setStartDate(this.startDate);
        gui.setEndDate(this.endDate);
        gui.setType(this.type);
        String[] args = {};
        gui.main(args);
    }
}
