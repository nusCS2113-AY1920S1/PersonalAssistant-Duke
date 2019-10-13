package duke.logic.parsers;

import org.junit.jupiter.api.Test;
import duke.data.tasks.Todo;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ParserUtilTest {

    @Test
    void createTodo() throws Exception {
        assertTrue(ParserUtil.createTodo("Homework") instanceof Todo);
    }

    @Test
    void getIndex() throws Exception {
        assertEquals(ParserUtil.getIndex("done 1"), 0);
    }
}
