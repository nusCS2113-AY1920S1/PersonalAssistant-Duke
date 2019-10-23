package leduc;

import leduc.command.EventCommand;
import leduc.exception.*;
import leduc.storage.Storage;
import leduc.task.EventsTask;
import leduc.task.Task;
import leduc.task.TaskList;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class EventCommandTest {

    @Test
    public void eventCommandExecuteTest(){
        Ui ui = new Ui();
        Storage storage = null;
        try {
            storage = new Storage(System.getProperty("user.dir")+ "/src/test/testFile/testFile.txt", System.getProperty("user.dir")+ "/src/test/testFile/configTest.txt");
        } catch (FileException e) {
            e.printStackTrace();
        } catch (MeaninglessException e) {
            e.printStackTrace();
        }
        List<Task> tasksList = new ArrayList<>();
        TaskList tasks = new TaskList( tasksList);
        LocalDateTime d1 = null;
        LocalDateTime d2 = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm", Locale.ENGLISH);
        try{
            d1 = LocalDateTime.parse("11/12/2019 20:30".trim(), formatter);
            d2 = LocalDateTime.parse("12/12/2019 20:40".trim(), formatter);
        }catch(Exception e){
            try {
                throw new NonExistentDateException();
            } catch (NonExistentDateException ex) {
                ex.printStackTrace();
            }
        }
        Date date1 = new Date(d1);
        Date date2 = new Date(d2);
        tasks.add(new EventsTask("testConflict", date1, date2));

        EventCommand eventCommand1 = new EventCommand("event");
        try{
            eventCommand1.execute(tasks,ui, storage);
            fail("should throw exception when there is no description");
        } catch (EmptyEventException | EmptyEventDateException | FileException | NonExistentDateException | ConflictDateException | PrioritizeLimitException e) {
            assertTrue(e.print().contains("description"));
        }

        EventCommand eventCommand2 = new EventCommand("event testNoDate");
        try{
            eventCommand2.execute(tasks,ui, storage);
            fail("should throw exception when there is no date");
        } catch (EmptyEventException | EmptyEventDateException | FileException | NonExistentDateException | ConflictDateException | PrioritizeLimitException e) {
            assertTrue(e.print().contains("period"));
        }

        EventCommand eventCommand3 = new EventCommand("event testNoDate /at");
        try{
            eventCommand3.execute(tasks,ui, storage);
            fail("should throw exception when there is no date");
        } catch (EmptyEventException | EmptyEventDateException | FileException | NonExistentDateException | ConflictDateException | PrioritizeLimitException e) {
            assertTrue(e.print().contains("period"));
        }

        EventCommand eventCommand4 = new EventCommand("event testNoDate /at 12/12/2019 20:30");
        try{
            eventCommand4.execute(tasks,ui, storage);
            fail("should throw exception when there is no period");
        } catch (EmptyEventException | EmptyEventDateException | FileException | NonExistentDateException | ConflictDateException | PrioritizeLimitException e) {
            assertTrue(e.print().contains("period"));
        }

        EventCommand eventCommand5 = new EventCommand("event testNoDate /at 12/12/2019 20:30 12/12/2019 20:40");
        try{
            eventCommand5.execute(tasks,ui, storage);
            fail("should throw exception when there is a problem with the date");
        } catch (EmptyEventException | EmptyEventDateException | FileException | NonExistentDateException | ConflictDateException | PrioritizeLimitException e) {
            assertTrue(e.print().contains("period"));
        }

        EventCommand eventCommand6 = new EventCommand("event testNoDate /at - 12/12/2019 20:30");
        try{
            eventCommand6.execute(tasks,ui, storage);
            fail("should throw exception when there is no date");
        } catch (EmptyEventException | EmptyEventDateException | FileException | NonExistentDateException | ConflictDateException | PrioritizeLimitException e) {
            assertTrue(e.print().contains("period"));
        }

        EventCommand eventCommand7 = new EventCommand("event testNoDate /at 12/12/2019 20:30 - ");
        try{
            eventCommand7.execute(tasks,ui, storage);
            fail("should throw exception when there is no date");
        } catch (EmptyEventException | EmptyEventDateException | FileException | NonExistentDateException | ConflictDateException | PrioritizeLimitException e) {
            assertTrue(e.print().contains("period"));
        }

        EventCommand eventCommand8 = new EventCommand("event testNoDate /at a - b");
        try{
            eventCommand8.execute(tasks,ui, storage);
            fail("should throw exception when there is a wrong format");
        } catch (EmptyEventException | EmptyEventDateException | FileException | NonExistentDateException | ConflictDateException | PrioritizeLimitException e) {
            assertTrue(e.print().contains("date doesn't exist"));
        }

        EventCommand eventCommand9 = new EventCommand("event testNoDate /at 12-12-2019 20:30 - 12-12-2019 20:40");
        try{
            eventCommand9.execute(tasks,ui, storage);
            fail("should throw exception when there is a wrong format");
        } catch (EmptyEventException | EmptyEventDateException | FileException | NonExistentDateException | ConflictDateException | PrioritizeLimitException e) {
            assertTrue(e.print().contains("date doesn't exist"));
        }

        EventCommand eventCommand10 = new EventCommand("event testNoDate /at 12/12/2019 2030 - 12/12/2019 20:40");
        try{
            eventCommand10.execute(tasks,ui, storage);
            fail("should throw exception when there is a wrong format");
        } catch (EmptyEventException | EmptyEventDateException | FileException | NonExistentDateException | ConflictDateException | PrioritizeLimitException e) {
            assertTrue(e.print().contains("date doesn't exist"));
        }

        EventCommand eventCommand11 = new EventCommand("event testConflictDate /at 11/12/2019 20:20 - 11/12/2019 21:00");
        try{
            eventCommand11.execute(tasks,ui, storage);
            fail("should throw exception when there is a conflict between the date");
        } catch (EmptyEventException | EmptyEventDateException | FileException | NonExistentDateException | ConflictDateException | PrioritizeLimitException e) {
            assertTrue(e.print().contains("conflict"));
        }

        EventCommand eventCommand12 = new EventCommand("event testConflictDate /at 11/12/2019 20:20 - 12/12/2019 21:00");
        try{
            eventCommand12.execute(tasks,ui, storage);
            fail("should throw exception when there is a conflict between the date");
        } catch (EmptyEventException | EmptyEventDateException | FileException | NonExistentDateException | ConflictDateException | PrioritizeLimitException e) {
            assertTrue(e.print().contains("conflict"));
        }

        EventCommand eventCommand13 = new EventCommand("event testConflictDate /at 12/12/2019 20:20 - 12/12/2019 21:00");
        try{
            eventCommand13.execute(tasks,ui, storage);
            fail("should throw exception when there is a conflict between the date");
        } catch (EmptyEventException | EmptyEventDateException | FileException | NonExistentDateException | ConflictDateException | PrioritizeLimitException e) {
            assertTrue(e.print().contains("conflict"));
        }

        EventCommand eventCommand14 = new EventCommand("event testConflictDate /at 12/12/2019 20:20 - 12/12/2019 20:30");
        try{
            eventCommand14.execute(tasks,ui, storage);
            fail("should throw exception when there is a conflict between the date");
        } catch (EmptyEventException | EmptyEventDateException | FileException | NonExistentDateException | ConflictDateException | PrioritizeLimitException e) {
            assertTrue(e.print().contains("conflict"));
        }


        EventCommand eventCommand15 = new EventCommand("event e1 /at 12/12/2000 22:22 - 12/12/2000 22:23 prio 6");
        try{
            eventCommand15.execute(tasks,ui,storage);
        }
        catch( DukeException e){
            assertTrue(false);
        }
        assertTrue(tasks.size()==2);
        assertTrue(tasks.get(1).getPriority() == 6);

        EventCommand eventCommand16 = new EventCommand("event e2 /at 12/12/2001 22:22 - 12/12/2001 22:23 prio 12");
        try{
            eventCommand16.execute(tasks,ui,storage);
        }
        catch( DukeException e){
            assertTrue(e instanceof PrioritizeLimitException);
        }
        assertTrue(tasks.size()==2);

        EventCommand eventCommand17 = new EventCommand("event e3 /at 12/12/2002 22:22 - 12/12/2002 22:23 prio Qzeaze");
        try{
            eventCommand17.execute(tasks,ui,storage);
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
