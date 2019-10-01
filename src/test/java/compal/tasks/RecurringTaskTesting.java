package compal.tasks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static compal.tasks.Task.Priority.high;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class RecurringTaskTesting {
    private String description = "Test content";
    private String date = "01/10/2019";
    private String time = "1230";
    private String symbol = "LAB";
    private Task.Priority priority = high;
    private RecurringTask recurringTask;

    @BeforeEach
    public void setup() {
        recurringTask = new RecurringTask(description, high, date, time, symbol);
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
    void hasReminder() {
        assertEquals(false, recurringTask.hasReminder());
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
        assertEquals(t, recurringTask.getStartTime());
    }

    @Test
    void setTimeTest() {
        Date t = recurringTask.getStartTime();
        recurringTask.setStartTime(time);
        assertEquals(t, recurringTask.getStartTime());
    }

    @Test
    void getStringTime() {
        assertEquals(time, recurringTask.getStringStartTime());
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
        assertEquals("[" + recurringTask.getSymbol() + "]"
                + "[" + recurringTask.getStatusIcon() + "] "
                + recurringTask.getDescription() + " Date: "
                + recurringTask.getStringDate() + " Time: "
                + recurringTask.getStringStartTime() + " Priority: "
                + recurringTask.getPriority(), recurringTask.toString());
    }
}
