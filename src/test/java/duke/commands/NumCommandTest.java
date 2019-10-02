package duke.commands;

import duke.Storage;
import duke.TaskList;
import duke.Ui;
import duke.enums.CommandType;
import duke.enums.TaskType;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NumCommandTest {
    Ui ui = new Ui();
    Storage storage = new Storage("");
    TaskList newList = new TaskList();

    /*
    @Test
    void execute_deleteTask_success() throws IndexOutOfBoundsException {
        OutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);
        System.setOut(ps);
        newList.addItem(TaskType.TODO, "Test FIND");
        new NumCommand(CommandType.DELETE, 1).execute(newList, ui, storage);
        assertEquals("Okay! I've deleted this task: Test FIND\n"
                + "The task was not completed.", os.toString().trim());
    }

    @Test
    void execute_deleteTask_notFound() throws IndexOutOfBoundsException {
        try {
            newList.addItem(TaskType.TODO, "Test FIND");
            new NumCommand(CommandType.DELETE, 2).execute(newList, ui, storage);
        } catch (IndexOutOfBoundsException e) {
            assertEquals("Index 1 out of bounds for length 1", e.getMessage());
        }
    }

    @Test
    void execute_markAsDone_success() throws IndexOutOfBoundsException {
        newList.addItem(TaskType.DEADLINE, "Test DEADLINE /by 15/12/2019 1500");
        newList.markTaskAsDone(0);
        assertEquals(true,newList.getTask(0).getIsDone());
    }

    @Test
    void execute_markAsDone_notFound() throws IndexOutOfBoundsException {
        try {
            newList.addItem(TaskType.DEADLINE, "Test DEADLINE /by 15/12/2019 1500");
            newList.markTaskAsDone(1);
        } catch (IndexOutOfBoundsException e) {
            assertEquals("Index 1 out of bounds for length 1", e.getMessage());
        }
    }
    */
}