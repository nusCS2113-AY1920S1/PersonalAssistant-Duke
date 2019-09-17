package leduc;

import leduc.command.PostponeCommand;
import leduc.exception.*;
import leduc.storage.Storage;
import leduc.task.DeadlinesTask;
import leduc.task.Task;
import leduc.task.TaskList;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Represents a JUnit test class for the PostponeCommand.
 */
public class PostponeCommandTest {
    /**
     * Represents a JUnit test method for the PostponeCommand.
     * Test the command depending on the input String (user).
     */
    @Test
    public void PostponeCommandTest() {
        Ui ui = new Ui();
        Storage storage = new Storage(System.getProperty("user.dir")+ "/src/test/testFile/PostponeCommandTest.txt");
        TaskList tasks = new TaskList(new ArrayList<Task>());
        try{
            tasks = new TaskList(storage.load()); // Use of ArrayList (A-Collections) to store tasks
        }
        catch (DukeException e){
            e.print();
        }
        assertTrue(tasks.size()==6);

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
            assertTrue(e instanceof DeadlineTypeException);
        }

        PostponeCommand postponeCommand4 = new PostponeCommand("postpone 2");
        try{
            postponeCommand4.execute(tasks,ui,storage);
        }
        catch( DukeException e ){
            assertTrue(e instanceof EmptyDeadlineDateException);
        }

        PostponeCommand postponeCommand5 = new PostponeCommand("postpone 2 /by 12/12/22a2 22:22");
        try{
            postponeCommand5.execute(tasks,ui,storage);
        }
        catch( DukeException e ){
            assertTrue(e instanceof NonExistentDateException);
        }



        Task task = tasks.get(tasks.size()-1);
        assertTrue(task.isDeadline());
        Date d = ((DeadlinesTask) task).getDeadlines();
        Date dAfter = d;
        dAfter.setD(d.getD().plusDays(1));
        Date dBefore = d;
        dAfter.setD(d.getD().plusDays(-1));

        PostponeCommand postponeCommand6 = new PostponeCommand("postpone 6 /by "+ dBefore);
        try{
            postponeCommand6.execute(tasks,ui,storage);
        }
        catch( DukeException e ){
            assertTrue(e instanceof PostponeDeadlineException);
        }

        PostponeCommand postponeCommand7 = new PostponeCommand("postpone 6 /by " +dAfter);
        try{
            postponeCommand7.execute(tasks,ui,storage);
        }
        catch( DukeException e ){ // Should not happen
            assertTrue(false);
        }
    }

}
