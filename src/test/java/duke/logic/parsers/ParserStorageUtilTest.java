package duke.logic.parsers;

import duke.commons.exceptions.DukeException;
import duke.model.planning.Todo;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class ParserStorageUtilTest {

    @Test
    void createTaskFromDate() throws DukeException {
        LocalDateTime startDate = LocalDateTime.of(2018, 8, 8, 8, 8);
        Todo d = new Todo("Visit Rome");
        Todo t = (Todo) ParserStorageUtil.createTaskFromStorage(ParserStorageUtil.toStorageString(d));
        assertFalse(t.isDone());
        assertEquals(t.getDescription(), "Visit Rome");
    }
}
