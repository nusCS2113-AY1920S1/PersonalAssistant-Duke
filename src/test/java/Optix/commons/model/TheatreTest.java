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
    void testCreate() {
        theatre = new Theatre("Test Show", 2000, 20);
        assertEquals(2000, theatre.getProfit());
        Show show = new Show("Dummy Show Name", 4000);
        theatre = new Theatre(show);
        assertEquals(show.getShowName(), theatre.getShowName());
        assertEquals(show.getProfit(), theatre.getProfit());
    }

    @Test
    void testGetSeatingArrangement() {
        String expected = "                |STAGE|           \n"
                        + "  [✘][✘][✘][✘][✘][✘][✘][✘][✘][✘]\n"
                        + "  [✘][✘][✘][✘][✘][✘][✘][✘][✘][✘]\n"
                        + "  [✘][✘][✘][✘][✘][✘][✘][✘][✘][✘]\n"
                        + "  [✘][✘][✘][✘][✘][✘][✘][✘][✘][✘]\n"
                        + "  [✘][✘][✘][✘][✘][✘][✘][✘][✘][✘]\n"
                        + "  [✘][✘][✘][✘][✘][✘][✘][✘][✘][✘]\n"
                        + "\n"
                        + "Tier 1 Seats: " + theatre.getTierOneSeats() + "\n"
                        + "Tier 2 Seats: " + theatre.getTierTwoSeats() + "\n"
                        + "Tier 3 Seats: " + theatre.getTierThreeSeats() + "\n";
        assertEquals(expected, theatre.getSeatingArrangement());
    }

    @Test
    void testSellSeats() {
        theatre.sellSeats("A1", "A2"); // test sell seat function
        String expected = "                |STAGE|           \n"
                + "  [✓][✓][✘][✘][✘][✘][✘][✘][✘][✘]\n"
                + "  [✘][✘][✘][✘][✘][✘][✘][✘][✘][✘]\n"
                + "  [✘][✘][✘][✘][✘][✘][✘][✘][✘][✘]\n"
                + "  [✘][✘][✘][✘][✘][✘][✘][✘][✘][✘]\n"
                + "  [✘][✘][✘][✘][✘][✘][✘][✘][✘][✘]\n"
                + "  [✘][✘][✘][✘][✘][✘][✘][✘][✘][✘]\n"
                + "\n"
                + "Tier 1 Seats: " + theatre.getTierOneSeats() + "\n"
                + "Tier 2 Seats: " + theatre.getTierTwoSeats() + "\n"
                + "Tier 3 Seats: " + theatre.getTierThreeSeats() + "\n";
        assertEquals(expected, theatre.getSeatingArrangement());
        assertEquals(60, theatre.getProfit());
        Seat[][] seats = theatre.getSeats();
        assertTrue(seats[0][0].isSold());
        assertTrue(seats[0][1].isSold());

        theatre.sellSeats("A0"); // test none existent seat
        assertEquals(expected, theatre.getSeatingArrangement());
        assertEquals(60, theatre.getProfit());
    }

    @Test
    void testReassignSeat() {
        theatre.sellSeats("A1");
        theatre.reassignSeat("A1", "C2"); // successful reassignment of seats.
        String expected = "                |STAGE|           \n"
                + "  [✘][✘][✘][✘][✘][✘][✘][✘][✘][✘]\n"
                + "  [✘][✘][✘][✘][✘][✘][✘][✘][✘][✘]\n"
                + "  [✘][✓][✘][✘][✘][✘][✘][✘][✘][✘]\n"
                + "  [✘][✘][✘][✘][✘][✘][✘][✘][✘][✘]\n"
                + "  [✘][✘][✘][✘][✘][✘][✘][✘][✘][✘]\n"
                + "  [✘][✘][✘][✘][✘][✘][✘][✘][✘][✘]\n"
                + "\n"
                + "Tier 1 Seats: " + theatre.getTierOneSeats() + "\n"
                + "Tier 2 Seats: " + theatre.getTierTwoSeats() + "\n"
                + "Tier 3 Seats: " + theatre.getTierThreeSeats() + "\n";
        assertEquals(expected, theatre.getSeatingArrangement());
        theatre.reassignSeat("A1", "A0"); // unsuccessful reassignment of seats.
        assertEquals(expected, theatre.getSeatingArrangement());
    }

    @Test
    void testRemoveSeat() {
        theatre.sellSeats("A1", "A2", "A3", "F3");
        assertEquals(110, theatre.getProfit());
        theatre.removeSeat("A3");
        String expected = "                |STAGE|           \n"
                + "  [✓][✓][✘][✘][✘][✘][✘][✘][✘][✘]\n"
                + "  [✘][✘][✘][✘][✘][✘][✘][✘][✘][✘]\n"
                + "  [✘][✘][✘][✘][✘][✘][✘][✘][✘][✘]\n"
                + "  [✘][✘][✘][✘][✘][✘][✘][✘][✘][✘]\n"
                + "  [✘][✘][✘][✘][✘][✘][✘][✘][✘][✘]\n"
                + "  [✘][✘][✓][✘][✘][✘][✘][✘][✘][✘]\n"
                + "\n"
                + "Tier 1 Seats: " + theatre.getTierOneSeats() + "\n"
                + "Tier 2 Seats: " + theatre.getTierTwoSeats() + "\n"
                + "Tier 3 Seats: " + theatre.getTierThreeSeats() + "\n";
        assertEquals(80, theatre.getProfit());
        assertEquals(expected, theatre.getSeatingArrangement());
        assertEquals(20, theatre.removeSeat("F3"));
        assertEquals(0, theatre.removeSeat("F3"));
        theatre.removeSeat("A0");
        assertEquals(60, theatre.getProfit());
    }

    @Test
    void testWriteToFile() {
        String expected = "Test Show | 0.000000 | 20.000000\n";
        assertEquals(expected, theatre.writeToFile());
    }
}