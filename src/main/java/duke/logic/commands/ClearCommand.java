package duke.logic.commands;

import duke.commons.exceptions.DukeException;
import duke.model.meal.MealList;
import duke.model.user.User;
import duke.model.wallet.Wallet;
import duke.storage.Storage;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

//@@author HashirZahir
/**
 * ClearCommand is a public class that inherits from abstract class Command.
 * A ClearCommand object encapsulates the 2 dates between which all meal data will be cleared.
 */
public class ClearCommand extends Command {
    Date startDate;
    Date endDate;

    /**
     * Constructor for ClearCommand.
     * @param startDateStr the start of the time period to be cleared, inclusive
     * @param endDateStr the end of the time period to be cleared, inclusive
     * @throws DukeException if the inputs cannot be parsed
     */
    public ClearCommand(String startDateStr, String endDateStr) {
        try {
            startDate = dateFormat.parse(startDateStr);
            endDate = dateFormat.parse(endDateStr);
        } catch (ParseException e) {
            ui.showMessage("Unable to parse input " + startDateStr + " and " + endDateStr + " as dates.");
        }
    }

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
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        for (cal.setTime(startDate); !cal.getTime().after(endDate); cal.add(Calendar.DATE, 1)) {
            meals.deleteAllMealsOnDate(dateFormat.format(cal.getTime()));
        }
        ui.showCleared(dateFormat.format(startDate), dateFormat.format(endDate));
        try {
            storage.updateFile(meals);
        } catch (DukeException e) {
            ui.showMessage(e.getMessage());
        }
        ui.showLine();
    }
}
