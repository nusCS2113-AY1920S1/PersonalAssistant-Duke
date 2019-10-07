/**import commands.AddCommand;
import commands.Command;
import controlpanel.DukeException;
import controlpanel.Ui;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import tasks.Events;
import tasks.TaskList;

import java.text.SimpleDateFormat;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;


public class DetectAnomaliesTest {
    private TaskList tasks;
    private Storage storage;
    private Ui ui;
    private SimpleDateFormat simpleDateFormat;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    public DetectAnomaliesTest() {
        Path currentDir = Paths.get("data/tasks.txt");
        String filePath = currentDir.toAbsolutePath().toString();
        storage = new Storage(filePath);
        ui = new Ui();
        tasks = new TaskList(storage.load());
        simpleDateFormat = new SimpleDateFormat("d/M/yyyy HHmm");
    }

    @Test
    public void testInvalidDuration() {
        System.setOut(originalOut);
        Command addEvent = new AddCommand("event", "project meeting /at 1/1/2000 0000 to 1/1/1999 0000");
        Assertions.assertThrows(DukeException.class, () -> addEvent.execute(tasks, ui, storage));
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void testScheduleClashes() throws ParseException {
        System.setOut(originalOut);
        Events e = new Events("important thing", simpleDateFormat.parse("1/1/2008 1900"), simpleDateFormat.parse("1/1/2008 2100"));
        tasks.addTask(e);
        Command addClashingEvent1 = new AddCommand("event", "party /at 1/1/2008 1910 to 1/1/2008 2110");
        Assertions.assertThrows(DukeException.class, () -> addClashingEvent1.execute(tasks, ui, storage));
        Command addClashingEvent2 = new AddCommand("event", "study session /at 1/1/2008 1830 to 1/1/2008 2030");
        Assertions.assertThrows(DukeException.class, () -> addClashingEvent2.execute(tasks, ui, storage));
        Command addClashingEvent3 = new AddCommand("event", "duke meeting /at 1/1/2008 1850 to 1/1/2008 2105");
        Assertions.assertThrows(DukeException.class, () -> addClashingEvent3.execute(tasks, ui, storage));
        Command addClashingEvent4 = new AddCommand("event", "phone my mom /at 1/1/2008 1500 to 1/1/2008 1945");
        Assertions.assertThrows(DukeException.class, () -> addClashingEvent4.execute(tasks, ui, storage));
        tasks.removeTask(tasks.lengthOfList() - 1);
        System.setOut(new PrintStream(outContent));
    }
}
*/