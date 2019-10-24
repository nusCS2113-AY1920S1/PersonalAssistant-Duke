package duke.logic.parsers;

import duke.commons.exceptions.parser.DukeDateTimeParseException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ParserTimeUtilTest {

    @Test
    void parseStringToDate() throws DukeDateTimeParseException {
        LocalDateTime date = LocalDateTime.of(2019, 9, 9, 9, 9);
        assertEquals(date, ParserTimeUtil.parseStringToDate("09/09/2019 0909"));
    }
}
