package leduc;

import leduc.command.PrioritizeCommand;
import leduc.exception.*;
import leduc.storage.Storage;
import leduc.task.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

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
            storage = new Storage(System.getProperty("user.dir")+ "/src/test/testFile/testFile.txt", System.getProperty("user.dir")+ "/src/test/testFile/configTest.txt");
        } catch (FileException e) {
            e.printStackTrace();
        } catch (MeaninglessException e) {
            e.printStackTrace();
        }

        TaskList tasks = new TaskList(new ArrayList<>());

        LocalDateTime d1 = null;
        LocalDateTime d2 = null;
        Date date1 = null;
        Date date2 = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm", Locale.ENGLISH);

        tasks.add(new TodoTask("td1"));

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
        tasks.add(new DeadlinesTask("d1",date1));

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
        tasks.add(new DeadlinesTask("d1",date1));

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
            assertTrue(e instanceof PrioritizeLimitException);
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

        assertTrue(tasks.size()==6);

        assertTrue(tasks.get(0).getPriority() == 0);
        assertTrue(tasks.get(1).getPriority()==2);
        assertTrue(tasks.get(2).getPriority()== 4);
        assertTrue(tasks.get(3).getPriority()==6);
        assertTrue(tasks.get(4).getPriority()==8);
        assertTrue(tasks.get(5).getPriority()==5);

        for ( int i = 0 ; i< tasks.getList().size() ; i++){
            PrioritizeCommand prioritizeCommand = new PrioritizeCommand("prioritize "+ (i+1) + " prio " + 5);
                try {
                    prioritizeCommand.execute(tasks, ui, storage);
                } catch (Exception e) {
                    assertTrue(false);
                }
        }

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
