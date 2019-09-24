package compal.tasks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

class DeadlineTest {
    private Deadline deadline;
    final private String description = "Test content";
    final private String date = "01/10/2019";

    @BeforeEach
    public void setup() {
        deadline = new Deadline(description, date);
    }

    @Test
    void getStatusIcon() {
        assertEquals("\u2718", deadline.getStatusIcon());
    }

    @Test
    void getSymbol() {
        assertEquals("D", deadline.getSymbol());
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
        assertEquals(d, deadline.getDate());
    }

    @Test
    void setDateTest() {
        Date d = deadline.getDate();
        deadline.setDate(date);
        assertEquals(d, deadline.getDate());
    }

    @Test
    void getStringdate() {
        assertEquals(date, deadline.getStringDate());
    }

    @Test
    void getDurationHour() {
        assertNull(deadline.getDurationHour());
    }

    @Test
    void getDurationMinute() {
        assertNull(deadline.getDurationMinute());
    }

    @Test
    void isHasReminder() {
        assertEquals(false, deadline.isHasReminder());
    }

    @Test
    void getTime() {
        assertNull(deadline.getTime());
    }

    @Test
    void getDescription() {
        assertEquals(description, deadline.getDescription());
    }

    @Test
    void markAsDoneTest() {
        deadline.markAsDone();
        assertEquals(true, deadline.isDone);
    }

    @Test
    void toStringTest() {
        assertEquals("[" + deadline.getSymbol() + "]" + "[" + deadline.getStatusIcon() + "] "
                + deadline.getDescription() + " Date: " + deadline.getStringDate(), deadline.toString());
    }
}
