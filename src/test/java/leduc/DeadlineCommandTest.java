package leduc;

import leduc.command.DeadlineCommand;
import leduc.exception.DateFormatException;
import leduc.exception.DukeException;
import leduc.exception.EmptyDeadlineDateException;
import leduc.exception.EmptyDeadlineException;
import leduc.storage.Storage;
import leduc.task.Task;
import leduc.task.TaskList;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Represents a JUnit test class for the DeadlineCommand.
 */
public class DeadlineCommandTest {

    /**
     * Represents a JUnit test method for the DeadlineCommand.
     * Test the command depending on the input String (user).
     */
    @Test
    public void deadlineCommandExecuteTest() {
        Ui ui = new Ui();
        Storage storage = new Storage("testFile/DeadlineCommandTest.txt");
        List<Task> tasksList = new ArrayList<>();
        TaskList tasks = new TaskList( tasksList);
        assertTrue(tasks.size()==0);



        DeadlineCommand deadlineCommand1 = new DeadlineCommand("deadline ok");
        try{
            deadlineCommand1.execute(tasks,ui,storage);
        }
        catch( DukeException e ){
            assertTrue(e instanceof EmptyDeadlineDateException);
        }
        assertTrue(tasks.size()==0);



        DeadlineCommand deadlineCommand2 = new DeadlineCommand("deadline /by 12/12/2000 22:22");
        try{
            deadlineCommand2.execute(tasks,ui,storage);
        }
        catch(DukeException e ){
            assertTrue(e instanceof EmptyDeadlineException);
        }
        assertTrue(tasks.size()==0);



        DeadlineCommand deadlineCommand3 = new DeadlineCommand("deadline d1 /by 12-12-2000 22:22");
        try{
            deadlineCommand3.execute(tasks,ui,storage);
        }
        catch( DukeException e){
            assertTrue( e instanceof DateFormatException);
        }
        assertTrue(tasks.size()==0);




    }

}
