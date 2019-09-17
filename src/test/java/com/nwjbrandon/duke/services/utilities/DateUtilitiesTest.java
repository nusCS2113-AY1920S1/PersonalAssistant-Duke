package com.nwjbrandon.duke.services.utilities;

import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DateUtilitiesTest {
    @Test
    void testSameDay() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String dateInString1 = "22-01-2015 10:20:56";
        String dateInString2 = "22-01-2015 12:20:56";

        Date date1 = sdf.parse(dateInString1);
        Date date2 = sdf.parse(dateInString2);

        assertTrue(DateUtilties.isSameDay(date1, date2));
    }

    @Test
    void testDifferentDay() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String dateInString1 = "22-01-2015 10:20:56";
        String dateInString2 = "22-02-2015 10:20:56";

        Date date1 = sdf.parse(dateInString1);
        Date date2 = sdf.parse(dateInString2);

        assertFalse(DateUtilties.isSameDay(date1, date2));
    }
}

