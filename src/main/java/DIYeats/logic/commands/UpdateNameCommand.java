package DIYeats.logic.commands;

import DIYeats.commons.exceptions.DukeException;
import DIYeats.model.meal.MealList;
import DIYeats.model.user.User;
import DIYeats.model.wallet.Wallet;
import DIYeats.storage.Storage;

//@@author koushireo

public class UpdateNameCommand extends Command {
    private String description;

    /**
     * Constructor for UpdateNameCommand.
     *
     * @param description the data to update the user document with
     */

    public UpdateNameCommand(String description) {
        this.description = description;
    }

    public UpdateNameCommand(boolean flag, String messageStr) {
        this.isFail = true;
        this.errorStr = messageStr;
    }

    /**
     * Executes the UpdateHeightCommand.
     *
     * @param meals   the MealList object in which the meals are supposed to be added
     * @param storage the storage object that handles all reading and writing to files
     * @param user    the object that handles all user data
     * @param wallet  the wallet object that stores transaction information
     */
    @Override
    public void execute(MealList meals, Storage storage, User user, Wallet wallet) {
        ui.showLine();
        user.setName(description);
        ui.showSuccess("name", description);
        try {
            storage.updateUser(user);
        } catch (DukeException e) {
            ui.showMessage(e.getMessage());
        }
        ui.showLine();
    }
}