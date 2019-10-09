package duke.parsers;

import duke.data.tasks.DoWithin;
import org.junit.jupiter.api.Test;
import duke.data.tasks.Deadline;
import duke.data.tasks.Event;
import duke.data.tasks.Todo;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ParserUtilTest {

    @Test
    void createWithin() throws Exception {
        assertTrue(ParserUtil.createWithin("within jogging between 1200 and 1300") instanceof DoWithin);
    }

    @Test
    void createTodo() throws Exception {
        assertTrue(ParserUtil.createTodo("Homework") instanceof Todo);
    }

    @Test
    void createDeadline() throws Exception {
        assertTrue(ParserUtil.createDeadline("deadline homework /by tomorrow") instanceof Deadline);
    }

    @Test
    void createEvent() {
        try {
            assertTrue(ParserUtil.createEvent("event exam /at classroom") instanceof Event);
        } catch (Exception e) {
            System.out.println("Fail");
        }
    }

    @Test
    void getIndex() throws Exception {
        assertEquals(ParserUtil.getIndex("done 1"), 0);
    }
}
