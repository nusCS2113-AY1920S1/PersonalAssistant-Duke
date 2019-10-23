package duke.logic.commands;

import duke.commons.exceptions.DukeException;
import duke.model.TransactionList;
import duke.storage.Storage;
import duke.model.Meal;
import duke.model.MealList;
import duke.ui.Ui;
import duke.model.user.User;

import java.util.Scanner;

public class EditCommand extends Command {
    private Meal updatedMeal;

    public EditCommand(Meal meal) {
        this.updatedMeal = meal;
    }

    @Override
    public void execute(MealList meals, Ui ui, Storage storage, User user, Scanner in, TransactionList transactions) throws DukeException {
        updatedMeal = meals.updateMeal(updatedMeal);
        String dateStr = updatedMeal.getDate();
        ui.showUpdated(this.updatedMeal, meals.getMealsList(this.updatedMeal.getDate()), user, dateStr);
        storage.updateFile(meals);
    }
}
