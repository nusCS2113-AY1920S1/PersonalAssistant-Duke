package duke.commands;

import duke.exceptions.DukeException;
import duke.storage.Storage;
import duke.tasks.Meal;
import duke.tasks.MealList;
import duke.ui.Ui;
import duke.user.User;

public class EditCommand extends Command {
    private Meal updatedMeal;

    public EditCommand(Meal meal) {
        this.updatedMeal = meal;
    }

    @Override
    public void execute(MealList meals, Ui ui, Storage storage, User user) throws DukeException {
        updatedMeal = meals.updateMeal(updatedMeal);
        String dateStr = updatedMeal.getDate();
        ui.showUpdated(this.updatedMeal, meals.getMealsList(this.updatedMeal.getDate()), user, dateStr);
        storage.updateFile(meals);
    }
}
