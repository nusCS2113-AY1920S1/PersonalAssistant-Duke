package duke.parsers;

import duke.commons.DukeDateTimeParseException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class ParserTimeTest {

    @Test
    void parseStringToDate() {
        LocalDateTime date = LocalDateTime.of(2019, 9, 9, 9, 9);
        try {
            assertEquals(date, ParserTime.parseStringToDate("09/09/2019 0909"));
        } catch (DukeDateTimeParseException e) {
            fail();
        }
    }
}