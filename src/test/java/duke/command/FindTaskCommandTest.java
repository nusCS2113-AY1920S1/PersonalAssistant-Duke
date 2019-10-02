package duke.command;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import duke.task.TaskList;
import duke.task.DukeException;
import duke.task.Ui;
import duke.task.Storage;

public class FindTaskCommandTest {
    @Test
    public void testFindCommand() throws DukeException, IOException {
        File tempFile = File.createTempFile("duke",".txt");
        tempFile.deleteOnExit();

        TaskList newTaskList = new TaskList();
        Ui newUi = new Ui();
        Storage newStorage = new Storage(tempFile.getPath());

        AddEventCommand eventCommand = new AddEventCommand(false,"event Birthday Party /at 12/12/2019 1830");
        eventCommand.execute(newTaskList, newUi, newStorage);

        AddToDoCommand todoCommand = new AddToDoCommand(false,"todo celebrate Birthday");
        todoCommand.execute(newTaskList, newUi, newStorage);

        AddDeadLineCommand deadLineCommand = new AddDeadLineCommand(false,"deadline do homework /by 12/12/2019 1830");
        deadLineCommand.execute(newTaskList, newUi, newStorage);

        assertEquals(3, newTaskList.getSize());
        newTaskList.getTask(0).markAsDone();

        FindTaskCommand newFindTaskCommand = new FindTaskCommand(false, "find Birthday");
        newFindTaskCommand.execute(newTaskList, newUi, newStorage);

        assertEquals("Here are the matching tasks in your list:\n"
                + "1.[E][DONE] Birthday Party (at: 12/12/2019 1830)\n"
                + "2.[T][NOT DONE] celebrate Birthday", newUi.output);
    }
}