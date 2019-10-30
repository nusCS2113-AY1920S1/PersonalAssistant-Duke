import Enums.Priority;
import Enums.RecurrenceScheduleType;
import Model_Classes.Leave;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class LeaveTest {
    private static SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private static Date from;
    private static Date to;
    private static String user = "user";

    static {
        try {
            from = format.parse("22/12/2019 18:00");
            to = format.parse("24/12/2019 22:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private Leave leave = new Leave("description", user, from, to);

    @Test
    void getDescription() {
        assertEquals(leave.getDescription(), "description");
    }

    @Test
    void getStartDate() {
        assertEquals(leave.getStartDate().toString(), "Sun Dec 22 18:00:00 SGT 2019");
    }

    @Test
    void getEndDate() {
        assertEquals(leave.getEndDate().toString(), "Tue Dec 24 22:00:00 SGT 2019");
    }

    @Test
    void getAssignee() {
        assertEquals(leave.getAssignee(), "user");
    }

    @Test
    void getPriority() {
        assertEquals(leave.getPriority(), Priority.low);
    }

    @Test
    void getDone() {
        assertFalse(leave.getDone());
    }

    @Test
    void getRecurrenceSchedule() {
        assertEquals(leave.getRecurrenceSchedule(), RecurrenceScheduleType.none);
    }
}
