import gazeeebo.commands.tasks.CalendarView;
import gazeeebo.tasks.Deadline;
import gazeeebo.tasks.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class CalendarViewTest {

    private ByteArrayOutputStream output = new ByteArrayOutputStream();
    private PrintStream mine = new PrintStream(output);
    private PrintStream original = System.out;

    @BeforeEach
    void setupStream() {
        System.setOut(mine);
    }

    @AfterEach
    void restoreStream() {
        System.out.flush();
        System.setOut(original);
    }
    @Test
    public void LeapYearTest() {
        CalendarView calendarView = new CalendarView();
        assertTrue(calendarView.isLeapYear(2020));
        assertFalse(calendarView.isLeapYear(2019));
    }
    @Test
    public void StartDayTest() {
        CalendarView calendarView = new CalendarView();
        assertEquals(3, calendarView.StartDay(10,23,2019));
    }
    @Test
    public void MonthlyViewTest() {
        CalendarView calendarView = new CalendarView();
        ArrayList<Task> list = new ArrayList<>();
        Deadline deadline = new Deadline("test","2019-10-12 12:12:12");
        list.add(deadline);
        calendarView.MonthlyView(list);
        assertEquals("          October 2019\r\n" +
                "  S    M    Tu   W    Th   F    S\r\n" +
                "             1    2    3    4    5 \r\n" +
                "   6    7    8    9   10   11  12* \r\n" +
                "  13   14   15   16   17   18   19 \r\n" +
                "  20   21   22  |23|  24   25   26 \r\n" +
                "  27   28   29   30   31 \r\n", output.toString());
    }
}