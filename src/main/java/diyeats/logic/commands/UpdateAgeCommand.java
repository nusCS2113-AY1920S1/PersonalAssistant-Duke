package diyeats.logic.commands;

import diyeats.commons.exceptions.ProgramException;
import diyeats.model.meal.MealList;
import diyeats.model.user.User;
import diyeats.model.wallet.Wallet;
import diyeats.storage.Storage;

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
        ui.showLine();
        int ageInt = 0;
        try {
            ageInt = Integer.parseInt(description);
            if (ageInt < 0) {
                ui.showMessage("Age cannot be less than 0");
            } else if (ageInt > 122) {
                ui.showMessage("Age cannot be more than 122(Unless you really are the oldest person in the world!");
            } else {
                user.setAge(ageInt);
                ui.showSuccess("age", description);
                try {
                    storage.updateUser(user);
                } catch (ProgramException e) {
                    ui.showMessage(e.getMessage());
                }
            }
        } catch (NumberFormatException e) {
            ui.showMessage("Please input a proper number for age");
        }
        ui.showLine();
    }

    public void updateUser(User user) {
        ui.showLine();
        int ageInt = 0;
        try {
            ageInt = Integer.parseInt(description);
            if (ageInt < 0) {
                ui.showMessage("Age cannot be less than 0");
            } else if (ageInt > 122) {
                ui.showMessage("Age cannot be more than 122(Unless you really are the oldest person in the world!");
            } else {
                user.setAge(ageInt);
                ui.showSuccess("age", description);
            }
        } catch (NumberFormatException e) {
            ui.showMessage("Please input a proper number for age");
        }
        ui.showLine();
    }
}
