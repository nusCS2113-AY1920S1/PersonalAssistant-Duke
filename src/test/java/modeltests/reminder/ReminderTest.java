package modeltests.reminder;

import models.reminder.Reminder;
import org.junit.jupiter.api.Test;
import util.date.DateTimeHelper;


import java.text.ParseException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReminderTest {
    private DateTimeHelper dateTimeHelper = new DateTimeHelper();

    /**
     * Always true test just to test if jUnit is working.
     */
    @Test
    void alwaysTrue() {
        assertEquals(2, 2);
    }

    @Test
    void testReminderDetails() {
        try {
            Date date = dateTimeHelper.formatDate("10/10/2019");
            Reminder reminder = new Reminder("Fix Captain America's Shield paint job", "maybe try pink instead", date);
            assertEquals("Fix Captain America's Shield paint job",reminder.getReminderName());
            assertEquals("maybe try pink instead",reminder.getReminderRemarks());
            assertEquals(date,reminder.getReminderDate());
        } catch (ParseException e) {
            System.out.println("Parsing error");
        }
    }
}
