import Storage.Storage;
import Tasks.Event;
import Exception.DukeException;
import Tasks.Task;
import UI.Ui;
import commands.EventCommand;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class EventCommandTest {
    //creating a stream to hold the output
    private ByteArrayOutputStream output = new ByteArrayOutputStream();
    private PrintStream mine = new PrintStream(output);
    //saving the original System.out
    private PrintStream original = System.out;

    @BeforeEach
    void setupStream() {
        //tell java to print to my own stream
        System.setOut(mine);
    }

    @AfterEach
    void restoreStream(){
        System.out.flush();
        System.setOut(original);
    }

    @Test
    void execute_scheduleAnomalies() throws ParseException, IOException, DukeException {
        Ui ui = new Ui();
        Storage storage = new Storage();
        ui.FullCommand = "event sleep/at 2019-12-02 12:02:12";
        EventCommand ec = new EventCommand();
        ArrayList<Task> tasks = new ArrayList<Task>();
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Event e = new Event("eat", fmt.parse("2019-12-02 12:02:12"));
        tasks.add(e);
        ec.execute(tasks, ui, storage);
        assertEquals("The following event(s) clash with your current event:\r\n" +
                "1.[E][\u2718]eat(at:Mon Dec 02 12:02:12 SGT 2019)\r\n\r\n" +
                "Got it. I've added this task:\r\n" +
                "[E][\u2718]sleep(at:Mon Dec 02 12:02:12 SGT 2019)\r\n" +
                "Now you have 2 tasks in the list.\r\n", output.toString());
    }
}