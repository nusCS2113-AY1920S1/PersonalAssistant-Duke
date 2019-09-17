package leduc;

import leduc.command.SnoozeCommand;
import leduc.exception.*;
import leduc.storage.Storage;
import leduc.task.Task;
import leduc.task.TaskList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Represents a JUnit test class for the SnoozeCommand.
 */
public class SnoozeCommandTest {
    /**
     * Represents a JUnit test method for the SnoozeCommand
     * Test the command depending on the input String (user).
     */
    @Test
    public void SnoozeCommandExecuteTest() {
        Ui ui = new Ui();
        String file = System.getProperty("user.dir")+ "/src/test/testFile/SnoozeCommandTest.txt";
        ui.display(file);
        Storage storage = new Storage(file);
        storage.getNewAppendWrite(storage.getFilePath(),ui); // need to initialized
        Parser parser = new Parser();
        TaskList tasks = new TaskList(new ArrayList<Task>());
        try{
            tasks = new TaskList(storage.load(parser, ui)); // Use of ArrayList (A-Collections) to store tasks
        }
        catch (DukeException e){
            e.print();
        }
        catch (IOException e){
            ui.display("\t IOException: \n\t\t error when readFile for initialization of tasks list");
        }
        assertTrue(tasks.size()==8);

        SnoozeCommand snoozeCommand1 = new SnoozeCommand("snooze 4");
        try{
            snoozeCommand1.execute(tasks,ui,storage,parser);
        }
        catch( DukeException e ){
            assertTrue(e instanceof SnoozeTypeException);
        }

        SnoozeCommand snoozeCommand2 = new SnoozeCommand("snooze 15");
        try{
            snoozeCommand2.execute(tasks,ui,storage,parser);
        }
        catch( DukeException e ){
            assertTrue(e instanceof NonExistentTaskException);
        }

        SnoozeCommand snoozeCommand3 = new SnoozeCommand("snooze 3");
        try{
            snoozeCommand3.execute(tasks,ui,storage,parser);
        }
        catch( DukeException e ){ // Should not happen
            assertTrue(false);
        }
    }

}
