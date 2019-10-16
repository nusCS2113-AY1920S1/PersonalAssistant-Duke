import duke.task.After;
import duke.task.TaskList;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Represents a test for After class.
 */
public class AfterTest {
    @Test
    public void afterTest() throws ParseException {
        String cross = "✗";
        System.out.println("Start afterTest");

        /**
         * First test is for after a specific task
         */
        String info = "return book";
        String endDate = "16/08/2019 1600";
        Date date = new SimpleDateFormat("dd/MM/yyyy HHmm").parse(endDate);
        String expectedDate = TaskList.dateToStringFormat(date);
        After after = new After(info, false, endDate);
        assertEquals("[A][" + cross + "] return book (after: " + expectedDate + ")", after.toString());

        /**
         * Second test is for after a specific date
         */
        String info2 = "buy bread";
        After after2 = new After(info2, false, endDate);
        assertEquals("[A][" + cross + "] buy bread (after: " + expectedDate + ")",after2.toString());

        System.out.println("Passed afterTest");
    }
}
