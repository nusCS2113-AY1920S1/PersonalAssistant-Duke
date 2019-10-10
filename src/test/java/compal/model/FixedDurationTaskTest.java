package compal.model;

import compal.model.tasks.FixedDurationTask;
import compal.model.tasks.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static compal.model.tasks.Task.Priority.high;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FixedDurationTaskTest {
    private String description = "Test content";
    private String date = "01/10/2019";
    private String startTime = "1130";
    private String endTime = "1230";
    private Task.Priority priority = high;
    private FixedDurationTask fdt;

    @BeforeEach
    public void setup() {
        fdt = new FixedDurationTask(description, priority, date, startTime, endTime);
    }

    @Test
    void getPriority() {
        assertEquals(priority, fdt.getPriority());
    }

    @Test
    void setPriorityTest() {
        fdt.setPriority(priority);
        assertEquals(priority, fdt.getPriority());
    }

    @Test
    void getStatusIcon() {
        fdt.isDone = true;
        assertEquals("\u2713",fdt.getStatusIcon());
        fdt.isDone = false;
        assertEquals("\u2718", fdt.getStatusIcon());
    }

    @Test
    void getIsDone() {
        fdt.isDone = true;
        assertEquals("true",fdt.getisDone());
        fdt.isDone = false;
        assertEquals("false", fdt.getisDone());
    }

    @Test
    void getHasReminder() {
        assertEquals("false", fdt.gethasReminder());
        fdt.setHasReminder();
        assertEquals("true",fdt.gethasReminder());
    }

    @Test
    void getSymbol() {
        assertEquals("FDT", fdt.getSymbol());
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
        assertEquals(d, fdt.getDate());
    }

    @Test
    void setDateTest() {
        Date d = fdt.getDate();
        fdt.setDate(date);
        assertEquals(d, fdt.getDate());
    }

    @Test
    void getStringdate() {
        assertEquals(date, fdt.getStringDate());
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
        assertEquals(d, fdt.getStartTime());
    }

    @Test
    void setStartTimeTest() {
        Date d = fdt.getStartTime();
        fdt.setStartTime(startTime);
        assertEquals(d, fdt.getStartTime());
    }

    @Test
    void getStringStartTime() {
        assertEquals(startTime, fdt.getStringStartTime());
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
        assertEquals(d, fdt.getEndTime());
    }

    @Test
    void setEndTimeTest() {
        Date d = fdt.getEndTime();
        fdt.setEndTime(endTime);
        assertEquals(d, fdt.getEndTime());
    }

    @Test
    void getStringEndTime() {
        assertEquals(endTime, fdt.getStringEndTime());
    }

    @Test
    void setHasReminder() {
        assertEquals(false, fdt.hasReminder());
        fdt.setHasReminder();
        assertEquals(true, fdt.hasReminder());
    }

    @Test
    void getDescription() {
        assertEquals(description, fdt.getDescription());
    }

    @Test
    void markAsDoneTest() {
        fdt.markAsDone();
        assertEquals(true, fdt.isDone);
    }

    @Test
    void toStringTest() {
        assertEquals("\n" + "[FDT]" + "[" + "\u2718" + "] " + description
                + " \nDate: " + date + " \nStart Time: " + startTime
                + " \nEnd Time: " + endTime + " \nPriority: " + priority
                + "\n***************", fdt.toString());
    }

    @Test
    void getAllDetailsTest() {
        StringBuilder list = new StringBuilder();
        list.append("FDT");
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
        list.append(fdt.gethasReminder());
        assertEquals(list.toString(), fdt.getAllDetailsAsString());
    }
}
