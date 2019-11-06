package duke.logic.commands;

import duke.commons.exceptions.DukeException;
import duke.model.meal.MealList;
import duke.model.user.User;
import duke.model.wallet.Wallet;
import duke.storage.Storage;

import java.time.LocalDate;
import java.util.HashMap;

//@@author koushireo

public class UpdateHeightCommand extends Command {
    private String description;

    /**
     * Constructor for UpdateHeightCommand.
     * @param description the data to update the user document with
     */

    public UpdateHeightCommand(String description) {
        this.description = description;
    }

    public UpdateHeightCommand(boolean flag, String messageStr) {
        this.isFail = true;
        this.errorStr = messageStr;
    }

    /**
     * Executes the UpdateHeightCommand.
     * @param meals the MealList object in which the meals are supposed to be added
     * @param storage the storage object that handles all reading and writing to files
     * @param user the object that handles all user data
     * @param wallet the wallet object that stores transaction information
     */
    @Override
    public void execute(MealList meals, Storage storage, User user, Wallet wallet) {
        int heightInt = 0;
        try {
            heightInt = Integer.parseInt(description);
            if (heightInt < 54) {
                ui.showMessage("Height cannot be less than 54cm(Unless you really are the shortest man on earth!)");
            } else if (heightInt > 272) {
                ui.showMessage("Height cannot be more than 272cm(Unless you really are the tallest man on earth!)");
            } else {
                user.setHeight(heightInt);
                ui.showSuccess("height", description);
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