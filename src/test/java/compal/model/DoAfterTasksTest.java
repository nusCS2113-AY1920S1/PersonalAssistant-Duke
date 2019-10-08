package compal.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static compal.model.Task.Priority.high;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class DoAfterTasksTest {
    private String description = "Test content";
    private String date = "01/10/2019";
    private DoAfterTasks doAfterTasks;
    private Task.Priority priority = high;


    @BeforeEach
    public void setup() {
        doAfterTasks = new DoAfterTasks(description, priority, date);
    }

    @Test
    void getStatusIcon() {
        assertEquals("\u2718", doAfterTasks.getStatusIcon());
    }

    @Test
    void getSymbol() {
        assertEquals("DAT", doAfterTasks.getSymbol());
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
        assertEquals(d, doAfterTasks.getDate());
    }

    @Test
    void setDateTest() {
        Date d = doAfterTasks.getDate();
        doAfterTasks.setDate(date);
        assertEquals(d, doAfterTasks.getDate());
    }

    @Test
    void getStringdate() {
        assertEquals(date, doAfterTasks.getStringDate());
    }


    @Test
    void hasReminder() {
        assertEquals(false, doAfterTasks.hasReminder());
    }

    @Test
    void getTime() {
        assertNull(doAfterTasks.getStartTime());
    }

    @Test
    void getDescription() {
        assertEquals(description, doAfterTasks.getDescription());
    }

    @Test
    void markAsDoneTest() {
        doAfterTasks.markAsDone();
        assertEquals(true, doAfterTasks.isDone);
    }
}
