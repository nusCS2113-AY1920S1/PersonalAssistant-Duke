package compal.tasks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FixedDurationTaskTest {
    private FixedDurationTask fixedDurationTask;
    final private String description = "Test content";
    final private String date = "01/10/2019";
    final private String time = "1230";
    final private int hour = 2;
    final private int minute = 25;

    @BeforeEach
    public void setup() {
        fixedDurationTask = new FixedDurationTask(description, date, time, hour, minute);
    }

    @Test
    void getStatusIcon() {
        assertEquals("\u2718", fixedDurationTask.getStatusIcon());
    }

    @Test
    void getSymbol() {
        assertEquals("FDT", fixedDurationTask.getSymbol());
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
        assertEquals(d, fixedDurationTask.getDate());
    }

    @Test
    void setDateTest() {
        Date d = fixedDurationTask.getDate();
        fixedDurationTask.setDate(date);
        assertEquals(d, fixedDurationTask.getDate());
    }

    @Test
    void getStringdate() {
        assertEquals(date, fixedDurationTask.getStringDate());
    }

    @Test
    void getDurationHour() {
        assertEquals(hour, fixedDurationTask.getDurationHour());
    }

    @Test
    void setDurationHourTest() {
        fixedDurationTask.setDurationHour(hour);
        assertEquals(hour, fixedDurationTask.getDurationHour());
    }

    @Test
    void getDurationMinute() {
        assertEquals(minute, fixedDurationTask.getDurationMinute());
    }

    @Test
    void setDuraitonMinuteTest() {
        fixedDurationTask.setDurationMinute(minute);
        assertEquals(minute, fixedDurationTask.getDurationMinute());
    }

    @Test
    void isHasReminder() {
        assertEquals(false, fixedDurationTask.isHasReminder());
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
        assertEquals(t, fixedDurationTask.getTime());
    }

    @Test
    void setTimeTest() {
        Date t = fixedDurationTask.getTime();
        fixedDurationTask.setTime(time);
        assertEquals(t, fixedDurationTask.getTime());
    }

    @Test
    void getStringTime() {
        assertEquals(time, fixedDurationTask.getStringTime());
    }

    @Test
    void getDescription() {
        assertEquals(description, fixedDurationTask.getDescription());
    }

    @Test
    void markAsDoneTest() {
        fixedDurationTask.markAsDone();
        assertEquals(true, fixedDurationTask.isDone);
    }

    @Test
    void toStringTest() {
        assertEquals("[" + fixedDurationTask.getSymbol() + "]"
                + "[" + fixedDurationTask.getStatusIcon() + "] " + fixedDurationTask.getDescription()
                + " Date: " + fixedDurationTask.getStringDate() + " Hour: " + fixedDurationTask.getDurationHour()
                + " Min: " + fixedDurationTask.getDurationMinute(), fixedDurationTask.toString());
    }
}
