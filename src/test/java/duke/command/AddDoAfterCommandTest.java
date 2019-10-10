package duke.command;

import duke.task.TaskList;
import duke.task.DukeException;
import duke.task.Ui;
import duke.task.Storage;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddDoAfterCommandTest {
    @Test
    public void testAddCommand() throws DukeException, IOException {
        File tempFile = File.createTempFile("duke",".txt");
        tempFile.deleteOnExit();

        TaskList newTaskList = new TaskList();
        Ui newUi = new Ui();
        Storage newStorage = new Storage(tempFile.getPath());

        AddDoAfterCommand doAfterCommand = new AddDoAfterCommand(false,
                "doafter To return book /after 13/10/2019 1830");
        doAfterCommand.execute(newTaskList, newUi, newStorage);

        assertEquals(1, newTaskList.getSize());
        assertEquals("[A][NOT DONE] To return book (after: 13/10/2019 1830)", newTaskList.getTask(0).toString());

        doAfterCommand = new AddDoAfterCommand(false,
                "doafter To borrow book /after 2");

        AddToDoCommand toDoCommand = new AddToDoCommand(false,
                "todo return book");
        toDoCommand.execute(newTaskList, newUi, newStorage);
        doAfterCommand.execute(newTaskList, newUi, newStorage);
        assertEquals(3, newTaskList.getSize());
        assertEquals("[A][NOT DONE] To borrow book (after: return book)", newTaskList.getTask(2).toString());

    }
}