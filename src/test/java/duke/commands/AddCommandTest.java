package duke.commands;

import duke.StorageStub;
import duke.commons.exceptions.DukeException;
import duke.storage.Storage;
import duke.data.tasks.Task;
import duke.data.tasks.Todo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class AddCommandTest {

    @Test
    void execute() throws DukeException {
        Storage storage = new StorageStub();
        Task task = new Todo("homework");
        AddCommand addCommand = new AddCommand(task);
        addCommand.execute(storage);
        assertTrue(storage.getTasks().contains(task));
    }
}
