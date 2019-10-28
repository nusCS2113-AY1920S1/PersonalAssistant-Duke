package duke.logic.commands;

import duke.ui.InputHandler;
import duke.commons.exceptions.DukeException;
import duke.model.TransactionList;
import duke.storage.Storage;
import duke.model.Goal;
import duke.model.MealList;
import duke.ui.Ui;
import duke.model.user.User;

/**
 * AddGoalCommand is a public class that inherits from abstract class Command.
 * An AddGoalCommand object encapsulates the goal object that is to be added.
 * average kg loss per day should NOT be more than 0.13607787283kg (>0.3 pounds)
 * average calorie loss per day should NOT exceed 40% base calorie intake,
 * balanced at around 20% (1kg = 7700cal = 7.7kcal)
 */
public class AddGoalCommand extends Command {
    private Goal goal = new Goal();
    private User user;
    private Ui ui;
    private InputHandler in;

    /**
     * Constructor for AddGoalCommand.
     */
    public AddGoalCommand() {
    }

    /**
     * Executes AddGoalCommand.
     * @param meals the MealList object in which the meal is supposed to be added
     * @param ui the ui object to display the user interface of an "add" command
     * @param storage the storage object that stores the list of meals
     * @param in the scanner object to handle secondary command IO
     */
    @Override
    public void execute(MealList meals, Ui ui, Storage storage, User user,
                        InputHandler in, TransactionList transactions) throws DukeException {
        this.ui = ui;
        this.user = user;
        this.in = in;
        askForStartDate();;
        askForEndDate();
        askForWeightTarget();
        askForTargetLifestyle();
        writeGoalToUser(user);
        storage.updateGoal(user);
    }

    private void askForStartDate() throws DukeException {
        ui.showQueryStartDate();
        String date = in.getDate();
        goal.setStartDate(date);
    }

    private void askForEndDate() throws DukeException {
        ui.showQueryEndDate();
        String date = in.getDate();
        goal.setEndDate(date);
    }

    private void askForWeightTarget() throws DukeException {
        ui.showQueryTargetWeight();
        double weight = in.getDouble();
        goal.setWeightTarget(weight);
    }

    private void askForTargetLifestyle() throws DukeException {
        int activityLevel = 5;
        ui.showQueryTargetLifestyle();
        if (in.getApproval()) {
            while (activityLevel > 4 || activityLevel < 0) {
                ui.showActivityLevel();
                activityLevel = in.getInt() - 1;
            }
        }
        goal.setLifestyleTarget(activityLevel);
    }

    private void writeGoalToUser(User user) throws DukeException {
        if (user.setGoal(goal, false) == false) {
            ui.queryOverrideExistingGoal();
            if (in.getApproval()) {
                user.setGoal(goal, true);
                ui.succeedSetGoal();
            } else {
                ui.failSetGoal();
            }
        } else {
            ui.succeedSetGoal();
        }
    }
}
