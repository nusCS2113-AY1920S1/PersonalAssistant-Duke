import Commands.Command;
import Commands.RemindersCommand;
import Commands.ViewScheduleCommand;
import ControlPanel.DukeException;
import ControlPanel.Storage;
import ControlPanel.Ui;
import org.junit.jupiter.api.Test;

import Tasks.*;

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
    public void dummyTest(){
        assertEquals(2, 2);
    }

    @Test
    public void testgetByAt()throws ParseException {

        SimpleDateFormat simpleDateFormat  = new SimpleDateFormat("d/M/yyyy HHmm");
        Date startDate = simpleDateFormat.parse("3/2/2019 1300");
        Date endDate = simpleDateFormat.parse("6/2/2019 1300");
        assertEquals("3/2/2019 1300",

                new Deadline("do assignments", startDate).getBy());

        assertEquals("6/2/2019 1300",

                new Events("marquee", startDate,endDate).getEndAt());

        assertEquals("3/2/2019 1300",

                new Events("marquee", startDate,endDate).getStartAt());

    }



}
