package parser;

import entertainment.pro.logic.parsers.TimeParser;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class TimeParserTest {

    @Test
    public void testDateConversion() {
        String str = TimeParser.convertDateToLine(TimeParser.convertStringToDate("20/09/2019"));
        assertEquals("20th of September 2019, 12:00 AM", str);
        String str2 = TimeParser.convertDateToLine(TimeParser.convertStringToDate("20/09/2019 1800"));
        assertEquals("20th of September 2019, 06:00 PM", str2);
        String str3 = TimeParser.convertDateToLine(TimeParser.convertStringToDate("20/09/2019 1800"));
        assertEquals("20th of September 2019, 06:00 PM", str3);
        String str4 = TimeParser.convertDateToLine(TimeParser.convertStringToDate("20-SEP-2019 1800"));
        assertEquals("20th of September 2019, 06:00 PM", str4);
        String str5 = TimeParser.convertDateToLine(TimeParser.convertStringToDate("20/09/2019"));
        assertEquals("20th of September 2019, 12:00 AM", str5);
        String str6 = TimeParser.convertDateToLine(TimeParser.convertStringToDate("20/09/2019 18:00"));
        assertEquals("20th of September 2019, 06:00 PM", str6);
        String str7 = TimeParser.convertDateToLine(TimeParser.convertStringToDate("20/09/2019 18:00"));
        assertEquals("20th of September 2019, 06:00 PM", str7);
        String str8 = TimeParser.convertDateToLine(TimeParser.convertStringToDate("20-SEP-2019 18:00"));
        assertEquals("20th of September 2019, 06:00 PM", str8);
    }

}