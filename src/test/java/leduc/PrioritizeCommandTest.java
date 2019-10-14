package leduc;

import leduc.command.PrioritizeCommand;
import leduc.exception.*;
import leduc.storage.Storage;
import leduc.task.Task;
import leduc.task.TaskList;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Represents a JUnit test class for the PrioritizeCommand.
 */
public class PrioritizeCommandTest {
    /**
     * Represents a JUnit test method for the PrioritizeCommand.
     * Test the command depending on the input String (user).
     */
    @Test
    public void PrioritizeCommandTest() {
        Ui ui = new Ui();
        Storage storage = null;
        try {
            storage = new Storage(System.getProperty("user.dir")+ "/src/test/testFile/PrioritizeCommandTest.txt", System.getProperty("user.dir")+ "/src/test/testFile/configTest.txt");
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

        for (Task t : tasks.getList()){
            assertTrue(t.getPriority()==5);
        }

        PrioritizeCommand prioritizeCommand1 = new PrioritizeCommand("prioritize 5 ,ez");
        try{
            prioritizeCommand1.execute(tasks,ui,storage);
        }
        catch( DukeException e ){
            assertTrue(e instanceof PrioritizeFormatException);
        }

        PrioritizeCommand prioritizeCommand2 = new PrioritizeCommand("prioritize 5 ");
        try{
            prioritizeCommand2.execute(tasks,ui,storage);
        }
        catch( DukeException e ){
            assertTrue(e instanceof PrioritizeFormatException);
        }

        PrioritizeCommand prioritizeCommand3 = new PrioritizeCommand("prioritize 5 prio");
        try{
            prioritizeCommand3.execute(tasks,ui,storage);
        }
        catch( DukeException e ){
            assertTrue(e instanceof PrioritizeFormatException);
        }

        PrioritizeCommand prioritizeCommand4 = new PrioritizeCommand("prioritize 5 pfzezfe");
        try{
            prioritizeCommand4.execute(tasks,ui,storage);
        }
        catch( DukeException e ){
            assertTrue(e instanceof PrioritizeFormatException);
        }

        PrioritizeCommand prioritizeCommand5  = new PrioritizeCommand("prioritize 5 prio dqsdqs");
        try{
            prioritizeCommand5.execute(tasks,ui,storage);
        }
        catch( DukeException e ){
            assertTrue(e instanceof PrioritizeFormatException);
        }

        PrioritizeCommand prioritizeCommand6  = new PrioritizeCommand("prioritize 15 prio 2");
        try{
            prioritizeCommand6.execute(tasks,ui,storage);
        }
        catch( DukeException e ){
            assertTrue(e instanceof NonExistentTaskException);
        }

        PrioritizeCommand prioritizeCommand7  = new PrioritizeCommand("prioritize");
        try{
            prioritizeCommand7.execute(tasks,ui,storage);
        }
        catch( DukeException e ){
            assertTrue(e instanceof EmptyArgumentException);
        }

        for ( int i = 0 ; i< tasks.getList().size() ; i++){
            int j = i *2;
            PrioritizeCommand prioritizeCommand = new PrioritizeCommand("prioritize " + (i+1) + " prio " + j);
            if (j< 9) {
                try {
                    prioritizeCommand.execute(tasks, ui, storage);
                } catch (Exception e) {
                    assertTrue(false);
                }
            }
            else{
                try {
                    prioritizeCommand.execute(tasks, ui, storage);
                } catch (Exception e) {
                    assertTrue(e instanceof PrioritizeLimitException);
                }
            }
        }

        TaskList tasks2 = new TaskList(new ArrayList<>());
        try{
            tasks2 = new TaskList(storage.load()); // Use of ArrayList (A-Collections) to store tasks
        }
        catch (DukeException e){
            e.print();
        }
        assertTrue(tasks2.size()==6);

        assertTrue(tasks2.get(0).getPriority() == 0);
        assertTrue(tasks2.get(1).getPriority()==2);
        assertTrue(tasks2.get(2).getPriority()== 4);
        assertTrue(tasks2.get(3).getPriority()==6);
        assertTrue(tasks2.get(4).getPriority()==8);
        assertTrue(tasks2.get(5).getPriority()==5);

        for ( int i = 0 ; i< tasks2.getList().size() ; i++){
            PrioritizeCommand prioritizeCommand = new PrioritizeCommand("prioritize "+ (i+1) + " prio " + 5);
                try {
                    prioritizeCommand.execute(tasks2, ui, storage);
                } catch (Exception e) {
                    assertTrue(false);
                }
        }

    }

}
