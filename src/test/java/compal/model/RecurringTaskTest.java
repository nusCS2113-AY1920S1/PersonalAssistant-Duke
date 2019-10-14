package compal.model;

import compal.model.tasks.RecurringTask;
import compal.model.tasks.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static compal.model.tasks.Task.Priority.high;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RecurringTaskTest {
    private String description = "Test content";
    private String date = "01/10/2019";
    private String startTime = "1130";
    private String endTime = "1230";
    private String symbol = "RT";
    private Task.Priority priority = high;
    private RecurringTask recurringTask;

    @BeforeEach
    public void setUp() {
        recurringTask = new RecurringTask(description, priority, date, startTime, endTime);
    }

    @Test
    void getPriority() {
        assertEquals(priority, recurringTask.getPriority());
    }

    @Test
    void setPriorityTest() {
        recurringTask.setPriority(priority);
        assertEquals(priority, recurringTask.getPriority());
    }

    @Test
    void getStatusIcon() {
        recurringTask.isDone = true;
        assertEquals("\u2713",recurringTask.getStatusIcon());
        recurringTask.isDone = false;
        assertEquals("\u2718", recurringTask.getStatusIcon());
    }

    @Test
    void getIsDone() {
        recurringTask.isDone = true;
        assertEquals("true",recurringTask.getisDone());
        recurringTask.isDone = false;
        assertEquals("false", recurringTask.getisDone());
    }

    @Test
    void getHasReminder() {
        assertEquals("false", recurringTask.gethasReminder());
        recurringTask.setHasReminder();
        assertEquals("true",recurringTask.gethasReminder());
    }

    @Test
    void getSymbol() {
        assertEquals(symbol, recurringTask.getSymbol());
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
    void getStartTime() {
        SimpleDateFormat format = new SimpleDateFormat("HHmm");
        Date d = null;
        try {
            d = format.parse(startTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assertEquals(d, recurringTask.getStartTime());
    }

    @Test
    void setStartTimeTest() {
        Date d = recurringTask.getStartTime();
        recurringTask.setStartTime(startTime);
        assertEquals(d, recurringTask.getStartTime());
    }

    @Test
    void getStringStartTime() {
        assertEquals(startTime, recurringTask.getStringStartTime());
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
        assertEquals(d, recurringTask.getEndTime());
    }

    @Test
    void setEndTimeTest() {
        Date d = recurringTask.getEndTime();
        recurringTask.setEndTime(endTime);
        assertEquals(d, recurringTask.getEndTime());
    }

    @Test
    void getStringEndTime() {
        assertEquals(endTime, recurringTask.getStringEndTime());
    }

    @Test
    void setHasReminder() {
        assertEquals(false, recurringTask.hasReminder());
        recurringTask.setHasReminder();
        assertEquals(true, recurringTask.hasReminder());
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
        assertEquals("\n" + "[" + symbol + "]" + "[" + "\u2718" + "] " + description
                + " \nDate: " + date + " \nStart Time: " + startTime
                + " \nEnd Time: " + endTime + " \nPriority: " + priority
                + "\n***************", recurringTask.toString());
    }

    @Test
    void getAllDetailsTest() {
        StringBuilder list = new StringBuilder();
        list.append(symbol);
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
        list.append(recurringTask.gethasReminder());
        assertEquals(list.toString(), recurringTask.getAllDetailsAsString());
    }
}
