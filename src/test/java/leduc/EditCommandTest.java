package leduc;


import leduc.command.EditCommand;
import leduc.exception.*;
import leduc.storage.Storage;
import leduc.task.EventsTask;
import leduc.task.HomeworkTask;
import leduc.task.TaskList;
import leduc.task.TodoTask;
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
 * Represents a JUnit test class for the one shot command of EditCommandTest.
 */
public class EditCommandTest {
    private static Ui ui;
    private static Storage storage;
    private static TaskList tasks;

    /**
     * Represents the before of the one shot command of EditCommandTest.
     */
    @BeforeAll
    public static void beforeEditCommandTest(){
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

        try{
            d1 = LocalDateTime.parse("14/09/2019 22:33".trim(), formatter);
        }catch(Exception e){
            try {
                throw new NonExistentDateException();
            } catch (NonExistentDateException ex) {
                ex.printStackTrace();
            }
        }
        date1 = new Date(d1);
        tasks.add(new HomeworkTask("d1",date1,1));

        try{
            d1 = LocalDateTime.parse("15/09/2019 22:23".trim(), formatter);
        }catch(Exception e){
            try {
                throw new NonExistentDateException();
            } catch (NonExistentDateException ex) {
                ex.printStackTrace();
            }
        }
        date1 = new Date(d1);
        tasks.add(new HomeworkTask("deadline",date1,1));

        try{
            d1 = LocalDateTime.parse("21/09/2019 00:00".trim(), formatter);
            d2 = LocalDateTime.parse("28/10/2019 22:22".trim(), formatter);
        }catch(Exception e){
            try {
                throw new NonExistentDateException();
            } catch (NonExistentDateException ex) {
                ex.printStackTrace();
            }
        }
        date1 = new Date(d1);
        date2 = new Date(d2);
        tasks.add(new EventsTask("e1",date1,date2,2));

        try{
            d1 = LocalDateTime.parse("22/09/2019 12:12".trim(), formatter);
        }catch(Exception e){
            try {
                throw new NonExistentDateException();
            } catch (NonExistentDateException ex) {
                ex.printStackTrace();
            }
        }
        date1 = new Date(d1);
        tasks.add(new HomeworkTask("deadlineOther",date1,2));

        tasks.add(new TodoTask("&",3));
        assertTrue(tasks.size()==5);
    }

    /**
     * Represents a JUnit test method for the EditCommandTest.
     * Test the command depending on the input String (user).
     */
    @Test
    public void EditCommandTest() {
        EditCommand editCommand1 = new EditCommand("edit odkz");
        try{
            editCommand1.execute(tasks,ui,storage);
        }
        catch( DukeException e ){
            assertTrue(e instanceof EditFormatException);
        }

        EditCommand editCommand2 = new EditCommand("edit 1 dqsdq");
        try{
            editCommand2.execute(tasks,ui,storage);
        }
        catch( DukeException e ){
            assertTrue(e instanceof EditFormatException);
        }

        EditCommand editCommand3 = new EditCommand("edit 1 dqsdq /at");
        try{
            editCommand3.execute(tasks,ui,storage);
        }
        catch( DukeException e ){
            assertTrue(e instanceof EditFormatException);
        }

        EditCommand editCommand4 = new EditCommand("edit 1 description testeditCommand4");
        try{
            editCommand4.execute(tasks,ui,storage);
        }
        catch( DukeException e ){
            assertTrue(false);
        }
        assertTrue(tasks.get(0).getTask().equals("testeditCommand4"));

        EditCommand editCommand5 = new EditCommand("edit 1 /by 12/12/2022 22:22");
        try{
            editCommand5.execute(tasks,ui,storage);
        }
        catch( DukeException e ){
            assertTrue(false);
        }

        EditCommand editCommand6 = new EditCommand("edit 1 /by 12/12/20222 22:22");
        try{
            editCommand6.execute(tasks,ui,storage);
        }
        catch( DukeException e ){
            assertTrue(e instanceof NonExistentDateException);
        }

        EditCommand editCommand7 = new EditCommand("edit 3 /by 12/12/20222 22:22");
        try{
            editCommand7.execute(tasks,ui,storage);
        }
        catch( DukeException e ){
            assertTrue(e instanceof EditFormatException);
        }

        EditCommand editCommand8 = new EditCommand("edit 3 /at 12/12/2022 22:22");
        try{
            editCommand8.execute(tasks,ui,storage);
        }
        catch( DukeException e ){
            assertTrue(e instanceof EmptyEventDateException);
        }

        EditCommand editCommand9 = new EditCommand("edit 3 /at 12/12/2022 22:22 - 10/12/2022 22:22");
        try{
            editCommand9.execute(tasks,ui,storage);
        }
        catch( DukeException e ){
            assertTrue(e instanceof DateComparisonEventException);
        }

        EditCommand editCommand10 = new EditCommand("edit 3 /at 12/12/2022 22:22 - 15/12/2022 22:22");
        try{
            editCommand10.execute(tasks,ui,storage);
        }
        catch( DukeException e ){
            assertTrue(false);
        }

    }

    /**
     * Represents the after of EditCommandTest.
     */
    @AfterAll
    public static void afterEditCommandTest(){
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
