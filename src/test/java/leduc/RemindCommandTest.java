package leduc;

import leduc.command.RemindCommand;
import leduc.exception.DukeException;
import leduc.exception.FileException;
import leduc.exception.MeaninglessException;
import leduc.storage.Storage;
import leduc.task.TaskList;
import leduc.ui.Ui;
import leduc.ui.UiEn;
import leduc.task.Task;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class RemindCommandTest {
    private static Ui ui;
    private static Storage storage;
    private static List<Task> tasklist;
    private static TaskList tasks;
    private static final PrintStream originalOut = System.out;
    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    /**
     * Represents the setup of RemindCommandTest.
     */
    @BeforeAll
    static void setUp() {
        ui = new UiEn();
        System.setOut(new PrintStream(outContent));

        try {
            storage = new Storage(System.getProperty("user.dir")+ "/src/test/java/testFile/testFile.txt", System.getProperty("user.dir")+ "/src/test/java/testFile/configTest.txt",System.getProperty("user.dir")+ "/src/test/java/testFile/welcome.txt");
            tasklist= storage.load();
        } catch (FileException e) {
            e.printStackTrace();
        } catch (MeaninglessException e) {
            e.printStackTrace();
        }
        tasks = new TaskList(tasklist);
    }

    @Test
    public void RemindCommandTest() {
        RemindCommand remind = new RemindCommand("");
        remind.execute(tasks, ui,storage);
        assertTrue(outContent.toString().contains("1. [H][X] math assignment 1 by: 07/11/2019 23:59 [Priority: 5]\n" +
                "\t 4. [H][X] Prepare interview  by: 09/11/2019 10:30 [Priority: 4]\n" +
                "\t 5. [E][X] Sport  at: 10/11/2019 11:00 - 10/11/2019 17:00 [Priority: 5]"));
    }
    @Test
    public void RemindCommandTest2(){
        RemindCommand remind = new RemindCommand("");
        tasks = new TaskList(new ArrayList<>());
        remind.execute(tasks, ui,storage);
        assertTrue(outContent.toString().contains("There is any task"));
    }

}