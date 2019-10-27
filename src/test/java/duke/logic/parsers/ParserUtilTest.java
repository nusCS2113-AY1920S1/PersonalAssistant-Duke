package duke.logic.parsers;

import duke.commons.exceptions.DukeException;
import duke.model.planning.Todo;
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
        assertEquals(ParserUtil.getIntegerIndexInList(0, 1, "done 1".strip().split(" ", 2)[1]), 0);
    }
}
