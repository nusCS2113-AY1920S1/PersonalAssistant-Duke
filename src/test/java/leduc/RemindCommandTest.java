package leduc;

import leduc.command.RemindCommand;
import leduc.exception.DukeException;
import leduc.exception.FileException;
import leduc.exception.MeaninglessException;
import leduc.exception.NonExistentDateException;
import leduc.storage.Storage;
import leduc.task.TaskList;
import leduc.task.HomeworkTask;
import leduc.Date;
import leduc.task.TodoTask;
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
        tasks.add(new TodoTask("math assignment"));
        tasks.add(new TodoTask("Prepare Interview"));
        tasks.add(new TodoTask("Sports"));
        try {
            Date d = new Date("11/03/2010 01:01");
            tasks.add(new HomeworkTask("science homework", d));
        }
        catch (NonExistentDateException e){
            e.printStackTrace();
        }


        remind.execute(tasks, ui,storage);
        assertTrue(outContent.toString().contains("1. [H][X] science homework by: 11/03/2010 01:01 [Priority: 5]\n" +
                "\t 2. [T][X] math assignment [Priority: 5]\n" +
                "\t 3. [T][X] Prepare Interview [Priority: 5]"));
    }
    @Test
    public void RemindCommandTest2(){
        RemindCommand remind = new RemindCommand("");
        tasks = new TaskList(new ArrayList<>());
        remind.execute(tasks, ui,storage);
        assertTrue(outContent.toString().contains("There aren't any tasks!!"));
    }

}