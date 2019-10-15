package leduc;

import leduc.command.RescheduleCommand;
import leduc.exception.*;
import leduc.storage.Storage;
import leduc.task.Task;
import leduc.task.TaskList;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Represents a JUnit test class for the RescheduleCommandTest.
 */
public class RescheduleCommandTest {
    /**
     * Represents a JUnit test method for the RescheduleCommandTest.
     * Test the command depending on the input String (user).
     */
    @Test
    public void RescheduleCommandTest() {
        Ui ui = new Ui();
        Storage storage = null;
        try {
            storage = new Storage(System.getProperty("user.dir")+ "/src/test/testFile/RescheduleCommandTest.txt", System.getProperty("user.dir")+ "/src/test/testFile/configTest.txt");
        } catch (FileException e) {
            e.printStackTrace();
        } catch (MeaninglessException e) {
            e.printStackTrace();
        }
        TaskList tasks = new TaskList(new ArrayList<Task>());
        try{
            tasks = new TaskList(storage.load()); // Use of ArrayList (A-Collections) to store tasks
        }
        catch (DukeException e){
            e.print();
        }
        assertTrue(tasks.size()==6);

        RescheduleCommand rescheduleCommand1 = new RescheduleCommand(
                "reschedule 4ee /at 12/12/2222 22:22 - 12/12/2222 22:24");
        try{
            rescheduleCommand1.execute(tasks,ui,storage);
        }
        catch( DukeException e ){
            assertTrue(e instanceof NonExistentTaskException);
        }

        RescheduleCommand rescheduleCommand2 = new RescheduleCommand(
                "reschedule 15 /at 12/12/2222 22:22 - 12/12/2222 22:24");
        try{
            rescheduleCommand2.execute(tasks,ui,storage);
        }
        catch( DukeException e ){
            assertTrue(e instanceof NonExistentTaskException);
        }

        RescheduleCommand rescheduleCommand3 = new RescheduleCommand(
                "reschedule 2 /at 12/12/2222 22:22 - 12/12/2222 22:24");
        try{
            rescheduleCommand3.execute(tasks,ui,storage);
        }
        catch( DukeException e ){
            assertTrue(e instanceof EventTypeException);
        }

        RescheduleCommand rescheduleCommand4 = new RescheduleCommand("reschedule 3");
        try{
            rescheduleCommand4.execute(tasks,ui,storage);
        }
        catch( DukeException e ){
            assertTrue(e instanceof EmptyEventDateException);
        }

        RescheduleCommand rescheduleCommand5 = new RescheduleCommand(
                "reschedule 3 /at 12/12/22a2 22:22 - 12/12/2222 22:24");
        try{
            rescheduleCommand5.execute(tasks,ui,storage);
        }
        catch( DukeException e ){
            assertTrue(e instanceof NonExistentDateException);
        }


        RescheduleCommand rescheduleCommand6 = new RescheduleCommand(
                "reschedule 3 /at 12/12/2222 22:22 - 12/12/1222 22:24");
        try{
            rescheduleCommand6.execute(tasks,ui,storage);
        }
        catch( DukeException e ){
            assertTrue(e instanceof DateComparisonEventException);
        }

        RescheduleCommand rescheduleCommand7 = new RescheduleCommand(
                "reschedule 3 /at 12/12/2019 22:22 - 12/12/2019 22:24");
        try{
            rescheduleCommand7.execute(tasks,ui,storage);
        }
        catch( DukeException e ){ // Should not happen
            assertTrue(false);
        }
    }

}
