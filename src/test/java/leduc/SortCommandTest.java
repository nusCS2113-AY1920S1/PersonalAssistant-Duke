package leduc;


import leduc.command.SortCommand;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Represents a JUnit test class for the SortCommand.
 */
public class SortCommandTest {
    private static Ui ui;
    private static Storage storage;
    private static TaskList tasks;
    private static TaskList tasks2;

    /**
     * Represents the before of SortCommandTest.
     */
    @BeforeAll
    public static void beforeSortCommandTest(){
        ui = new UiEn();
        try {
            storage = new Storage(System.getProperty("user.dir")+ "/src/test/java/testFile/testFile.txt", System.getProperty("user.dir")+ "/src/test/java/testFile/configTest.txt",System.getProperty("user.dir")+ "/src/test/java/testFile/welcome.txt");
        } catch (FileException e) {
            e.printStackTrace();
        } catch (MeaninglessException e) {
            e.printStackTrace();
        }

        tasks = new TaskList(new ArrayList<>());
        tasks2 = new TaskList(new ArrayList<>());

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
        tasks2.add(new HomeworkTask("d1",date1,1));
        tasks2.add(new HomeworkTask("d2","[V]",date1));


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
        tasks2.add(new EventsTask("e1",date1,date2,2));
        tasks2.add(new EventsTask("e2","[V]",date1,date2));

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

        tasks.add(new TodoTask("1",4));
        tasks2.add(new TodoTask("t1",4));
        tasks2.add(new TodoTask("t2","[V]"));


        tasks.add(new TodoTask("2",5));

        tasks.add(new TodoTask("5",6));

        tasks.add(new TodoTask("td1",7));

        tasks.add(new TodoTask("td3",8));

        tasks.add(new TodoTask("td4",9));

        tasks.add(new TodoTask("td5",8));

        tasks.add(new TodoTask("test",7));

        tasks.add(new TodoTask("test 2",6));

        tasks.add(new TodoTask("test 3",5));

        assertTrue(tasks.size()==15);

    }
    /**
     * Represents a JUnit test method for the SortCommand.
     * Test the command depending on the input String (user).
     */
    @Test
    public void SortCommandExecuteTest() {
        SortCommand sortCommandDate = new SortCommand("sort date");
        try{
            sortCommandDate.execute(tasks,ui,storage);
        }
        catch( DukeException e ){
            assertTrue(false);
        }
        assertTrue(tasks.size()==15);

        assertEquals("d1",tasks.get(0).getTask());
        assertEquals("deadline",tasks.get(1).getTask());
        assertEquals("e1",tasks.get(2).getTask());
        assertEquals("deadlineOther",tasks.get(3).getTask());
        assertEquals("&",tasks.get(4).getTask());
        assertEquals("1",tasks.get(5).getTask());
        assertEquals("2",tasks.get(6).getTask());
        assertEquals("5",tasks.get(7).getTask());
        assertEquals("td1",tasks.get(8).getTask());
        assertEquals("td3",tasks.get(9).getTask());
        assertEquals("td4",tasks.get(10).getTask());
        assertEquals("td5",tasks.get(11).getTask());
        assertEquals("test",tasks.get(12).getTask());
        assertEquals("test 2",tasks.get(13).getTask());
        assertEquals("test 3",tasks.get(14).getTask());

        SortCommand sortCommand1 = new SortCommand("sort ok");
        try{
            sortCommand1.execute(tasks,ui,storage);
        }
        catch( DukeException e ){
            assertTrue(e instanceof MeaninglessException);
        }
        assertTrue(tasks.size()==15);

        SortCommand sortCommand2 = new SortCommand("sort 4");
        try{
            sortCommand2.execute(tasks,ui,storage);
        }
        catch( DukeException e ){
            assertTrue(e instanceof MeaninglessException);
        }
        assertTrue(tasks.size()==15);

        SortCommand sortCommand10 = new SortCommand("sort");
        try{
            sortCommand10.execute(tasks,ui,storage);
        }
        catch( DukeException e ){
            assertTrue(e instanceof EmptyArgumentException);
        }
        assertTrue(tasks.size()==15);

        SortCommand sortCommand3 = new SortCommand("sort description");
        try{
            sortCommand3.execute(tasks,ui,storage);
        }
        catch( DukeException e ){
            assertTrue(false);
        }
        assertTrue(tasks.size()==15);

        assertEquals("&",tasks.get(0).getTask());
        assertEquals("1",tasks.get(1).getTask());
        assertEquals("2",tasks.get(2).getTask());
        assertEquals("5",tasks.get(3).getTask());
        assertEquals("d1",tasks.get(4).getTask());
        assertEquals("deadline",tasks.get(5).getTask());
        assertEquals("deadlineOther",tasks.get(6).getTask());
        assertEquals("e1",tasks.get(7).getTask());
        assertEquals("td1",tasks.get(8).getTask());
        assertEquals("td3",tasks.get(9).getTask());
        assertEquals("td4",tasks.get(10).getTask());
        assertEquals("td5",tasks.get(11).getTask());
        assertEquals("test",tasks.get(12).getTask());
        assertEquals("test 2",tasks.get(13).getTask());
        assertEquals("test 3",tasks.get(14).getTask());

        SortCommand sortCommand13 = new SortCommand("sort priority");
        try{
            sortCommand13.execute(tasks,ui,storage);
        }
        catch( DukeException e ){
            assertTrue(false);
        }
        assertTrue(tasks.size()==15);

        assertEquals("&",tasks.get(4).getTask());
        assertEquals("1",tasks.get(5).getTask());
        assertEquals("2",tasks.get(6).getTask());
        assertEquals("5",tasks.get(8).getTask());
        assertEquals("d1",tasks.get(0).getTask());
        assertEquals("deadline",tasks.get(1).getTask());
        assertEquals("deadlineOther",tasks.get(2).getTask());
        assertEquals("e1",tasks.get(3).getTask());
        assertEquals("td1",tasks.get(10).getTask());
        assertEquals("td3",tasks.get(12).getTask());
        assertEquals("td4",tasks.get(14).getTask());
        assertEquals("td5",tasks.get(13).getTask());
        assertEquals("test",tasks.get(11).getTask());
        assertEquals("test 2",tasks.get(9).getTask());
        assertEquals("test 3",tasks.get(7).getTask());


        SortCommand sortCommand14 = new SortCommand("sort type");
        try{
            sortCommand14.execute(tasks2,ui,storage);
        }
        catch( DukeException e ){
            assertTrue(false);
        }
        assertTrue(tasks2.size()==6);// 2 todo tasks, 2 homework tasks, 2 event tasks


        assertEquals(tasks2.get(0).getTag(),tasks2.get(1).getTag());
        assertEquals(tasks2.get(2).getTag(),tasks2.get(3).getTag());
        assertEquals(tasks2.get(4).getTag(),tasks2.get(5).getTag());

        SortCommand sortCommand15 = new SortCommand("sort done");
        try{
            sortCommand15.execute(tasks2,ui,storage);
        }
        catch( DukeException e ){
            assertTrue(false);
        }
        assertTrue(tasks2.size()==6);// 3 done tasks, 3 not done

        assertEquals(tasks2.get(0).getMark(),tasks2.get(1).getMark());
        assertEquals(tasks2.get(1).getMark(),tasks2.get(2).getMark());
        assertEquals(tasks2.get(3).getMark(),tasks2.get(4).getMark());
        assertEquals(tasks2.get(4).getMark(),tasks2.get(5).getMark());

    }

    /**
     * Represents the after of SortCommandTest.
     */
    @AfterAll
    public static void afterSortCommandTest(){
        tasks.getList().removeAll(tasks.getList());
        tasks2.getList().removeAll(tasks2.getList());
        try {
            storage.save(tasks.getList());
        }
        catch(FileException f){
            assertTrue(false);
        }
        assertTrue(tasks.size()==0);
    }

}
