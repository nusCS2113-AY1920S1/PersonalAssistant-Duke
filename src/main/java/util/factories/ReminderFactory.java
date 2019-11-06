package util.factories;

import models.reminder.IReminder;
import models.reminder.NullReminder;
import models.reminder.Reminder;
import util.ParserHelper;
import util.date.DateTimeHelper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class ReminderFactory {

    private ParserHelper parserHelper;
    private DateTimeHelper dateTimeHelper;

    public ReminderFactory() {
        this.parserHelper = new ParserHelper();
        this.dateTimeHelper = new DateTimeHelper();
    }

    /**
     * Method to create a new reminder and put it into the remidner list.
     * @param input Input containing the information about the reminder.
     * @return Reminder as an object
     */
    public IReminder createReminder(String input) throws ParseException {
        if (!input.contains("-n")) {
            return new NullReminder();
        }

        String [] reminderDetails = input.split("-");
        ArrayList<String> newReminderDetails = parserHelper.parseReminderDetails(reminderDetails);
        String newReminderName = newReminderDetails.get(0);
        String newReminderRemarks = newReminderDetails.get(1);
        Date newReminderDate = null;
        if (newReminderDetails.get(2) != null) {
            newReminderDate = dateTimeHelper.formatDate(newReminderDetails.get(2));
        }

        return new Reminder(newReminderName, newReminderRemarks, newReminderDate);
    }
}
