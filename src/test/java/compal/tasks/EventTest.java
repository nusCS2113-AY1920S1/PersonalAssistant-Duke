package compal.tasks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class EventTest {
    private String description = "Test content";
    private Task.Priority priority = null;
    private String date = "01/10/2019";
    private String time = "1230";
    private Event event;

    @BeforeEach
    public void setup() {
        event = new Event(description, priority, date, time);
    }

    @Test
    void getStatusIcon() {
        assertEquals("\u2718", event.getStatusIcon());
    }

    @Test
    void getSymbol() {
        assertEquals("E", event.getSymbol());
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
        assertEquals(d, event.getDate());
    }

    @Test
    void setDateTest() {
        Date d = event.getDate();
        event.setDate(date);
        assertEquals(d, event.getDate());
    }

    @Test
    void getStringdate() {
        assertEquals(date, event.getStringDate());
    }

    @Test
    void getDurationHour() {
        assertNull(event.getDurationHour());
    }

    @Test
    void getDurationMinute() {
        assertNull(event.getDurationMinute());
    }

    @Test
    void isHasReminder() {
        assertEquals(false, event.hasReminder());
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
        assertEquals(t, event.getTime());
    }

    @Test
    void setTimeTest() {
        Date t = event.getTime();
        event.setTime(time);
        assertEquals(t, event.getTime());
    }

    @Test
    void getStringTime() {
        assertEquals(time, event.getStringTime());
    }

    @Test
    void getDescription() {
        assertEquals(description, event.getDescription());
    }

    @Test
    void markAsDoneTest() {
        event.markAsDone();
        assertEquals(true, event.isDone);
    }

    @Test
    void toStringTest() {
        assertEquals("[" + event.getSymbol() + "]" + "[" + event.getStatusIcon() + "] "
                + event.getDescription() + " Date: " + event.getStringDate() + " Time: "
                + event.getStringTime(), event.toString());
    }
}
