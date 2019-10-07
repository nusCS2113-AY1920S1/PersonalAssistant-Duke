package leduc;


import leduc.command.SortCommand;
import leduc.exception.*;
import leduc.storage.Storage;
import leduc.task.Task;
import leduc.task.TaskList;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Represents a JUnit test class for the SortCommand.
 */
public class SortCommandTest {

    /**
     * Represents a JUnit test method for the SortCommand.
     * Test the command depending on the input String (user).
     */
    @Test
    public void SortCommandExecuteTest() {
        Ui ui = new Ui();
        Storage storage = new Storage(System.getProperty("user.dir")+ "/src/test/testFile/SortCommandTest.txt");
        TaskList tasks = new TaskList(new ArrayList<Task>());
        try{
            tasks = new TaskList(storage.load()); // Use of ArrayList (A-Collections) to store tasks
        }
        catch (DukeException e){
            e.print();
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

        SortCommand sortCommand4 = new SortCommand("sort date");
        try{
            sortCommand4.execute(tasks,ui,storage);
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
    }

}
