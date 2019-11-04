package duke.model.lists;

import duke.commons.exceptions.DuplicateTaskException;
import duke.commons.exceptions.DukeException;
import duke.commons.exceptions.QueryOutOfBoundsException;
import duke.logic.parsers.ParserTimeUtil;
import duke.model.Event;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EventListTest {

    @Test
    void add() throws DukeException {
        EventList events = new EventList();
        LocalDateTime startDate = ParserTimeUtil.parseStringToDate("12/12/12");
        LocalDateTime endDate = ParserTimeUtil.parseStringToDate("06/06/18");
        Event event = new Event("Pulau Ubin", startDate, endDate);
        events.add(event);
        assertThrows(DuplicateTaskException.class, () -> events.add(event));
    }

    @Test
    void isEmpty() throws DukeException {
        EventList events = new EventList();
        assertTrue(events.isEmpty());
        LocalDateTime startDate = ParserTimeUtil.parseStringToDate("12/12/12");
        LocalDateTime endDate = ParserTimeUtil.parseStringToDate("06/06/18");
        Event event = new Event("Pulau Ubin", startDate, endDate);
        events.add(event);
        assertFalse(events.isEmpty());
        Event event1 = new Event("Orchard", startDate, endDate);
        events.add(event1);
        assertFalse(events.isEmpty());
    }

    @Test
    void contains() throws DukeException {
        EventList events = new EventList();
        LocalDateTime startDate = ParserTimeUtil.parseStringToDate("12/12/12");
        LocalDateTime endDate = ParserTimeUtil.parseStringToDate("06/06/18");
        Event event = new Event("Pulau Ubin", startDate, endDate);
        assertFalse(events.contains(event));
        events.add(event);
        assertTrue(events.contains(event));
    }

    @Test
    void size() throws DukeException {
        EventList events = new EventList();
        assertEquals(events.size(), 0);
        LocalDateTime startDate = ParserTimeUtil.parseStringToDate("12/12/12");
        LocalDateTime endDate = ParserTimeUtil.parseStringToDate("06/06/18");
        Event event = new Event("Pulau Ubin", startDate, endDate);
        events.add(event);
        assertEquals(events.size(), 1);
        events.remove(0);
        assertEquals(events.size(), 0);
    }

    @Test
    void get() throws DukeException {
        EventList events = new EventList();
        LocalDateTime startDate = ParserTimeUtil.parseStringToDate("12/12/12");
        LocalDateTime endDate = ParserTimeUtil.parseStringToDate("06/06/18");
        final Event event = new Event("Pulau Ubin", startDate, endDate);
        assertThrows(IndexOutOfBoundsException.class, () -> events.get(0));
        assertThrows(IndexOutOfBoundsException.class, () -> events.get(1));
        assertThrows(IndexOutOfBoundsException.class, () -> events.get(-1));
        events.add(event);
        assertEquals(events.get(0), event);
        assertThrows(IndexOutOfBoundsException.class, () -> events.get(1));
        assertThrows(IndexOutOfBoundsException.class, () -> events.get(-1));
    }

    @Test
    void sort() throws DukeException {
        EventList events = new EventList();
        LocalDateTime startDate = ParserTimeUtil.parseStringToDate("12/12/12");
        LocalDateTime endDate = ParserTimeUtil.parseStringToDate("06/06/18");
        Event event = new Event("Pulau Ubin", startDate.plusDays(5), endDate);
        Event event1 = new Event("Woodlands", startDate.minusDays(2), endDate);
        Event event2 = new Event("Jurong east", startDate, endDate.plusDays(5));
        Event event3 = new Event("Kranji", startDate, endDate.minusDays(2));
        Event event4 = new Event("Tampines mrt", startDate, endDate);
        events.add(event);
        events.add(event1);
        events.add(event2);
        events.add(event3);
        events.add(event4);
        events.sort();
        assertEquals(events.get(0), event1);
        assertEquals(events.get(1), event3);
        assertEquals(events.get(2), event4);
        assertEquals(events.get(3), event2);
        assertEquals(events.get(4), event);
        events.sort();
        assertEquals(events.get(0), event1);
        assertEquals(events.get(1), event3);
        assertEquals(events.get(2), event4);
        assertEquals(events.get(3), event2);
        assertEquals(events.get(4), event);
        Event event5 = new Event("City Hall mrt", startDate.minusDays(10), endDate);
        events.add(event5);
        events.sort();
        assertEquals(events.get(0), event5);
        assertEquals(events.get(1), event1);
        assertEquals(events.get(2), event3);
        assertEquals(events.get(3), event4);
        assertEquals(events.get(4), event2);
        assertEquals(events.get(5), event);
    }

    @Test
    void getSortedList() throws DukeException {
        EventList events = new EventList();
        LocalDateTime startDate = ParserTimeUtil.parseStringToDate("12/12/12");
        LocalDateTime endDate = ParserTimeUtil.parseStringToDate("06/06/18");
        Event event = new Event("Pulau Ubin", startDate.plusDays(5), endDate);
        Event event1 = new Event("Woodlands", startDate.minusDays(2), endDate);
        Event event2 = new Event("Jurong east", startDate, endDate.plusDays(5));
        Event event3 = new Event("Kranji", startDate, endDate.minusDays(2));
        Event event4 = new Event("Tampines mrt", startDate, endDate);
        events.add(event);
        events.add(event1);
        events.add(event2);
        events.add(event3);
        events.add(event4);
        EventList events1 = events.getSortedList();
        assertEquals(events1.get(0), event1);
        assertEquals(events1.get(1), event3);
        assertEquals(events1.get(2), event4);
        assertEquals(events1.get(3), event2);
        assertEquals(events1.get(4), event);
        EventList events2 = events.getSortedList();
        assertNotEquals(events2, events1);
        Event event5 = new Event("City Hall mrt", startDate.minusDays(10), endDate);
        events.add(event5);
        events1 = events.getSortedList();
        assertEquals(events1.get(0), event5);
        assertEquals(events1.get(1), event1);
        assertEquals(events1.get(2), event3);
        assertEquals(events1.get(3), event4);
        assertEquals(events1.get(4), event2);
        assertEquals(events1.get(5), event);
        assertEquals(events.get(0), event);
        assertEquals(events.get(1), event1);
        assertEquals(events.get(2), event2);
        assertEquals(events.get(3), event3);
        assertEquals(events.get(4), event4);
        assertEquals(events.get(5), event5);
    }

    @Test
    void iterator() throws DukeException {
        EventList events = new EventList();
        int i = 0;
        for (Event e: events) {
            assertEquals(e, events.get(i));
            i++;
        }
        LocalDateTime startDate = ParserTimeUtil.parseStringToDate("12/12/12");
        LocalDateTime endDate = ParserTimeUtil.parseStringToDate("06/06/18");
        Event event = new Event("Pulau Ubin", startDate.plusDays(5), endDate);
        Event event1 = new Event("Woodlands", startDate.minusDays(2), endDate);
        Event event2 = new Event("Jurong east", startDate, endDate.plusDays(5));
        Event event3 = new Event("Kranji", startDate, endDate.minusDays(2));
        Event event4 = new Event("Tampines mrt", startDate, endDate);
        events.add(event);
        events.add(event1);
        events.add(event2);
        events.add(event3);
        events.add(event4);
        i = 0;
        for (Event e: events) {
            assertEquals(e, events.get(i));
            i++;
        }
    }

    @Test
    void setEvents() throws DukeException {
        List<Event> events = new ArrayList<>();
        LocalDateTime startDate = ParserTimeUtil.parseStringToDate("12/12/12");
        LocalDateTime endDate = ParserTimeUtil.parseStringToDate("06/06/18");
        Event event = new Event("Pulau Ubin", startDate.plusDays(5), endDate);
        Event event1 = new Event("Woodlands", startDate.minusDays(2), endDate);
        Event event2 = new Event("Jurong east", startDate, endDate.plusDays(5));
        Event event3 = new Event("Kranji", startDate, endDate.minusDays(2));
        Event event4 = new Event("Tampines mrt", startDate, endDate);
        events.add(event);
        events.add(event1);
        events.add(event2);
        events.add(event3);
        events.add(event4);
        EventList eventList = new EventList();
        eventList.setEvents(events);
        events.add(event2);
        assertThrows(DuplicateTaskException.class, () -> eventList.setEvents(events));
    }

    @Test
    void remove() throws DukeException {
        EventList events = new EventList();
        LocalDateTime startDate = ParserTimeUtil.parseStringToDate("12/12/12");
        LocalDateTime endDate = ParserTimeUtil.parseStringToDate("06/06/18");
        Event event = new Event("Pulau Ubin", startDate.plusDays(5), endDate);
        Event event1 = new Event("Woodlands", startDate.minusDays(2), endDate);
        Event event2 = new Event("Jurong east", startDate, endDate.plusDays(5));
        Event event3 = new Event("Kranji", startDate, endDate.minusDays(2));
        Event event4 = new Event("Tampines mrt", startDate, endDate);
        events.add(event);
        events.add(event1);
        events.add(event2);
        events.add(event3);
        events.add(event4);
        assertTrue(events.contains(event));
        events.remove(0);
        assertFalse(events.contains(event));
        events.remove(0);
        assertFalse(events.contains(event1));
        events.remove(0);
        assertFalse(events.contains(event2));
        assertThrows(QueryOutOfBoundsException.class, () -> events.remove(-1));
        assertThrows(QueryOutOfBoundsException.class, () -> events.remove(2));
    }
}
