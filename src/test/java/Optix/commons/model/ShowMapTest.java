package optix.commons.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ShowMapTest {
    private LocalDate date1 = LocalDate.of(2020, 10, 10);
    private LocalDate date2 = LocalDate.of(2020, 11, 11);
    private ShowMap shows = new ShowMap();

    @BeforeEach
    void init() {
        shows.addShow("Test Show", date1, 20);
    }

    @Test
    void testEditShowName() {
        shows.editShowName(date1, "Edited Show");
        assertEquals("Edited Show", shows.getShowName(date1));
        assertEquals(1, shows.size());
    }

    @Test
    void testPostponeShow() {
        shows.postponeShow(date1, date2);
        assertFalse(shows.containsKey(date1));
        assertTrue(shows.containsKey(date2));
    }

    @Test
    void testListShow() {
        shows.addShow("Test Show 2", date2, 20);
        String expected = "1. Test Show (on: 10/10/2020)\n" + "2. Test Show 2 (on: 11/11/2020)\n";
        assertEquals(expected, shows.listShow());
    }

    @Test
    void testDeleteShow() {
        shows.deleteShow(date1);
        assertTrue(shows.isEmpty());
    }

    @Test
    void testViewSeats() {
        String expected = "                |STAGE|           \n"
                + "  [✘][✘][✘][✘][✘][✘][✘][✘][✘][✘]\n"
                + "  [✘][✘][✘][✘][✘][✘][✘][✘][✘][✘]\n"
                + "  [✘][✘][✘][✘][✘][✘][✘][✘][✘][✘]\n"
                + "  [✘][✘][✘][✘][✘][✘][✘][✘][✘][✘]\n"
                + "  [✘][✘][✘][✘][✘][✘][✘][✘][✘][✘]\n"
                + "  [✘][✘][✘][✘][✘][✘][✘][✘][✘][✘]\n"
                + "\n"
                + "Tier 1 Seats: 20\n"
                + "Tier 2 Seats: 20\n"
                + "Tier 3 Seats: 20\n";
        assertEquals(expected, shows.viewSeats(date1));
    }

    @Test
    void testSellSeats() {
        String expected = "You have successfully purchased the following seats: \n"
                          + "[A1, A2]\n"
                          + "The total cost of the ticket is $60.00\n";
        assertEquals(expected, shows.sellSeats(date1, "A1", "A2"));
        expected = "☹ OOPS!!! All of the seats [A1, A2] are unavailable\n";
        assertEquals(expected, shows.sellSeats(date1, "A1", "A2"));
    }

    @Test
    void testReassignSeat() {
        shows.sellSeats(date1, "A1", "A3");
        String expected = "☹ OOPS!!! Please enter valid seat numbers.\n";
        assertEquals(expected, shows.reassignSeat(date1, "A0", "A0"));
        expected = "Your current seat is already A1.\n";
        assertEquals(expected, shows.reassignSeat(date1, "A1", "A1"));
        expected = "The seat A2 is still available for booking.\n";
        assertEquals(expected, shows.reassignSeat(date1, "A2", "A1"));
        expected = "☹ OOPS!!! Seat A1 is unavailable. Use the View Command to"
                + " view the available seats.\n";
        assertEquals(expected, shows.reassignSeat(date1, "A3", "A1"));
    }
}