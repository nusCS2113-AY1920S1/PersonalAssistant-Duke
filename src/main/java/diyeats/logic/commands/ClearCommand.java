package diyeats.logic.commands;

import diyeats.commons.exceptions.ProgramException;
import diyeats.model.meal.MealList;
import diyeats.model.user.User;
import diyeats.model.wallet.Wallet;
import diyeats.storage.Storage;

import java.time.LocalDate;

//@@author HashirZahir
/**
 * ClearCommand is a public class that inherits from abstract class Command.
 * A ClearCommand object encapsulates the 2 dates between which all meal data will be cleared.
 */
public class ClearCommand extends Command {
    LocalDate startDate;
    LocalDate endDate;

    /**
     * Constructor for ClearCommand.
     * @param startDate the start of the time period to be cleared, inclusive.
     * @param endDate the end of the time period to be cleared, inclusive.
     */
    public ClearCommand(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // This constructor called if there are issues parsing user input as dates.
    public ClearCommand(boolean flag, String messageStr) {
        this.isFail = true;
        this.errorStr = messageStr;
    }

    /**
     * Executes the ClearCommand.
     * @param meals the MealList object in which the meals are supposed to be added
     * @param storage the storage object that handles all reading and writing to files
     * @param user the object that handles all user data
     * @param wallet the wallet object that stores transaction information
     */
    @Override
    public void execute(MealList meals, Storage storage, User user, Wallet wallet) {
        ui.showLine();
        for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
            meals.deleteAllMealsOnDate(date);
        }
        ui.showCleared(dateFormat.format(startDate), dateFormat.format(endDate));
        try {
            storage.updateFile(meals);
        } catch (ProgramException e) {
            ui.showMessage(e.getMessage());
        }
        ui.showLine();
    }
}
