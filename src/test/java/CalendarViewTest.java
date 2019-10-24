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
    @Test
    public void AnnualViewTest() {
        CalendarView calendarView = new CalendarView();
        ArrayList<Task> list = new ArrayList<>();
        Deadline deadline = new Deadline("test","2019-10-12 12:12:12");
        list.add(deadline);
        calendarView.AnnualView(list);
        assertEquals("          January 2019\r\n" +
                "  S    M    Tu   W    Th   F    S\r\n" +
                "             1    2    3    4    5 \r\n" +
                "   6    7    8    9   10   11   12 \r\n" +
                "  13   14   15   16   17   18   19 \r\n" +
                "  20   21   22   23   24   25   26 \r\n" +
                "  27   28   29   30   31 \r\n" +
                "\r\n" +
                "          February 2019\r\n" +
                "  S    M    Tu   W    Th   F    S\r\n" +
                "                            1    2 \r\n" +
                "   3    4    5    6    7    8    9 \r\n" +
                "  10   11   12   13   14   15   16 \r\n" +
                "  17   18   19   20   21   22   23 \r\n" +
                "  24   25   26   27   28 \r\n" +
                "          March 2019\r\n" +
                "  S    M    Tu   W    Th   F    S\r\n" +
                "                            1    2 \r\n" +
                "   3    4    5    6    7    8    9 \r\n" +
                "  10   11   12   13   14   15   16 \r\n" +
                "  17   18   19   20   21   22   23 \r\n" +
                "  24   25   26   27   28   29   30 \r\n" +
                "  31 \r\n" +
                "\r\n" +
                "          April 2019\r\n" +
                "  S    M    Tu   W    Th   F    S\r\n" +
                "        1    2    3    4    5    6 \r\n" +
                "   7    8    9   10   11   12   13 \r\n" +
                "  14   15   16   17   18   19   20 \r\n" +
                "  21   22   23   24   25   26   27 \r\n" +
                "  28   29   30 \r\n" +
                "          May 2019\r\n" +
                "  S    M    Tu   W    Th   F    S\r\n" +
                "                  1    2    3    4 \r\n" +
                "   5    6    7    8    9   10   11 \r\n" +
                "  12   13   14   15   16   17   18 \r\n" +
                "  19   20   21   22   23   24   25 \r\n" +
                "  26   27   28   29   30   31 \r\n" +
                "\r\n" +
                "          June 2019\r\n" +
                "  S    M    Tu   W    Th   F    S\r\n" +
                "                                 1 \r\n" +
                "   2    3    4    5    6    7    8 \r\n" +
                "   9   10   11   12   13   14   15 \r\n" +
                "  16   17   18   19   20   21   22 \r\n" +
                "  23   24   25   26   27   28   29 \r\n" +
                "  30 \r\n" +
                "          July 2019\r\n" +
                "  S    M    Tu   W    Th   F    S\r\n" +
                "        1    2    3    4    5    6 \r\n" +
                "   7    8    9   10   11   12   13 \r\n" +
                "  14   15   16   17   18   19   20 \r\n" +
                "  21   22   23   24   25   26   27 \r\n" +
                "  28   29   30   31 \r\n" +
                "\r\n" +
                "          August 2019\r\n" +
                "  S    M    Tu   W    Th   F    S\r\n" +
                "                       1    2    3 \r\n" +
                "   4    5    6    7    8    9   10 \r\n" +
                "  11   12   13   14   15   16   17 \r\n" +
                "  18   19   20   21   22   23   24 \r\n" +
                "  25   26   27   28   29   30   31 \r\n" +
                "\r\n" +
                "          September 2019\r\n" +
                "  S    M    Tu   W    Th   F    S\r\n" +
                "   1    2    3    4    5    6    7 \r\n" +
                "   8    9   10   11   12   13   14 \r\n" +
                "  15   16   17   18   19   20   21 \r\n" +
                "  22   23   24   25   26   27   28 \r\n" +
                "  29   30 \r\n" +
                "          October 2019\r\n" +
                "  S    M    Tu   W    Th   F    S\r\n" +
                "             1    2    3    4    5 \r\n" +
                "   6    7    8    9   10   11  12* \r\n" +
                "  13   14   15   16   17   18   19 \r\n" +
                "  20   21   22  |23|  24   25   26 \r\n" +
                "  27   28   29   30   31 \r\n" +
                "\r\n" +
                "          November 2019\r\n" +
                "  S    M    Tu   W    Th   F    S\r\n" +
                "                            1    2 \r\n" +
                "   3    4    5    6    7    8    9 \r\n" +
                "  10   11   12   13   14   15   16 \r\n" +
                "  17   18   19   20   21   22   23 \r\n" +
                "  24   25   26   27   28   29   30 \r\n" +
                "\r\n" +
                "          December 2019\r\n" +
                "  S    M    Tu   W    Th   F    S\r\n" +
                "   1    2    3    4    5    6    7 \r\n" +
                "   8    9   10   11   12   13   14 \r\n" +
                "  15   16   17   18   19   20   21 \r\n" +
                "  22   23   24   25   26   27   28 \r\n" +
                "  29   30   31 \r\n" +
                "\r\n", output.toString());
    }
}