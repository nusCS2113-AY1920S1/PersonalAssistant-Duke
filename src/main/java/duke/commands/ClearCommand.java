package duke.commands;

import duke.exceptions.DukeException;
import duke.storage.Storage;
import duke.tasks.MealList;
import duke.ui.Ui;
import duke.user.User;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * ClearCommand is a public class that inherits from abstract class Command.
 * A ClearCommand object encapsulates the 2 dates between which all meal data will be cleared.
 */
public class ClearCommand extends Command {
    Date startDate, endDate;
    public ClearCommand(String startDateStr, String endDateStr) throws DukeException{
        try {
            startDate = dateFormat.parse(startDateStr);
            endDate = dateFormat.parse(endDateStr);
        } catch (ParseException e) {
            throw new DukeException("Unable to parse input " + startDateStr + " and " + endDateStr + " as dates.");
        }
    }

    @Override
    public void execute(MealList mealList, Ui ui, Storage storage, User user){
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        for (cal.setTime(startDate); !cal.getTime().after(endDate); cal.add(Calendar.DATE, 1)) {
            mealList.deleteAllMealsOnDate(dateFormat.format(cal.getTime()));
        }
        ui.showCleared(dateFormat.format(startDate), dateFormat.format(endDate));
        storage.updateFile(mealList);
    }
}
