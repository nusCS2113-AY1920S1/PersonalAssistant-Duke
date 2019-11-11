package leduc;

import leduc.command.SetWelcomeCommand;
import leduc.exception.DukeException;
import leduc.exception.FileException;
import leduc.exception.MeaninglessException;
import leduc.exception.NonExistentDateException;
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

public class SetWelcomeCommandTest {
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
        } catch (NonExistentDateException e) {
            e.printStackTrace();
        }
        tasks = new TaskList(tasklist);
    }

    @Test
    void SetWelcomeCommandTest(){
        SetWelcomeCommand welcome = new SetWelcomeCommand("setwelcome Test Message");
        try{
            welcome.execute(tasks, ui, storage);
        }
        catch(FileException e){
            e.printStackTrace();
        }
        assertTrue(outContent.toString().contains("Test Message"));

    }

    @Test
    void SetWelcomeCommandTest2(){
        SetWelcomeCommand welcome = new SetWelcomeCommand("Test Message");
        try{
            welcome.execute(tasks, ui, storage);
        }
        catch(FileException e){
            e.printStackTrace();
        }
        assertTrue(outContent.toString().contains("Message"));
    }
    @Test
    void SetWelcomeCommandTest3(){
        SetWelcomeCommand welcome = new SetWelcomeCommand("3");
        try{
            welcome.execute(tasks, ui, storage);
        }
        catch(FileException e){
            e.printStackTrace();
        }
        assertTrue(outContent.toString().contains(""));
    }
    @Test
    void SetWelcomeCommandTest4(){
        SetWelcomeCommand welcome = new SetWelcomeCommand("setwelcome 3");
        try{
            welcome.execute(tasks, ui, storage);
        }
        catch(FileException e){
            e.printStackTrace();
        }
        assertTrue(outContent.toString().contains("3"));
    }
}
