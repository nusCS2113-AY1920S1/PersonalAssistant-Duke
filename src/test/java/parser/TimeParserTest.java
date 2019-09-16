package parser;

import org.junit.jupiter.api.Test;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TimeParserTest {
    @Test
    public void testDateConversion() {
<<<<<<< HEAD
        Date date = TimeParser.convertStringToDate("17/09/2019 1800");
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HHmm");
        Date checkDate = null;
        try {
            checkDate = formatter.parse("17/09/2019 1800");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date date1 = TimeParser.convertStringToDate("17-SEP-2019 1800");
        SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy HHmm");
        Date checkDate1 = null;
        try {
            checkDate1 = formatter1.parse("17-09-2019 1800");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assertEquals(checkDate, date);
        assertEquals(checkDate1, date1);

=======
        String str = TimeParser.convertDateToLine(TimeParser.convertStringToDate("17/09/2019"));
        String str2 = TimeParser.convertDateToLine(TimeParser.convertStringToDate("17/09/2019 1800"));
        String str3 = TimeParser.convertDateToLine(TimeParser.convertStringToDate("17-SEP-2019 1800"));
        assertEquals("17th of September 2019, 12:00 AM", str);
        assertEquals("17th of September 2019, 06:00 PM", str2);
        assertEquals("17th of September 2019, 06:00 PM", str3);
>>>>>>> upstream/master
    }
}
