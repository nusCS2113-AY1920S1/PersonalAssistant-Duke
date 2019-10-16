package duke;

import org.junit.jupiter.api.Test;
import duke.task.duketasks.Event;
import duke.task.duketasks.Task;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventTest {

    @Test
    public void shouldTestEventToSaveString() {
        // assert statements
        assertEquals("E | - | project meeting | Aug 6th 2-4pm",
                new Event("project meeting", "Aug 6th 2-4pm").toSaveString());
    }

    @Test
    public void shouldTestEventToString() {
        // assert statements
        assertEquals("[E][-] project meeting (at: Aug 6th 2-4pm)",
                new Event("project meeting", "Aug 6th 2-4pm").toString());
    }

    @Test
    public void shouldTestEventGetStatusIcon() {
        Task event = new Event("project meeting", "Aug 6th 2-4pm");
        event.markAsDone();
        // assert statements
        assertEquals("+", event.getStatusIcon());
    }
}
