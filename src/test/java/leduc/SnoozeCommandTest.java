package leduc;

import leduc.command.SnoozeCommand;
import leduc.exception.*;
import leduc.storage.Storage;
import leduc.task.*;
import leduc.ui.Ui;
import leduc.ui.UiEn;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Represents a JUnit test class for the SnoozeCommand.
 */
public class SnoozeCommandTest {

    private static Ui ui;
    private static Storage storage;
    private static TaskList tasks;

    /**
     * Represents the before of SnoozeCommandExecuteTest.
     */
    @BeforeAll
    public static void beforeSnoozeCommandExecuteTest(){
        ui = new UiEn();
        try {
            storage = new Storage(System.getProperty("user.dir")+ "/src/test/java/testFile/testFile.txt", System.getProperty("user.dir")+ "/src/test/java/testFile/configTest.txt",System.getProperty("user.dir")+ "/src/test/java/testFile/welcome.txt");
        } catch (FileException e) {
            e.printStackTrace();
        } catch (MeaninglessException e) {
            e.printStackTrace();
        }
        tasks = new TaskList(new ArrayList<>());

        LocalDateTime d1 = null;
        LocalDateTime d2 = null;
        Date date1 = null;
        Date date2 = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm", Locale.ENGLISH);

        tasks.add(new TodoTask("td1"));

        try{
            d1 = LocalDateTime.parse("13/09/2019 10:22".trim(), formatter);
        }catch(Exception e){
            try {
                throw new NonExistentDateException();
            } catch (NonExistentDateException ex) {
                ex.printStackTrace();
            }
        }
        date1 = new Date(d1);
        tasks.add(new HomeworkTask("d1",date1));

        try{
            d1 = LocalDateTime.parse("21/09/2019 22:22".trim(), formatter);
            d2 = LocalDateTime.parse("28/09/2019 22:11".trim(), formatter);
        }catch(Exception e){
            try {
                throw new NonExistentDateException();
            } catch (NonExistentDateException ex) {
                ex.printStackTrace();
            }
        }
        date1 = new Date(d1);
        date2 = new Date(d2);
        tasks.add(new EventsTask("e1",date1,date2));

        tasks.add(new TodoTask("td3"));

        tasks.add(new TodoTask("td4"));

        assertTrue(tasks.size()==5);
    }

    /**
     * Represents a JUnit test method for the SnoozeCommand
     * Test the command depending on the input String (user).
     */
    @Test
    public void SnoozeCommandExecuteTest() {
        SnoozeCommand snoozeCommand1 = new SnoozeCommand("snooze 4");
        try{
            snoozeCommand1.execute(tasks,ui,storage);
        }
        catch( DukeException e ){
            assertTrue(e instanceof HomeworkTypeException);
        }

        SnoozeCommand snoozeCommand2 = new SnoozeCommand("snooze 15");
        try{
            snoozeCommand2.execute(tasks,ui,storage);
        }
        catch( DukeException e ){
            assertTrue(e instanceof NonExistentTaskException);
        }

        SnoozeCommand snoozeCommand3 = new SnoozeCommand("snooze 2");
        try{
            snoozeCommand3.execute(tasks,ui,storage);
        }
        catch( DukeException e ){ // Should not happen
            assertTrue(false);
        }
    }

    /**
     * Represents the after of SnoozeCommandTest.
     */
    @AfterAll
    public static void afterSnoozeCommandTest(){
        tasks.getList().removeAll(tasks.getList());
        try {
            storage.save(tasks.getList());
        }
        catch(FileException f){
            assertTrue(false);
        }
        assertTrue(tasks.size()==0);
    }

}
