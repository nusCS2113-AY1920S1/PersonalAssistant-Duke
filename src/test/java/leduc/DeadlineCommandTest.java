package leduc;

import leduc.command.DeadlineCommand;
import leduc.exception.*;
import leduc.storage.Storage;
import leduc.task.TaskList;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

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
    public void deadlineCommandExecuteTest()  {
        Ui ui = new Ui();
        Storage storage = null;
        try {
            storage = new Storage(System.getProperty("user.dir")+ "/src/test/testFile/testFile.txt", System.getProperty("user.dir")+ "/src/test/testFile/configTest.txt");
        } catch (FileException e) {
            e.printStackTrace();
        } catch (MeaninglessException e) {
            e.printStackTrace();
        }
        TaskList tasks = new TaskList(new ArrayList<>());
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
            assertTrue( e instanceof NonExistentDateException);
        }
        assertTrue(tasks.size()==0);

        DeadlineCommand deadlineCommand4 = new DeadlineCommand("deadline d1 /by 12/12/2000 22:22");
        try{
            deadlineCommand4.execute(tasks,ui,storage);
        }
        catch( DukeException e){ //should not happen
            assertTrue(false);
        }
        assertTrue(tasks.size()==1);

        DeadlineCommand deadlineCommand5 = new DeadlineCommand("deadline d1 /by 12/12/2000 22:22 prio 6");
        try{
            deadlineCommand5.execute(tasks,ui,storage);
        }
        catch( DukeException e){
            assertTrue(false);
        }
        assertTrue(tasks.size()==2);
        assertTrue(tasks.get(1).getPriority() == 6);
        assertTrue(tasks.get(0).getPriority() == 5);


        DeadlineCommand deadlineCommand6 = new DeadlineCommand("deadline d1 /by 12/12/2000 22:22 prio 12");
        try{
            deadlineCommand6.execute(tasks,ui,storage);
        }
        catch( DukeException e){
            assertTrue(e instanceof PrioritizeLimitException);
        }
        assertTrue(tasks.size()==2);

        DeadlineCommand deadlineCommand7 = new DeadlineCommand("deadline d1 /by 12/12/2000 22:22 prio Qzeaze");
        try{
            deadlineCommand7.execute(tasks,ui,storage);
        }
        catch( DukeException e){
            assertTrue(e instanceof PrioritizeLimitException);
        }
        assertTrue(tasks.size()==2);

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
