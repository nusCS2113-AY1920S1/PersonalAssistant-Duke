package duchess.model.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventTest {
    private final Event event = new Event("2019-12-27T12:12", "2019-12-27T12:14");

    @Test
    public void constructor() {
        assertEquals(event.getStart(), "2019-12-27T12:12");
        assertEquals(event.getEnd(), "2019-12-27T12:14");
    }

    @Test
    public void toString_formatsCorrectly() {
        event.setDescription("Test");
        assertEquals(event.toString(),
                "[E][X] Test (at: 27/12/2019 1212 to 27/12/2019 1214)");
    }

    @Test
    public void snooze_snoozes() {
        event.snooze();
        assertEquals(event.getStart(), "2020-01-03T12:12");
    }
}
