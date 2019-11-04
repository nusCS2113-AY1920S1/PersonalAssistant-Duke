package duke.logic.parsers.storageParsers;

import duke.commons.exceptions.ApiException;
import duke.commons.exceptions.ParseException;
import duke.model.Event;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class EventStorageParserTest {

    @Test
    void createEventFromStorage() throws ApiException, ParseException {
        Event event = new Event("NTU", LocalDateTime.now(), LocalDateTime.now());
        Event check = EventStorageParser.createEventFromStorage(EventStorageParser.toStorageString(event));
        assertFalse(check.isDone());
        assertEquals(check.getDescription(), "NTU");
    }
}