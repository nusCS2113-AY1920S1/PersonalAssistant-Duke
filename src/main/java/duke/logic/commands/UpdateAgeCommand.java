package duke.logic.commands;

import duke.commons.exceptions.DukeException;
import duke.model.meal.MealList;
import duke.model.user.User;
import duke.model.wallet.Wallet;
import duke.storage.Storage;

import java.time.LocalDate;
import java.util.HashMap;

//@@author koushireo

public class UpdateAgeCommand extends Command {
    private String description;
    private String weight;

    /**
     * Constructor for UpdateWeightCommand.
     * @param description the data to update the user document with
     */

    public UpdateAgeCommand(String description) {
        this.description = description;
    }

    public UpdateAgeCommand(boolean flag, String messageStr) {
        this.isFail = true;
        this.errorStr = messageStr;
    }

    /**
     * Executes the UpdateAgeCommand.
     * @param meals the MealList object in which the meals are supposed to be added
     * @param storage the storage object that handles all reading and writing to files
     * @param user the object that handles all user data
     * @param wallet the wallet object that stores transaction information
     */
    @Override
    public void execute(MealList meals, Storage storage, User user, Wallet wallet) {
        int ageInt = 0;
        try {
            ageInt = Integer.parseInt(description);
            if (ageInt < 0) {
                ui.showMessage("Age cannot be less than 0");
            } else {
                user.setAge(ageInt);
                ui.showSuccess("age", description);
                try {
                    storage.updateUser(user);
                } catch (DukeException e) {
                    ui.showMessage(e.getMessage());
                }
            }
        } catch (NumberFormatException e) {
            ui.showMessage("Please input a proper number");
        }
    }
}
