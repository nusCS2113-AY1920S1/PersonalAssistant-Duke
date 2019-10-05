package duke.data.tasks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EventTest {

    @Test
    void testToString() {
        Event event = new Event("Exam", "Classroom");
        assertEquals(event.toString(), "[E][✘] Exam (at: Classroom)");
    }
}
