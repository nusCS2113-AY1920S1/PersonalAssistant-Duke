//@@author YuanJiayi
import mistermusik.commons.events.eventtypes.Event;
import mistermusik.commons.events.eventtypes.eventsubclasses.recurringeventsubclasses.Lesson;
import mistermusik.commons.events.eventtypes.eventsubclasses.recurringeventsubclasses.Practice;
import mistermusik.logic.ClashException;
import mistermusik.logic.EventList;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RecurringEventTest {
    @Test
    public void addRecurringEventTest() throws ClashException {
        ArrayList<String> testListString = new ArrayList<>();
        EventList testList = new EventList(testListString);

        // test practice type
        Event practiceTest = new Practice("practice 1", "06-11-2019 1200", "06-11-2019 1400");
        testList.addRecurringEvent(practiceTest, 60);
        assertEquals(2, testList.getNumEvents());

        // test lesson type
        Event lessonTest = new Lesson("lesson 1", "13-08-2019 1000", "13-08-2019 1200");
        testList.addRecurringEvent(lessonTest, 35);
        assertEquals(6, testList.getNumEvents());

        // test the period larger than one semester
        Event largePeriodTest = new Practice("practice 2", "23-09-2019 0900", "23-09-2019 1000");
        testList.addRecurringEvent(largePeriodTest, 113);
        assertEquals(7, testList.getNumEvents());

        // test the period exactly one semester (112 days)
        Event exactOneSemesterPeriodTest = new Lesson("lesson 2", "07-10-2019 0800", "07-10-2019 0900");
        testList.addRecurringEvent(exactOneSemesterPeriodTest, 112);
        assertEquals(9, testList.getNumEvents());

        // test the period just shorter than 112 days
        Event smallPeriodTest = new Practice("practice 3", "14-12-2019 1800", "14-12-2019 1900");
        testList.addRecurringEvent(smallPeriodTest, 111);
        assertEquals(11, testList.getNumEvents());

        // test recurring lesson with "isDone"
        Event notDoneLessonTest = new Lesson("lesson", false,"01-01-2020 2200", "01-01-2020 2300");
        testList.addRecurringEvent(notDoneLessonTest, 120);
        assertEquals(12, testList.getNumEvents());

        // test clash
        Event clashTest = new Lesson("lesson 3", "14-12-2019 1800", "14-12-2019 1900");
        try {
            testList.addRecurringEvent(clashTest, 100);
        } catch (ClashException e) {
            assertEquals(e.getClashEvent().toString(), clashTest.toString());
        }
    }
}
