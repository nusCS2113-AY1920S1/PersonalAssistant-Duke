import CustomExceptions.RoomShareException;
import Enums.Priority;
import Enums.RecurrenceScheduleType;
import Enums.TimeUnit;
import Model_Classes.Meeting;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class MeetingTest {
    private static SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private static Date date;

    static {
        try {
            date = format.parse("22/12/2019 18:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    private static Meeting meeting1 = new Meeting("description", date);
    private static Meeting meeting2 = new Meeting("description", date, 2, TimeUnit.hours);

    @Test
    void getDescription() {
        assertEquals(meeting1.getDescription(), "description");
        assertEquals(meeting2.getDescription(), "description");
    }

    @Test
    void getDone() {
        assertFalse(meeting1.getDone());
        try {
            meeting2.setDone(true);
        } catch (RoomShareException e) {
            e.printStackTrace();
        }
        assertTrue(meeting2.getDone());
    }

    @Test
    void getDate() {
        assertEquals(meeting1.getDate().toString(), "Sun Dec 22 18:00:00 SGT 2019" );
    }

    @Test
    void getPriority() {
        assertEquals(meeting1.getPriority(), Priority.low);
        meeting2.setPriority(Priority.high);
        assertEquals(meeting2.getPriority(), Priority.high);
    }

    @Test
    void getAssignee() {
        assertEquals(meeting1.getAssignee(), "everyone");
        meeting2.setAssignee("john");
        assertEquals(meeting2.getAssignee(), "john");
    }

    @Test
    void getTimeUnit() {
        assertEquals(meeting2.getTimeUnit(), TimeUnit.hours);
    }

    @Test
    void getDuration() {
        assertEquals(meeting2.getDuration(), "2");
    }

    @Test
    void getRecurrenceSchedule() {
        assertEquals(meeting1.getRecurrenceSchedule(), RecurrenceScheduleType.none);
        assertEquals(meeting2.getRecurrenceSchedule(), RecurrenceScheduleType.none);
    }
}
