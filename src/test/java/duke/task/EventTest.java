package duke.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EventTest {
    Event event = new Event("Test String", "Test date");

    @Test
    void testToString1() {
        assertEquals(event.toString(),"[E][\u2718] Test String (at: Test date)\n" );
    }

    @Test
    void testGetSymbol() {
        assertEquals(event.getSymbol(), "[E]");
    }

    @Test
    void testWriteToFile() {
        assertEquals(event.writeToFile(), "E | 0 | Test String | Test date");
    }
}