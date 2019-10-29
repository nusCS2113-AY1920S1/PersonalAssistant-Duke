import Enums.Priority;
import Enums.RecurrenceScheduleType;
import Model_Classes.Assignment;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class AssignmentTest {
    private static SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private static Date date;

    static {
        try {
            date = format.parse("22/12/2019 18:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private Assignment assignment = new Assignment("description", date);

    @Test
    void getDescription() {
        assertEquals(assignment.getDescription(), "description");
    }

    @Test
    void getDate() {
        assertEquals(assignment.getDate().toString(), "Sun Dec 22 18:00:00 SGT 2019");
    }

    @Test
    void getDone() {
        assertFalse(assignment.getDone());
    }

    @Test
    void getPriority() {
        assertEquals(assignment.getPriority(), Priority.low);
    }

    @Test
    void getAssignee() {
        assertEquals(assignment.getAssignee(), "everyone");
    }

    @Test
    void getRecurrenceSchedule() {
        assertEquals(assignment.getRecurrenceSchedule(), RecurrenceScheduleType.none);
    }
}
