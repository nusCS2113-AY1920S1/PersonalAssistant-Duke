package leduc;

import leduc.command.HomeworkCommand;
import leduc.exception.*;
import leduc.storage.Storage;
import leduc.task.TaskList;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Represents a JUnit test class for the DeadlineCommand.
 */
public class DeadlineCommandTest {
    private static Ui ui;
    private static Storage storage;
    private static TaskList tasks;

    /**
     * Represents the before of deadlineCommandExecuteTest.
     */
    @BeforeAll
    public static void beforeDeadlineCommandExecuteTest(){
        ui = new UiEn();
        try {
            storage = new Storage(System.getProperty("user.dir")+ "/src/test/java/testFile/testFile.txt", System.getProperty("user.dir")+ "/src/test/java/testFile/configTest.txt",System.getProperty("user.dir")+ "/src/test/java/testFile/welcome.txt");
        } catch (FileException e) {
            e.printStackTrace();
        } catch (MeaninglessException e) {
            e.printStackTrace();
        }
        tasks = new TaskList(new ArrayList<>());
        assertTrue(tasks.size()==0);
    }
    /**
     * Represents a JUnit test method for the DeadlineCommand.
     * Test the command depending on the input String (user).
     */
    @Test
    public void deadlineCommandExecuteTest()  {
        HomeworkCommand deadlineCommand1 = new HomeworkCommand("deadline ok");
        try{
            deadlineCommand1.execute(tasks,ui,storage);
        }
        catch( DukeException e ){
            assertTrue(e instanceof EmptyHomeworkDateException);
        }
        assertTrue(tasks.size()==0);



        HomeworkCommand deadlineCommand2 = new HomeworkCommand("deadline /by 12/12/2000 22:22");
        try{
            deadlineCommand2.execute(tasks,ui,storage);
        }
        catch(DukeException e ){
            assertTrue(e instanceof EmptyHomeworkException);
        }
        assertTrue(tasks.size()==0);



        HomeworkCommand deadlineCommand3 = new HomeworkCommand("deadline d1 /by 12-12-2000 22:22");
        try{
            deadlineCommand3.execute(tasks,ui,storage);
        }
        catch( DukeException e){
            assertTrue( e instanceof NonExistentDateException);
        }
        assertTrue(tasks.size()==0);

        HomeworkCommand deadlineCommand4 = new HomeworkCommand("deadline d1 /by 12/12/2000 22:22");
        try{
            deadlineCommand4.execute(tasks,ui,storage);
        }
        catch( DukeException e){ //should not happen
            assertTrue(false);
        }
        assertTrue(tasks.size()==1);

        HomeworkCommand deadlineCommand5 = new HomeworkCommand("deadline d1 /by 12/12/2000 22:22 prio 6");
        try{
            deadlineCommand5.execute(tasks,ui,storage);
        }
        catch( DukeException e){
            assertTrue(false);
        }
        assertTrue(tasks.size()==2);
        assertTrue(tasks.get(1).getPriority() == 6);
        assertTrue(tasks.get(0).getPriority() == 5);


        HomeworkCommand deadlineCommand6 = new HomeworkCommand("deadline d1 /by 12/12/2000 22:22 prio 12");
        try{
            deadlineCommand6.execute(tasks,ui,storage);
        }
        catch( DukeException e){
            assertTrue(e instanceof PrioritizeLimitException);
        }
        assertTrue(tasks.size()==2);

        HomeworkCommand deadlineCommand7 = new HomeworkCommand("deadline d1 /by 12/12/2000 22:22 prio Qzeaze");
        try{
            deadlineCommand7.execute(tasks,ui,storage);
        }
        catch( DukeException e){
            assertTrue(e instanceof PrioritizeLimitException);
        }
        assertTrue(tasks.size()==2);

        HomeworkCommand deadlineCommand8 = new HomeworkCommand("deadline d1 /by 12/12/2000 22:22 recu");
        try{
            deadlineCommand8.execute(tasks,ui,storage);
        }
        catch( DukeException e){
            assertTrue(e instanceof RecurrenceException);
        }

        HomeworkCommand deadlineCommand9 = new HomeworkCommand("deadline d1 /by 12/12/2000 22:22 recu day");
        try{
            deadlineCommand9.execute(tasks,ui,storage);
        }
        catch( DukeException e){
            assertTrue(e instanceof RecurrenceException);
        }

        HomeworkCommand deadlineCommand10 = new HomeworkCommand("deadline d1 /by 12/12/2000 22:22 recu week");
        try{
            deadlineCommand10.execute(tasks,ui,storage);
        }
        catch( DukeException e){
            assertTrue(e instanceof RecurrenceException);
        }

        HomeworkCommand deadlineCommand11 = new HomeworkCommand("deadline d1 /by 12/12/2000 22:22 recu month");
        try{
            deadlineCommand11.execute(tasks,ui,storage);
        }
        catch( DukeException e){
            assertTrue(e instanceof RecurrenceException);
        }

        HomeworkCommand deadlineCommand12 = new HomeworkCommand("deadline d1 /by 12/12/2000 22:22 recu day a");
        try{
            deadlineCommand12.execute(tasks,ui,storage);
        }
        catch( DukeException e){
            assertTrue(e instanceof RecurrenceException);
        }

        HomeworkCommand deadlineCommand13 = new HomeworkCommand("deadline d1 /by 12/12/2000 22:22 recu week a");
        try{
            deadlineCommand13.execute(tasks,ui,storage);
        }
        catch( DukeException e){
            assertTrue(e instanceof RecurrenceException);
        }

        HomeworkCommand deadlineCommand14 = new HomeworkCommand("deadline d1 /by 12/12/2000 22:22 recu month a");
        try{
            deadlineCommand14.execute(tasks,ui,storage);
        }
        catch( DukeException e){
            assertTrue(e instanceof RecurrenceException);
        }

        HomeworkCommand deadlineCommand15 = new HomeworkCommand("deadline d1 /by 12/12/2000 22:22 recu eazea 1");
        try{
            deadlineCommand15.execute(tasks,ui,storage);
        }
        catch( DukeException e){
            assertTrue(e instanceof RecurrenceException);
        }

        HomeworkCommand deadlineCommand16 = new HomeworkCommand("deadline d1 /by 12/12/2000 prio 6 22:22 recu");
        try{
            deadlineCommand16.execute(tasks,ui,storage);
        }
        catch( DukeException e){
            assertTrue(e instanceof RecurrenceException);
        }

        HomeworkCommand deadlineCommand17 = new HomeworkCommand("deadline d1 /by 12/12/2000 22:22 prio 6 recu day");
        try{
            deadlineCommand17.execute(tasks,ui,storage);
        }
        catch( DukeException e){
            assertTrue(e instanceof RecurrenceException);
        }

        HomeworkCommand deadlineCommand18 = new HomeworkCommand("deadline d1 /by 12/12/2000 22:22 prio 6 recu week");
        try{
            deadlineCommand18.execute(tasks,ui,storage);
        }
        catch( DukeException e){
            assertTrue(e instanceof RecurrenceException);
        }

        HomeworkCommand deadlineCommand19 = new HomeworkCommand("deadline d1 /by 12/12/2000 22:22 prio 6 recu month");
        try{
            deadlineCommand19.execute(tasks,ui,storage);
        }
        catch( DukeException e){
            assertTrue(e instanceof RecurrenceException);
        }

        HomeworkCommand deadlineCommand20 = new HomeworkCommand("deadline d1 /by 12/12/2000 22:22 prio 6 recu day a");
        try{
            deadlineCommand20.execute(tasks,ui,storage);
        }
        catch( DukeException e){
            assertTrue(e instanceof RecurrenceException);
        }

        HomeworkCommand deadlineCommand21 = new HomeworkCommand("deadline d1 /by 12/12/2000 22:22 prio 6 recu week a");
        try{
            deadlineCommand21.execute(tasks,ui,storage);
        }
        catch( DukeException e){
            assertTrue(e instanceof RecurrenceException);
        }

        HomeworkCommand deadlineCommand22 = new HomeworkCommand("deadline d1 /by 12/12/2000 22:22 prio 6 recu month a");
        try{
            deadlineCommand22.execute(tasks,ui,storage);
        }
        catch( DukeException e){
            assertTrue(e instanceof RecurrenceException);
        }

        HomeworkCommand deadlineCommand23 = new HomeworkCommand("deadline d1 /by 12/12/2000 22:22 prio 6 recu eazea 1");
        try{
            deadlineCommand23.execute(tasks,ui,storage);
        }
        catch( DukeException e){
            assertTrue(e instanceof RecurrenceException);
        }
    }

    /**
     * Represents the after of deadlineCommandExecuteTest.
     */
    @AfterAll
    public static void afterDeadlineCommandExecuteTest(){
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
