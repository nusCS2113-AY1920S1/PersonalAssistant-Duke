package diyeats.logic.commands;

import diyeats.commons.exceptions.ProgramException;
import diyeats.model.meal.MealList;
import diyeats.model.user.User;
import diyeats.model.wallet.Wallet;
import diyeats.storage.Storage;

//@@author koushireo

public class UpdateActivityCommand extends Command {
    private String description;

    /**
     * Constructor for UpdateActivityCommand.
     * @param description the data to update the user document with
     */

    public UpdateActivityCommand(String description) {
        this.description = description;
    }

    public UpdateActivityCommand(boolean flag, String messageStr) {
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
        ui.showLine();
        int activityInt = 0;
        try {
            activityInt = Integer.parseInt(description);
            if (activityInt < 1) {
                ui.showMessage("Activity Level cannot be less than 1");
                ui.showActivityLevel();
            } else if (activityInt > 5) {
                ui.showMessage("Activity Level cannot be more than 5");
                ui.showActivityLevel();
            } else {
                user.setActivityLevel(activityInt);
                ui.showSuccess("Activity Level", description);
                try {
                    storage.updateUser(user);
                } catch (ProgramException e) {
                    ui.showMessage(e.getMessage());
                }
            }
        } catch (NumberFormatException e) {
            ui.showMessage("Please input a proper number for activity level");
        }
        ui.showLine();
    }

    public void updateUser(User user) {
        ui.showLine();
        int activityInt = 0;
        try {
            activityInt = Integer.parseInt(description);
            if (activityInt < 1) {
                ui.showMessage("Activity Level cannot be less than 1");
                ui.showActivityLevel();
            } else if (activityInt > 5) {
                ui.showMessage("Activity Level cannot be more than 5");
                ui.showActivityLevel();
            } else {
                user.setActivityLevel(activityInt);
                ui.showSuccess("Activity Level", description);
            }
        } catch (NumberFormatException e) {
            ui.showMessage("Please input a proper number for activity level");
        }
        ui.showLine();
    }
}