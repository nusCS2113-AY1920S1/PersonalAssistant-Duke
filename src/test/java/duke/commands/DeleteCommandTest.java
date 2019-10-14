package duke.commands;

import duke.StorageStub;
import duke.commons.exceptions.DukeException;
import duke.storage.Storage;
import duke.data.tasks.Task;
import duke.data.tasks.Todo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

class DeleteCommandTest {

    @Test
    void execute() throws DukeException {
        Storage storage = new StorageStub();
        Task task = new Todo("homework");
        storage.getTasks().add(task);
        DeleteCommand deleteCommand = new DeleteCommand(0);
        deleteCommand.execute(storage);
        assertFalse(storage.getTasks().contains(task));
    }
}
