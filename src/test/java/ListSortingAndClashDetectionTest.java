//@@author Ryan-Wong-Ren-Wei

import mistermusik.commons.budgeting.CostExceedsBudgetException;
import mistermusik.commons.events.eventtypes.Event;
import mistermusik.commons.events.eventtypes.eventsubclasses.Concert;
import mistermusik.commons.events.eventtypes.eventsubclasses.ToDo;
import mistermusik.commons.events.eventtypes.eventsubclasses.recurringeventsubclasses.Lesson;
import mistermusik.commons.events.eventtypes.eventsubclasses.recurringeventsubclasses.Practice;
import mistermusik.logic.ClashException;
import mistermusik.logic.EndBeforeStartException;
import mistermusik.logic.EventList;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public class ListSortingAndClashDetectionTest {
    @Test
    /**
     * This method tests that the clash handler works when singular events are added to the list.
     */
    public void clashTest() throws CostExceedsBudgetException, EndBeforeStartException {
        ArrayList<String> readFromFile = new ArrayList<String>();
        String fileContent;
        fileContent = "XT/fawpeifwe/02-12-2019";
        readFromFile.add(fileContent);
        fileContent = "XP/apiejfpwiefw/03-12-2019 1500/03-12-2019 1800";
        readFromFile.add(fileContent);
        fileContent = "XC/halloween/04-12-2019 1600/04-12-2019 1930/13";
        readFromFile.add(fileContent);

        EventList eventListTest = new EventList(readFromFile);
        Event testEvent = new Practice("Horn practice", "3-12-2019 1400",
                "3-12-2019 1600");
        try {
            eventListTest.addEvent(testEvent);
            fail();
        } catch (ClashException e) {
            assertEquals(eventListTest.getEvent(1), e.getClashEvent());
        }

        testEvent = new Lesson("Some lesson", "3-12-2019 1700",
                "3-12-2019 1900");
        try {
            eventListTest.addEvent(testEvent);
            fail();
        } catch (ClashException e) {
            assertEquals(eventListTest.getEvent(1), e.getClashEvent());
        }

        testEvent = new Concert("Some other concert", "4-12-2019 1500",
                "4-12-2019 1600", 0);
        try {
            eventListTest.addEvent(testEvent);
        } catch (ClashException e) {
            fail();
        }

        testEvent = new Concert("Some other concert", "4-12-2019 1500",
                "4-12-2019 1601", 0);
        try {
            eventListTest.addEvent(testEvent);
            fail();
        } catch (ClashException e) {
            assertEquals(eventListTest.getEvent(2), e.getClashEvent());
        }

    }

    @Test
    /**
     * This methods tests that the schedule clash handler triggers when recurring events
     * are added to the list.
     */
    public void clashTestRecurring() {
        ArrayList<String> readFromFile = new ArrayList<String>();
        String fileContent;
        fileContent = "XT/fawpeifwe/02-12-2019";
        readFromFile.add(fileContent);
        fileContent = "XP/apiejfpwiefw/03-12-2019 1500/03-12-2019 1800";
        readFromFile.add(fileContent);
        fileContent = "XC/halloween/04-12-2019 1600/04-12-2019 1930/3";
        readFromFile.add(fileContent);

        EventList eventListTest = new EventList(readFromFile);

        Event testEvent1 = new Practice("Horn practice", "28-11-2019 1400",
                "28-11-2019 1600");
        assertThrows(ClashException.class, () -> {
            eventListTest.addRecurringEvent(testEvent1, 5);
        });

        Event testEvent2 = new Lesson("Theory lesson", "28-11-2019 1400",
                "28-11-2019 1600");
        try {
            eventListTest.addRecurringEvent(testEvent2, 10);

        } catch (ClashException e) {
            fail();
        }
    }

    /**
     * This method tests the automatic sorting function that should trigger whenever new events are added
     * to the list. Events are expected to be sorted in increasing order by date.
     */
    @Test
    public void testSorting() throws Exception {
        ArrayList<String> readFromFile = new ArrayList<String>();
        String fileContent;
        fileContent = "XT/fawpeifwe/02-12-2019";
        readFromFile.add(fileContent);
        fileContent = "XP/apiejfpwiefw/03-12-2019 1500/03-12-2019 1800";
        readFromFile.add(fileContent);
        fileContent = "XC/halloween/04-12-2019 1600/04-12-2019 1930/5";
        readFromFile.add(fileContent);

        EventList eventListTest = new EventList(readFromFile);
        boolean succeeded = true;
        Event testEvent1 = new Practice("Horn practice", "05-12-2019 1400",
                "05-12-2019 1600");
        Event testEvent2 = new Lesson("Full Orchestra rehearsal", "03-12-2019 1400",
                "03-12-2019 1500");
        Event testEvent3 = new ToDo("Complete theory homework CS2113", "01-12-2019");

        eventListTest.addEvent(testEvent1);
        eventListTest.addEvent(testEvent2);
        eventListTest.addNewTodo(testEvent3);
        eventListTest.sortList();

        ArrayList<Event> sortedEventList = new ArrayList<>();
        sortedEventList.add(new ToDo("Complete theory homework CS2113", "01-12-2019"));
        sortedEventList.add(new ToDo("fawpeifwe", "02-12-2019"));
        sortedEventList.add(new Lesson("Full Orchestra rehearsal", "03-12-2019 1400",
                "03-12-2019 1500"));
        sortedEventList.add(new Practice("apiejfpwiefw", "03-12-2019 1500",
                "03-12-2019 1800"));
        sortedEventList.add(new Concert("halloween", "04-12-2019 1600",
                "04-12-2019 1930", 5));
        sortedEventList.add(new Practice("Horn practice", "05-12-2019 1400",
                "05-12-2019 1600"));

        int i = 0;
        for (Event currEvent : eventListTest.getEventArrayList()) {
            assertEquals(currEvent.toString(), sortedEventList.get(i).toString());
            ++i;
        }
    }
}
