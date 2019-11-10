package unit;

import javafx.util.Pair;
import org.junit.jupiter.api.Test;
import spinbox.DateTime;
import spinbox.entities.Calendar;
import spinbox.entities.items.tasks.Lecture;
import spinbox.entities.items.tasks.Task;
import spinbox.exceptions.DateFormatException;
import spinbox.exceptions.ScheduleDateException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class CalendarTest {
    @Test
    public void calendarCreation_variousDateTimeString_successfulCreationAndExpectedDayOfWeekOfStartDate() {
        Calendar test1 = null;
        Calendar test2 = null;
        Calendar test3 = null;

        test1 = new Calendar("12/07/2029");
        test2 = new Calendar("11/07/2029");
        test3 = new Calendar("11/04/2029");
        assertEquals(7, test1.getStartDateDay());
        assertEquals(5, test2.getStartDateDay());
        assertEquals(5, test3.getStartDateDay());
    }


    @Test
    public void getEndOfMonthDay_variousDateTimeString_successfulCreationAndExpectedEndOfMonthDay() {
        Calendar test1 = null;
        Calendar test2 = null;
        Calendar test3 = null;

        test1 = new Calendar("02/07/2029");
        test2 = new Calendar("11/07/2029");
        test3 = new Calendar("08/04/2029");
        assertEquals(28, test1.getEndOfMonthDay());
        assertEquals(30, test2.getEndOfMonthDay());
        assertEquals(31, test3.getEndOfMonthDay());
    }

    @Test
    public void getYearString_variousDateTimeString_successfulCreationAndExpectedString() {
        Calendar test1 = null;
        Calendar test2 = null;
        Calendar test3 = null;

        test1 = new Calendar("02/01/2029");
        test2 = new Calendar("11/07/2010");
        test3 = new Calendar("08/04/2011");

        assertEquals("2029", test1.getYearString());
        assertEquals("2010", test2.getYearString());
        assertEquals("2011", test3.getYearString());
    }

    @Test
    public void getMonthString_variousDateTimeString_successfulCreationAndExpectedString() {
        Calendar test1 = null;
        Calendar test2 = null;
        Calendar test3 = null;

        test1 = new Calendar("02/01/2029");
        test2 = new Calendar("11/07/2010");
        test3 = new Calendar("08/04/2011");

        assertEquals("February", test1.getMonthString());
        assertEquals("November", test2.getMonthString());
        assertEquals("August", test3.getMonthString());
    }

    @Test
    public void getTaskWithinCalendar_TaskListAndDateTimeString_returnCorrectTask() {

        try {
            List<Pair<String, Task>> testTaskList = new ArrayList<>();
            Calendar testCalendar = new Calendar("11/07/2029");
            testTaskList.add(new Pair<>("CS1231", new Lecture("lecture 1",
                    new DateTime("11/08/2029 14:00"), new DateTime("11/08/2029 18:00"))));
            testTaskList.add(new Pair<>("CS2040C", new Lecture("lecture 2",
                    new DateTime("11/09/2029 14:00"), new DateTime("11/09/2029 18:00"))));
            testTaskList.add(new Pair<>("GET1101", new Lecture("lecture 3",
                    new DateTime("11/09/2029 14:00"), new DateTime("12/09/2029 18:00"))));
            testTaskList.add(new Pair<>("MA1511",new Lecture("lecture 4",
                    new DateTime("11/09/2029 14:00"), new DateTime("12/09/2029 18:00"))));
            Task lectureOne = testCalendar.taskInCalendarByDayInMonth(testTaskList).get(7).getValue().get(0).getValue();
            Task lectureTwo = testCalendar.taskInCalendarByDayInMonth(testTaskList).get(8).getValue().get(0).getValue();
            assertEquals("[LEC][NOT DONE] lecture 1 (at: 11/08/2029 14:00 to 11/08/2029 18:00)",
                    lectureOne.toString());
            assertEquals("[LEC][NOT DONE] lecture 2 (at: 11/09/2029 14:00 to 11/09/2029 18:00)",
                    lectureTwo.toString());
            Boolean overlap = testCalendar.taskInCalendarByDayInMonth(testTaskList).get(9).getValue().isEmpty();
            assertEquals(false, overlap);
        } catch (ScheduleDateException | DateFormatException e) {
            fail(e.getMessage());
        }

    }
}
