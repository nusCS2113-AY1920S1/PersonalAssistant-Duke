package duke.logic.parsers;

import duke.commons.exceptions.DukeException;
import duke.model.Event;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class ParserStorageUtilTest {

    @Test
    void createTaskFromDate() throws DukeException {
        Event event = new Event("NTU", LocalDateTime.now(), LocalDateTime.now());
        Event check = ParserStorageUtil.createTaskFromStorage(ParserStorageUtil.toStorageString(event));
        assertFalse(check.isDone());
        assertEquals(check.getDescription(), "NTU");
    }
}
