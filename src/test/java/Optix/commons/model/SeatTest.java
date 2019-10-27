package optix.commons.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SeatTest {
    private Seat seat;

    @BeforeEach
    void init() {
        seat = new Seat("1");
    }

    @Test
    void testGetSeat() {
        assertEquals("[✘]", seat.getSeat());
        seat.setBooked(true);
        assertEquals("[✓]", seat.getSeat());
    }


    @Test
    void testGetSeatPrice() {
        assertEquals(30, seat.getSeatPrice(30));
        seat.setSeatTier("2");
        assertEquals(24, seat.getSeatPrice(20));
        seat.setSeatTier("3");
        assertEquals(30, seat.getSeatPrice(20));
    }
}