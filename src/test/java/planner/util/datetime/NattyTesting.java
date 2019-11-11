//@@author namiwa

package planner.util.datetime;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import planner.logic.exceptions.legacy.ModInvalidTimeException;

public class NattyTesting {

    private NattyWrapper natty = new NattyWrapper();

    @DisplayName("Testing Date return")
    @Test
    public void nattyDateTest() {
        try {
            Date first = natty.runParser("today");
            TimeUnit.MILLISECONDS.sleep(1);
            Date second = new Date(System.currentTimeMillis());
            assertTrue(second.after(first));
        } catch (ModInvalidTimeException e) {
            System.out.println(e.getMessage());
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    @DisplayName("Testing Local Date Time")
    @Test
    public void nattyLocalDateTimeTest() {
        try {
            LocalDateTime before = natty.dateToLocalDateTime("today");
            TimeUnit.MILLISECONDS.sleep(1);
            LocalDateTime after = LocalDateTime.now();
            assertTrue(before.isBefore(after));
        } catch (ModInvalidTimeException e) {
            System.out.println(e.getMessage());
        }  catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }


}
