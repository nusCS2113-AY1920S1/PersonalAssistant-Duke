package duke.tasks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventTest {

    @Test
    public void testToString(){
        assertEquals("[E][x] Read book (at: COM1)", new Event("Read book", "COM1").toString());
    }

}
