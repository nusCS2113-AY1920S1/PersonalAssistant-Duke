package leduc;

import leduc.command.PostponeCommand;
import leduc.exception.*;
import leduc.storage.Storage;
import leduc.task.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Represents a JUnit test class for the PostponeCommand.
 */
public class PostponeCommandTest {

    private static Ui ui;
    private static Storage storage;
    private static TaskList tasks;

    /**
     * Represents the before of PostponeCommandTest.
     */
    @BeforeAll
    public static void beforePostponeCommandTest(){
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

        tasks.add(new TodoTask("todo"));

        try{
            d1 = LocalDateTime.parse("13/09/2019 01:52".trim(), formatter);
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

        try{
            d1 = LocalDateTime.parse("13/09/2019 01:52".trim(), formatter);
        }catch(Exception e){
            try {
                throw new NonExistentDateException();
            } catch (NonExistentDateException ex) {
                ex.printStackTrace();
            }
        }
        date1 = new Date(d1);
        tasks.add(new HomeworkTask("d1",date1));

        assertTrue(tasks.size()==6);
    }

    /**
     * Represents a JUnit test method for the PostponeCommand.
     * Test the command depending on the input String (user).
     */
    @Test
    public void PostponeCommandTest() {
        PostponeCommand postponeCommand1 = new PostponeCommand("postpone 4ee /by 12/12/2222 22:22");
        try{
            postponeCommand1.execute(tasks,ui,storage);
        }
        catch( DukeException e ){
            assertTrue(e instanceof NonExistentTaskException);
        }

        PostponeCommand postponeCommand2 = new PostponeCommand("postpone 15 /by 12/12/2222 22:22");
        try{
            postponeCommand2.execute(tasks,ui,storage);
        }
        catch( DukeException e ){
            assertTrue(e instanceof NonExistentTaskException);
        }

        PostponeCommand postponeCommand3 = new PostponeCommand("postpone 3 /by 12/12/2222 22:22");
        try{
            postponeCommand3.execute(tasks,ui,storage);
        }
        catch( DukeException e ){
            assertTrue(e instanceof HomeworkTypeException);
        }

        PostponeCommand postponeCommand4 = new PostponeCommand("postpone 2");
        try{
            postponeCommand4.execute(tasks,ui,storage);
        }
        catch( DukeException e ){
            assertTrue(e instanceof EmptyHomeworkDateException);
        }

        PostponeCommand postponeCommand5 = new PostponeCommand("postpone 2 /by 12/12/22a2 22:22");
        try{
            postponeCommand5.execute(tasks,ui,storage);
        }
        catch( DukeException e ){
            assertTrue(e instanceof NonExistentDateException);
        }



        Task task = tasks.get(tasks.size()-1);
        assertTrue(task.isHomework());
        Date d = ((HomeworkTask) task).getDeadlines();
        Date dAfter = d;
        dAfter.setDate(d.getDate().plusDays(1));
        Date dBefore = d;
        dAfter.setDate(d.getDate().plusDays(-1));

        PostponeCommand postponeCommand6 = new PostponeCommand("postpone 6 /by "+ dBefore);
        try{
            postponeCommand6.execute(tasks,ui,storage);
        }
        catch( DukeException e ){
            assertTrue(e instanceof PostponeHomeworkException);
        }

        PostponeCommand postponeCommand7 = new PostponeCommand("postpone 6 /by " +dAfter);
        try{
            postponeCommand7.execute(tasks,ui,storage);
        }
        catch( DukeException e ){ // Should not happen
            assertTrue(false);
        }
    }

    /**
     * Represents the after of PostponeCommandTest.
     */
    @AfterAll
    public static void afterPostponeCommandTest(){
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
