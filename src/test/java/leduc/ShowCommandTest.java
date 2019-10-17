package leduc;

import leduc.command.ShowCommand;
import leduc.exception.DukeException;
import leduc.exception.FileException;
import leduc.exception.MeaninglessException;
import leduc.storage.Storage;
import leduc.task.Task;
import leduc.task.TaskList;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ShowCommandTest {
    @Test
    public void ShowCommandTest(){
        Ui ui = new Ui();
        Storage storage = null;
        try {
            storage = new Storage(System.getProperty("user.dir")+ "/src/test/testFile/ShowCommandTest.txt", System.getProperty("user.dir")+ "/src/test/testFile/configTest.txt");
        } catch (FileException e) {
            e.printStackTrace();
        } catch (MeaninglessException e) {
            e.printStackTrace();
        }
        TaskList tasks = new TaskList(new ArrayList<Task>());
        ShowCommand showCommand = new ShowCommand("test");
        int dayOfWeek = 0;
        try {
            dayOfWeek = showCommand.getDayOfWeekInInt("monday");
        } catch (MeaninglessException e) {
            e.printStackTrace();
        }
        assertEquals(1, dayOfWeek);
        try {
            dayOfWeek = showCommand.getDayOfWeekInInt("tuesday");
        } catch (MeaninglessException e) {
            e.printStackTrace();
        }
        assertEquals(2, dayOfWeek);
        try {
            dayOfWeek = showCommand.getDayOfWeekInInt("wednesday");
        } catch (MeaninglessException e) {
            e.printStackTrace();
        }
        assertEquals(3, dayOfWeek);
        try {
            dayOfWeek = showCommand.getDayOfWeekInInt("thursday");
        } catch (MeaninglessException e) {
            e.printStackTrace();
        }
        assertEquals(4, dayOfWeek);
        try {
            dayOfWeek = showCommand.getDayOfWeekInInt("friday");
        } catch (MeaninglessException e) {
            e.printStackTrace();
        }
        assertEquals(5, dayOfWeek);
        try {
            dayOfWeek = showCommand.getDayOfWeekInInt("saturday");
        } catch (MeaninglessException e) {
            e.printStackTrace();
        }
        assertEquals(6, dayOfWeek);
        try {
            dayOfWeek = showCommand.getDayOfWeekInInt("sunday");
        } catch (MeaninglessException e) {
            e.printStackTrace();
        }
        assertEquals(7, dayOfWeek);

        try {
            dayOfWeek = showCommand.getDayOfWeekInInt("aazeadzda");
            fail("The exception should be a MeaninglessException");
        } catch (MeaninglessException e) {
            assertTrue(e.print().contains("MeaninglessException"));
        }

        try {
            ShowCommand showCommand1 = new ShowCommand("show dsqdsqdq");
            showCommand1.execute(tasks, ui, storage);
            fail("The exception should be a WrongParameterException");
        } catch (DukeException e) {
            assertTrue(e.print().contains("WrongParameterException"));
        }

        try {
            ShowCommand showCommand2 = new ShowCommand("show day dqdqd");
            showCommand2.execute(tasks, ui, storage);
            fail("The exception should be a NonExistentDateException");
        } catch (DukeException e) {
            assertTrue(e.print().contains("NonExistentDateException"));
        }
        try {
            ShowCommand showCommand3 = new ShowCommand("show dayofweek dqdqd");
            showCommand3.execute(tasks, ui, storage);
            fail("The exception should be a MeaninglessException");
        } catch (DukeException e) {
            assertTrue(e.print().contains("MeaninglessException"));
        }
        try {
            ShowCommand showCommand3 = new ShowCommand("show month /2019");
            showCommand3.execute(tasks, ui, storage);
            fail("The exception should be a NonExistentDateException");
        } catch (DukeException e) {
            assertTrue(e.print().contains("NonExistentDateException"));
        }
        try {
            ShowCommand showCommand3 = new ShowCommand("show month 01/");
            showCommand3.execute(tasks, ui, storage);
            fail("The exception should be a NonExistentDateException");
        } catch (DukeException e) {
            assertTrue(e.print().contains("NonExistentDateException"));
        }
        try {
            ShowCommand showCommand3 = new ShowCommand("show month 20/2010");
            showCommand3.execute(tasks, ui, storage);
            fail("The exception should be a NonExistentDateException");
        } catch (DukeException e) {
            assertTrue(e.print().contains("NonExistentDateException"));
        }
        try {
            ShowCommand showCommand3 = new ShowCommand("show month sqd/dqd");
            showCommand3.execute(tasks, ui, storage);
            fail("The exception should be a NonExistentDateException");
        } catch (DukeException e) {
            assertTrue(e.print().contains("NonExistentDateException"));
        }
        try {
            ShowCommand showCommand3 = new ShowCommand("show year dsqdqs");
            showCommand3.execute(tasks, ui, storage);
            fail("The exception should be a NonExistentDateException");
        } catch (DukeException e) {
            assertTrue(e.print().contains("NonExistentDateException"));
        }
    }
}
