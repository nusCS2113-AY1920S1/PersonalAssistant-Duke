package compal.logic.commands;

import compal.commons.Compal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RecurTaskCommandTest {
    private RecurTaskCommand recurTask;
    private Compal compal;

    @BeforeEach
    public void setUp() {
        compal = new Compal();
        recurTask = new RecurTaskCommand(compal);
    }

    @Test
    public void iniTest() {
        assertEquals(compal, recurTask.compal);
        assertEquals(compal.tasklist,recurTask.compal.tasklist);
    }

    @Test
    public void getRepTest() throws Compal.DukeException {
        String command = "/rep 5";
        assertEquals(5, recurTask.getRep(command));
    }

    @Test
    public void getEndDateTest() throws Compal.DukeException {
        String time = "15/10/2019";
        String command = "/edate 15/10/2019";
        assertEquals(time, recurTask.getEndDate(command));
    }

    @Test
    public void getFreqTest() {
        String command = "/freq 6";
        assertEquals(6, recurTask.getFreq(command));
        command = "trash";
        assertEquals(7, recurTask.getFreq(command));
        command = "/freq";
        assertEquals(7, recurTask.getFreq(command));
    }

    @Test
    public void incrementDateTest() {
        String date = "14/10/2019";
        String newDate = "21/10/2019";
        assertEquals(newDate, recurTask.incrementDate(date, 7));
    }
}
