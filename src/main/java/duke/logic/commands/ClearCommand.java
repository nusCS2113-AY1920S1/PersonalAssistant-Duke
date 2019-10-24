package duke.logic.commands;

import duke.commons.exceptions.DukeException;
import duke.model.TransactionList;
import duke.storage.Storage;
import duke.model.MealList;
import duke.ui.Ui;
import duke.model.user.User;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

/**
 * ClearCommand is a public class that inherits from abstract class Command.
 * A ClearCommand object encapsulates the 2 dates between which all meal data will be cleared.
 */
public class ClearCommand extends Command {
    Date startDate;
    Date endDate;

    public ClearCommand(String startDateStr, String endDateStr) throws DukeException {
        try {
            startDate = dateFormat.parse(startDateStr);
            endDate = dateFormat.parse(endDateStr);
        } catch (ParseException e) {
            throw new DukeException("Unable to parse input " + startDateStr + " and " + endDateStr + " as dates.");
        }
    }

    @Override
    public void execute(MealList mealList, Ui ui, Storage storage, User user,
                        Scanner in, TransactionList transactions) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        for (cal.setTime(startDate); !cal.getTime().after(endDate); cal.add(Calendar.DATE, 1)) {
            mealList.deleteAllMealsOnDate(dateFormat.format(cal.getTime()));
        }
        ui.showCleared(dateFormat.format(startDate), dateFormat.format(endDate));
        storage.updateFile(mealList);
    }
}
