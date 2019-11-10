//@@author YuanJiayi
import mistermusik.commons.events.eventtypes.Event;
import mistermusik.commons.events.formatting.EventDate;
import mistermusik.logic.EventList;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RescheduleTest {
    @Test
    public void rescheduleStartDateTest() {
        ArrayList<String> readFromFile = new ArrayList<>();
        String fileContent;
        fileContent = "XP/practice 1 /03-12-2019 1500/03-12-2019 1800";
        readFromFile.add(fileContent);
        EventList eventListTest = new EventList(readFromFile);
        // test reschedule start date and time of an event
        Event practiceTest = eventListTest.getEvent(0);
        EventDate newPracticeStartDate = new EventDate("09-11-2019 0000");
        practiceTest.rescheduleStartDate(newPracticeStartDate);
        assertEquals(newPracticeStartDate, practiceTest.getStartDate());
    }

    @Test
    public void rescheduleEndDateTest() {
        ArrayList<String> readFromFile = new ArrayList<>();
        String fileContent;
        fileContent = "XP/practice 1 /03-12-2019 1500/03-12-2019 1800";
        readFromFile.add(fileContent);
        EventList eventListTest = new EventList(readFromFile);
        Event practiceTest = eventListTest.getEvent(0);
        // test reschedule end date and time of an event
        EventDate newPracticeEndDate = new EventDate("09-11-2019 0100");
        practiceTest.rescheduleEndDate(newPracticeEndDate);
        assertEquals(newPracticeEndDate, practiceTest.getEndDate());
    }
}
