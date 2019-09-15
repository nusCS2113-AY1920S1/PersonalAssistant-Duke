package parser;

import org.junit.jupiter.api.Test;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TimeParserTest {
    @Test
    public void testDateConversion() {
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

    }
}
