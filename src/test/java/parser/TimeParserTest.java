package parser;

import duke.TimeParser;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class TimeParserTest {
    @Test
    public void testDateConversion() {
        String str = TimeParser.convertDateToLine(TimeParser.convertStringToDate("17/09/2019"));
        String str2 = TimeParser.convertDateToLine(TimeParser.convertStringToDate("17/09/2019 1800"));
        String str3 = TimeParser.convertDateToLine(TimeParser.convertStringToDate("17-SEP-2019 1800"));
        assertEquals("17th of September 2019, 12:00 AM", str);
        assertEquals("17th of September 2019, 06:00 PM", str2);
        assertEquals("17th of September 2019, 06:00 PM", str3);
    }
}