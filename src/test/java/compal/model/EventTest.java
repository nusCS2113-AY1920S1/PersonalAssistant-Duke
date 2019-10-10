package compal.model;

import compal.model.tasks.Event;
import compal.model.tasks.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static compal.model.tasks.Task.Priority.high;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventTest {
    private String description = "Test content";
    private String date = "01/10/2019";
    private String startTime = "1130";
    private String endTime = "1230";
    private Task.Priority priority = high;
    private Event event;

    @BeforeEach
    public void setup() {
        event = new Event(description, priority, date, startTime, endTime);
    }

    @Test
    void getPriority() {
        assertEquals(priority, event.getPriority());
    }

    @Test
    void setPriorityTest() {
        event.setPriority(priority);
        assertEquals(priority, event.getPriority());
    }

    @Test
    void getStatusIcon() {
        event.isDone = true;
        assertEquals("\u2713",event.getStatusIcon());
        event.isDone = false;
        assertEquals("\u2718", event.getStatusIcon());
    }

    @Test
    void getIsDone() {
        event.isDone = true;
        assertEquals("true",event.getisDone());
        event.isDone = false;
        assertEquals("false", event.getisDone());
    }

    @Test
    void getHasReminder() {
        assertEquals("false", event.gethasReminder());
        event.setHasReminder();
        assertEquals("true",event.gethasReminder());
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
    void getStartTime() {
        SimpleDateFormat format = new SimpleDateFormat("HHmm");
        Date d = null;
        try {
            d = format.parse(startTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assertEquals(d, event.getStartTime());
    }

    @Test
    void setStartTimeTest() {
        Date d = event.getStartTime();
        event.setStartTime(startTime);
        assertEquals(d, event.getStartTime());
    }

    @Test
    void getStringStartTime() {
        assertEquals(startTime, event.getStringStartTime());
    }

    @Test
    void getEndTime() {
        SimpleDateFormat format = new SimpleDateFormat("HHmm");
        Date d = null;
        try {
            d = format.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assertEquals(d, event.getEndTime());
    }

    @Test
    void setEndTimeTest() {
        Date d = event.getEndTime();
        event.setEndTime(endTime);
        assertEquals(d, event.getEndTime());
    }

    @Test
    void getStringEndTime() {
        assertEquals(endTime, event.getStringEndTime());
    }

    @Test
    void setHasReminder() {
        assertEquals(false, event.hasReminder());
        event.setHasReminder();
        assertEquals(true, event.hasReminder());
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
        assertEquals("\n" + "[E]" + "[" + "\u2718" + "] " + description
                + " \nDate: " + date + " \nStart Time: " + startTime
                + " \nEnd Time: " + endTime + " \nPriority: " + priority
                + "\n***************", event.toString());
    }

    @Test
    void getAllDetailsTest() {
        StringBuilder list = new StringBuilder();
        list.append("E");
        list.append("_");
        list.append(description);
        list.append("_");
        list.append(false);
        list.append("_");
        list.append(priority.toString());
        list.append("_");
        list.append(date);
        list.append("_");
        list.append(startTime);
        list.append("_");
        list.append(endTime);
        list.append("_");
        list.append(event.gethasReminder());
        assertEquals(list.toString(), event.getAllDetailsAsString());
    }
}
