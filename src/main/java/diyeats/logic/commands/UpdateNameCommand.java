package diyeats.logic.commands;

import diyeats.commons.exceptions.ProgramException;
import diyeats.model.meal.MealList;
import diyeats.model.undo.Undo;
import diyeats.model.user.User;
import diyeats.model.wallet.Wallet;
import diyeats.storage.Storage;

//@@author koushireo

public class UpdateNameCommand extends Command {
    private String description;

    /**
     * Constructor for UpdateNameCommand.
     * @param description the data to update the user name with
     */

    public UpdateNameCommand(String description) {
        this.description = description;
    }

    public UpdateNameCommand(boolean flag, String messageStr) {
        this.isFail = true;
        this.errorStr = messageStr;
    }

    /**
     * Executes the UpdateNameCommand.
     * @param meals   the MealList object in which the meals are supposed to be added
     * @param storage the storage object that handles all reading and writing to files
     * @param user    the object that handles all user data
     * @param wallet  the wallet object that stores transaction information
     */
    @Override
    public void execute(MealList meals, Storage storage, User user, Wallet wallet, Undo undo) {
        ui.showLine();
        user.setName(description);
        ui.showSuccess("name", description);
        try {
            storage.writeUser(user);
        } catch (ProgramException e) {
            ui.showMessage(e.getMessage());
        }
        ui.showLine();
    }

    /**
     * Updates user's name during userSetup.
     * @param user user object that encapsulate the user data
     */

    public void updateUser(User user) {
        ui.showLine();
        user.setName(description);
        ui.showSuccess("name", description);
        ui.showLine();
    }

    /**
     * This function facilitates undo for updating name.
     * @param meals   the MealList object in which the meals are supposed to be added
     * @param storage the storage object that handles all reading and writing to files
     * @param user    the object that handles all user data
     * @param wallet  the wallet object that stores transaction information
     */

    public void undo(MealList meals, Storage storage, User user, Wallet wallet) {
        user.setName(description);
        try {
            storage.writeUser(user);
        } catch (ProgramException e) {
            ui.showMessage(e.getMessage());
        }
    }
}