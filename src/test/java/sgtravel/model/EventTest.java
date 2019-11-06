package sgtravel.model;

import sgtravel.commons.exceptions.DukeException;
import sgtravel.logic.parsers.ParserTimeUtil;
import sgtravel.model.locations.Venue;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EventTest {
    private Venue v1 = new Venue("YEW TEE INDUSTRIAL ESTATE", 1.3973210291170202, 103.753758637401, 0, 0);
    private Venue v2 = new Venue("Tuas Checkpoint", 1.34942405517095, 103.636127935782, 0, 0);

    @Test
    void testToString() throws DukeException {
        LocalDateTime startDate = ParserTimeUtil.parseStringToDate("12/12/12");
        LocalDateTime endDate = ParserTimeUtil.parseStringToDate("06/06/18");
        Event event = new Event("Pulau Ubin", startDate, endDate);
        assertEquals(event.toString(), "[E][✘] Pulau Ubin between "
                + ParserTimeUtil.stringify(startDate) + " and "
                + ParserTimeUtil.stringify(endDate));
        assertNotEquals(event.toString(), "");
    }

    @Test
    void getLocation() throws DukeException {
        LocalDateTime startDate = ParserTimeUtil.parseStringToDate("12/12/12");
        LocalDateTime endDate = ParserTimeUtil.parseStringToDate("06/06/18");
        Event event = new Event("Pulau Ubin", startDate, endDate);
        assertNotEquals(event.getLocation(), v1);
        event.setLocation(v1);
        assertEquals(event.getLocation(), v1);
    }

    @Test
    void setLocation() throws DukeException {
        LocalDateTime startDate = ParserTimeUtil.parseStringToDate("12/12/12");
        LocalDateTime endDate = ParserTimeUtil.parseStringToDate("06/06/18");
        Event event = new Event("Pulau Ubin", startDate, endDate);
        event.setLocation(v1);
        assertEquals(event.getLocation(), v1);
        event.setLocation(v1);
        assertEquals(event.getLocation(), v1);
        event.setLocation(v2);
        assertEquals(event.getLocation(), v2);
    }

    @Test
    void isSameTask() throws DukeException {
        LocalDateTime startDate = ParserTimeUtil.parseStringToDate("12/12/12");
        LocalDateTime endDate = ParserTimeUtil.parseStringToDate("06/06/18");
        Event event = new Event("Pulau Ubin", startDate, endDate);
        Event event1 = new Event("Pulau Ubin", startDate, endDate);
        assertTrue(event.isSameTask(event1));
        assertTrue(event1.isSameTask(event));
        event.setLocation(v1);
        assertFalse(event.isSameTask(event1));
        assertFalse(event1.isSameTask(event));
        event1.setLocation(v1);
        assertTrue(event.isSameTask(event1));
        assertTrue(event1.isSameTask(event));
    }
}
