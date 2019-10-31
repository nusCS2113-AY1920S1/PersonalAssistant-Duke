package duke.logic.edits;

import duke.commons.exceptions.DukeDateTimeParseException;
import duke.commons.exceptions.DukeException;
import duke.commons.exceptions.EventSelectionOutOfBoundsException;
import duke.logic.parsers.ParserTimeUtil;
import duke.model.Event;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EditorTest {
    private static final int DESCRIPTION = 0;
    private static final int START_DATE = 1;
    private static final int END_DATE = 2;

    @Test
    void edit() throws DukeException {
        LocalDateTime startDate = ParserTimeUtil.parseStringToDate("12/12/12");
        LocalDateTime endDate = ParserTimeUtil.parseStringToDate("06/06/18");
        Event event = new Event("Pulau Ubin", startDate, endDate);
        assertEquals(event.getDescription(), "Pulau Ubin");
        Editor.edit("Geylang", event, DESCRIPTION);
        assertEquals(event.getDescription(), "Geylang");
        Editor.edit("Geylang", event, DESCRIPTION);
        assertEquals(event.getDescription(), "Geylang");
        assertThrows(ApiNullRequestException.class, () -> Editor.edit("gwhore", event, DESCRIPTION));
        assertEquals(event.getDescription(), "Geylang");
        assertNotEquals(event.getDescription(), "gwhore");
        assertThrows(DukeDateTimeParseException.class, () -> Editor.edit("non", event, START_DATE));
        assertThrows(DukeDateTimeParseException.class, () -> Editor.edit("non", event, END_DATE));
        LocalDateTime date = ParserTimeUtil.parseStringToDate("01/01/18");
        Editor.edit("01/01/18", event, START_DATE);
        assertEquals(event.getStartDate().toLocalDate(), date.toLocalDate());
        assertNotEquals(event.getStartDate(), startDate);
        Editor.edit("02/02/18", event, END_DATE);
        assertEquals(event.getEndDate().toLocalDate(), ParserTimeUtil.parseStringToDate("02/02/18").toLocalDate());
        assertNotEquals(event.getEndDate(), endDate);
        assertThrows(EventSelectionOutOfBoundsException.class, () -> Editor.edit("meow", event, -1));
        assertThrows(EventSelectionOutOfBoundsException.class, () -> Editor.edit("meow", event, 3));
    }
}
