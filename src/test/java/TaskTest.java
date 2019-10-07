/**import controlpanel.DukeException;
import controlpanel.Ui;
import org.junit.jupiter.api.Test;

import tasks.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskTest {
    private Ui ui;
    private TaskList tasks;
    private Storage storage;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Test
    public void testMarkAsDone() {
        String description = "Get lit tonight";
        Task test = new Task(description);
        test.markAsDone();
        assertEquals(true, test.getStatus());
    }

    @Test
    public void testGetStatusIcon() {
        String description = "Get lit tonight";
        Task test = new Task(description);
        assertEquals("[\u2718]", test.getStatusIcon());
        test.markAsDone();
        assertEquals("[\u2713]", test.getStatusIcon());
    }

    @Test
    public void testType() throws ParseException{
        SimpleDateFormat simpleDateFormat  = new SimpleDateFormat("d/M/yyyy HHmm");
        Date startDate = simpleDateFormat.parse("3/2/2019 1300");
        Date endDate = simpleDateFormat.parse("6/2/2019 1300");
        String strStartDate = simpleDateFormat.format(startDate);
        String strEndDate = simpleDateFormat.format(endDate);
        String needs = "2 hours";
        String description = "Get lit tonight";

        assertEquals("T",
                new ToDos(description).getType());
        assertEquals("D",
                new Deadline(description, startDate).getType());
        assertEquals("E",
                new Events(description, startDate, endDate).getType());
        assertEquals("P",
                new Periods(description, strStartDate, strEndDate).getType());
        assertEquals("F",
                new FixedDuration(description, needs).getType());
    }

    @Test
    public void testGetByAtFromToNeeds()throws ParseException {
        SimpleDateFormat simpleDateFormat  = new SimpleDateFormat("d/M/yyyy HHmm");
        Date startDate = simpleDateFormat.parse("3/2/2019 1300");
        Date endDate = simpleDateFormat.parse("6/2/2019 1300");
        String from = simpleDateFormat.format(startDate);
        String to = simpleDateFormat.format(endDate);
        String needs = "2 hours";

        assertEquals("3/2/2019 1300",
                new Deadline("do assignments", startDate).getBy());

        assertEquals("6/2/2019 1300",
                new Events("marquee", startDate,endDate).getEndAt());

        assertEquals("3/2/2019 1300",
                new Events("marquee", startDate,endDate).getStartAt());

        assertEquals("3/2/2019 1300",
                new Periods("zouk", from,to).getFrom());

        assertEquals("6/2/2019 1300",
                new Periods("zouk", from, to).getTo());

        assertEquals("2 hours",
                new FixedDuration("zoukout", needs).getNeeds());
    }

    @Test
    public void testToString() throws DukeException, ParseException {
        String description = "Get lit tonight";
        SimpleDateFormat simpleDateFormat  = new SimpleDateFormat("d/M/yyyy HHmm");
        Date startDate = simpleDateFormat.parse("3/2/2019 1300");
        Date endDate = simpleDateFormat.parse("6/2/2019 1300");
        String strStartDate = simpleDateFormat.format(startDate);
        String strEndDate = simpleDateFormat.format(endDate);
        String needs = "2 hours";

        assertEquals("[T][\u2718] Get lit tonight",
                new ToDos(description).toString());

        assertEquals("[D][\u2718] Get lit tonight(by: " + strStartDate + ")",
                new Deadline(description, startDate).toString());

        assertEquals("[E][\u2718] Get lit tonight(at: " + strStartDate + " to " + strEndDate + ")",
                new Events(description, startDate, endDate).toString());

        assertEquals("[P][\u2718] Get lit tonight(from: " + strStartDate + " to " + strEndDate + ")",
                new Periods(description, strStartDate, strEndDate).toString());

        assertEquals("[F][\u2718] Get lit tonight(needs: 2 hours)",
                new FixedDuration(description, needs).toString());
    }
}
 */