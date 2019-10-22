package duke.logic.parsers;

import duke.commons.exceptions.DukeException;
import duke.model.events.Todo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ParserUtilTest {

    @Test
    void createTodo() throws Exception {
        assertTrue(ParserUtil.createTodo("todo Homework") instanceof Todo);
    }

    @Test
    void getIndex() throws Exception {
        assertEquals(ParserUtil.getIndex("done 1"), 0);
    }

    @Test
    void getSafeIndex() throws DukeException {
        assertEquals(ParserUtil.getSafeIndex("done 5 10 10 10"), 4);
    }
}
