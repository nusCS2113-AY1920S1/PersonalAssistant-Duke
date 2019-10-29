import CustomExceptions.RoomShareException;
import Enums.Priority;
import Enums.RecurrenceScheduleType;
import Enums.TimeUnit;
import Operations.TaskCreator;
import javafx.util.Pair;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class TaskCreatorTest {
    private static TaskCreator taskCreator = new TaskCreator();
    private static String input1 = "add #meeting# (description) &22/12/2019 18:00& *high* %week% " +
            "@john@ ^2 hours^ !R!";
    private static String input2 = "add #meeting# (description) &23/12/2019 18:00&";
    private static String input3 = "add #leave# (description) &24/12/2019 18:00&25/12/2019 18:00&";
    @Test
    void extractDescription() {
        try {
            assertEquals(taskCreator.extractDescription(input1), "description");
        } catch (RoomShareException e) {
            e.printStackTrace();
        }
    }

    @Test
    void extractDate() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date1 = new Date();
        Date date2 = new Date();
        Date date3 = new Date();
        try {
            date1 = format.parse("22/12/2019 18:00");
            date2 = format.parse("24/12/2019 18:00");
            date3 = format.parse("25/12/2019 18:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ArrayList<Date> dates = new ArrayList<>();
        ArrayList<Date> dates2 = new ArrayList<>();
        dates2.add(date2);
        dates2.add(date3);
        dates.add(date1);
        try {
            assertEquals(taskCreator.extractDate(input1), dates);
        } catch (RoomShareException e) {
            e.printStackTrace();
        }
        try {
            assertEquals(taskCreator.extractDate(input3), dates2);
        } catch (RoomShareException e) {
            e.printStackTrace();
        }
    }

    @Test
    void extractType() {
        try {
            assertEquals(taskCreator.extractType(input1), "meeting");
        } catch (RoomShareException e) {
            e.printStackTrace();
        }
        try {
            assertEquals(taskCreator.extractType(input2), "meeting");
        } catch (RoomShareException e) {
            e.printStackTrace();
        }
        try {
            assertEquals(taskCreator.extractType(input3), "leave");
        } catch (RoomShareException e) {
            e.printStackTrace();
        }
    }

    @Test
    void extractPriority() {
        assertEquals(taskCreator.extractPriority(input1), Priority.high);
        assertEquals(taskCreator.extractPriority(input2), Priority.low);
    }

    @Test
    void extractAssignee() {
        assertEquals(taskCreator.extractAssignee(input1), "john");
        assertEquals(taskCreator.extractAssignee(input2), "everyone");
    }

    @Test
    void extractRecurrence() {
        assertEquals(taskCreator.extractRecurrence(input1), RecurrenceScheduleType.week);
        assertEquals(taskCreator.extractRecurrence(input2), RecurrenceScheduleType.none);
    }

    @Test
    void extractDuration() {
        Pair<Integer, TimeUnit> pair = new Pair<>(2, TimeUnit.hours);
        Pair<Integer, TimeUnit> pair2 = new Pair<>(0, TimeUnit.unDefined);
        assertEquals(taskCreator.extractDuration(input1), pair);
        assertEquals(taskCreator.extractDuration(input2), pair2);
    }

    @Test
    void extractReminder() {
        assertTrue(taskCreator.extractReminder(input1));
        assertFalse(taskCreator.extractReminder(input2));
    }

    @Test
    void create() {

    }
}
