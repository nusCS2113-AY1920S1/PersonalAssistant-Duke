package duke.parsers;

import duke.tasks.DoWithin;
import org.junit.jupiter.api.Test;
import duke.tasks.Deadline;
import duke.tasks.Event;
import duke.tasks.Todo;
import duke.tasks.DoWithin;

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
    void createWithin() throws Exception {
        assertTrue(ParserUtil.createWithin("within jogging between 1200 and 1300") instanceof DoWithin);
    }

    @Test
    void getIndex() throws Exception {
        assertEquals(ParserUtil.getIndex("done 1"), 0);
    }
}
