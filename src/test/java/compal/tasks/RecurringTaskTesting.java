package compal.tasks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class RecurringTaskTesting {
    private RecurringTask recurringTask;
    final private String description = "Test content";
    final private String date = "01/10/2019";
    final private String time = "1230";

    @BeforeEach
    public void setup() {
        recurringTask = new RecurringTask(description, date, time);
    }

    @Test
    void getStatusIcon() {
        assertEquals("\u2718", recurringTask.getStatusIcon());
    }

    @Test
    void getSymbol() {
        assertEquals("RT", recurringTask.getSymbol());
    }

    @Test
    void getDate() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date d = null;
        try {
            d = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assertEquals(d, recurringTask.getDate());
    }

    @Test
    void setDateTest() {
        Date d = recurringTask.getDate();
        recurringTask.setDate(date);
        assertEquals(d, recurringTask.getDate());
    }

    @Test
    void getStringdate() {
        assertEquals(date, recurringTask.getStringDate());
    }

    @Test
    void getDurationHour() {
        assertNull(recurringTask.getDurationHour());
    }

    @Test
    void getDurationMinute() {
        assertNull(recurringTask.getDurationMinute());
    }

    @Test
    void isHasReminder() {
        assertEquals(false, recurringTask.isHasReminder());
    }

    @Test
    void getTime() {
        SimpleDateFormat format = new SimpleDateFormat("HHmm");
        Date t = null;
        try {
            t = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assertEquals(t, recurringTask.getTime());
    }

    @Test
    void setTimeTest() {
        Date t = recurringTask.getTime();
        recurringTask.setTime(time);
        assertEquals(t, recurringTask.getTime());
    }

    @Test
    void getStringTime() {
        assertEquals(time, recurringTask.getStringTime());
    }

    @Test
    void getDescription() {
        assertEquals(description, recurringTask.getDescription());
    }

    @Test
    void markAsDoneTest() {
        recurringTask.markAsDone();
        assertEquals(true, recurringTask.isDone);
    }

    @Test
    void toStringTest() {
        assertEquals("[" + recurringTask.getSymbol() + "]" + "[" + recurringTask.getStatusIcon() + "] "
                + recurringTask.getDescription() + " Date: " + recurringTask.getStringDate() + " Time: "
                + recurringTask.getStringTime(), recurringTask.toString());
    }
}
