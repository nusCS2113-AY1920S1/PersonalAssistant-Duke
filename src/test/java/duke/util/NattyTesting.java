package duke.util;

import duke.exceptions.ModInvalidTimeException;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class NattyTesting {

    private NattyWrapper natty = new NattyWrapper();

    @Test
    public void nattyDateTest() {
        try {
            Date first = natty.runParser("now");
            Date second = Calendar.getInstance().getTime();
            assertTrue(second.after(first));
        } catch (ModInvalidTimeException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void nattyLocalDateTimeTest() {
        try {
            LocalDateTime before = natty.dateToLocalDateTime("today");
            LocalDateTime after = LocalDateTime.now();
            assertTrue(before.isBefore(after));
        } catch (ModInvalidTimeException e) {
            System.out.println(e.getMessage());
        }
    }


}
