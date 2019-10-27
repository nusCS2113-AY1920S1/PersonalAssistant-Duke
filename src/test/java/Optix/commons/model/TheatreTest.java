package optix.commons.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TheatreTest {
    private Theatre theatre;

    @BeforeEach
    void init() {
        theatre = new Theatre("Test Show", 20);
    }

    @Test
    void testGetSeatingArrangement() {
        String expected ="                |STAGE|           \n"
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
        assertEquals(expected, theatre.getSeatingArrangement());
    }

    @Test
    void testSellSeats() {
        theatre.sellSeats("A1", "A2"); // test sell seat function
        String expected ="                |STAGE|           \n"
                + "  [✓][✓][✘][✘][✘][✘][✘][✘][✘][✘]\n"
                + "  [✘][✘][✘][✘][✘][✘][✘][✘][✘][✘]\n"
                + "  [✘][✘][✘][✘][✘][✘][✘][✘][✘][✘]\n"
                + "  [✘][✘][✘][✘][✘][✘][✘][✘][✘][✘]\n"
                + "  [✘][✘][✘][✘][✘][✘][✘][✘][✘][✘]\n"
                + "  [✘][✘][✘][✘][✘][✘][✘][✘][✘][✘]\n"
                + "\n"
                + "Tier 1 Seats: 20\n"
                + "Tier 2 Seats: 20\n"
                + "Tier 3 Seats: 18\n";
        assertEquals(expected, theatre.getSeatingArrangement());
        assertEquals(60, theatre.getProfit());
        Seat[][] seats = theatre.getSeats();
        assertTrue(seats[0][0].isBooked());
        assertTrue(seats[0][1].isBooked());

        theatre.sellSeats("A0"); // test none existent seat
        assertEquals(expected, theatre.getSeatingArrangement());
        assertEquals(60, theatre.getProfit());
    }

    @Test
    void testReassignSeat() {
        theatre.sellSeats("A1");
        theatre.reassignSeat("A1", "A2"); // successful reassignment of seats.
        String expected ="                |STAGE|           \n"
                + "  [✘][✓][✘][✘][✘][✘][✘][✘][✘][✘]\n"
                + "  [✘][✘][✘][✘][✘][✘][✘][✘][✘][✘]\n"
                + "  [✘][✘][✘][✘][✘][✘][✘][✘][✘][✘]\n"
                + "  [✘][✘][✘][✘][✘][✘][✘][✘][✘][✘]\n"
                + "  [✘][✘][✘][✘][✘][✘][✘][✘][✘][✘]\n"
                + "  [✘][✘][✘][✘][✘][✘][✘][✘][✘][✘]\n"
                + "\n"
                + "Tier 1 Seats: 20\n"
                + "Tier 2 Seats: 20\n"
                + "Tier 3 Seats: 19\n";
        assertEquals(expected, theatre.getSeatingArrangement());
        theatre.reassignSeat("A1", "A0"); // unsuccessful reassignment of seats.
        assertEquals(expected, theatre.getSeatingArrangement());
    }

    @Test
    void testRemoveSeat() {
        theatre.sellSeats("A1", "A2", "A3");
        assertEquals(90, theatre.getProfit());
        theatre.removeSeat("A3");
        String expected ="                |STAGE|           \n"
                + "  [✓][✓][✘][✘][✘][✘][✘][✘][✘][✘]\n"
                + "  [✘][✘][✘][✘][✘][✘][✘][✘][✘][✘]\n"
                + "  [✘][✘][✘][✘][✘][✘][✘][✘][✘][✘]\n"
                + "  [✘][✘][✘][✘][✘][✘][✘][✘][✘][✘]\n"
                + "  [✘][✘][✘][✘][✘][✘][✘][✘][✘][✘]\n"
                + "  [✘][✘][✘][✘][✘][✘][✘][✘][✘][✘]\n"
                + "\n"
                + "Tier 1 Seats: 20\n"
                + "Tier 2 Seats: 20\n"
                + "Tier 3 Seats: 18\n";
        assertEquals(60, theatre.getProfit());
        assertEquals(expected, theatre.getSeatingArrangement());
        theatre.removeSeat("A0");
        assertEquals(60, theatre.getProfit());
        assertEquals(expected, theatre.getSeatingArrangement());
    }

    @Test
    void testWriteToFile() {
        String expected = "Test Show | 0.000000 | 20.000000\n";
        assertEquals(expected, theatre.writeToFile());
    }
}