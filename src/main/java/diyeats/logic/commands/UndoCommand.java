package diyeats.logic.commands;

import diyeats.commons.exceptions.ProgramException;
import diyeats.model.meal.Meal;
import diyeats.model.meal.MealList;
import diyeats.model.undo.Undo;
import diyeats.model.user.User;
import diyeats.model.wallet.Wallet;
import diyeats.storage.Storage;

import java.util.ArrayList;

/**
 * AddCommand is a public class that inherits from abstract class Command.
 * An AddCommand object encapsulates the current meal that is to be added.
 */
public class UndoCommand extends Command {
    /**
     * Constructor for AddCommand.
     * the meal specified as the instance field meal.
     */
    public UndoCommand(){
    }

    public UndoCommand(boolean flag, String messageStr) {
        this.isFail = true;
        this.errorStr = messageStr;
    }

    /**
     * Executes add command.
     * @param meals the MealList object in which the meals are supposed to be added
     * @param storage the storage object that handles all reading and writing to files
     * @param user the object that handles all user data
     * @param wallet the wallet object that stores transaction information
     */
    public void execute(MealList meals, Storage storage, User user, Wallet wallet, Undo undo) {
        try {
            undo.execute(meals, storage, user, wallet);
        } catch (ProgramException e) {
            ui.showMessage(e.getMessage());
        }
    }

}