package parser;

import entertainment.pro.logic.parsers.TimeParser;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class TimeParserTest {
    @Test
    public void testDateConversion() {
       String str = TimeParser.convertDateToLine(TimeParser.convertStringToDate("20/09/2019"));
       String str2 = TimeParser.convertDateToLine(TimeParser.convertStringToDate("20/09/2019 1800"));
        String str3 = TimeParser.convertDateToLine(TimeParser.convertStringToDate("20/09/2019 1800"));
       String str4 = TimeParser.convertDateToLine(TimeParser.convertStringToDate("20-SEP-2019 1800"));
       assertEquals("20th of September 2019, 12:00 AM", str);
       assertEquals("20th of September 2019, 06:00 PM", str2);
       assertEquals("20th of September 2019, 06:00 PM", str3);
       assertEquals("20th of September 2019, 06:00 PM", str4);
   }
}