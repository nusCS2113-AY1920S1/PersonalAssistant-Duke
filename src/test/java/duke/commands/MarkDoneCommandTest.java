package duke.commands;

import duke.StorageStub;
import duke.UiStub;
import duke.commons.exceptions.DukeException;
import duke.storage.Storage;
import duke.data.tasks.Task;
import duke.data.tasks.Todo;
import javafx.scene.layout.VBox;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;


class MarkDoneCommandTest {

    @Test
    void execute() throws DukeException {
        Storage storage = new StorageStub();;
        Task task = new Todo("homework1");
        AddCommand addCommand = new AddCommand(task);
        addCommand.execute(storage);
        MarkDoneCommand markDoneCommand = new MarkDoneCommand(0);
        markDoneCommand.execute(storage);
        assertTrue(storage.getTasks().get(0).isDone());
        DeleteCommand deleteCommand = new DeleteCommand(0);
        deleteCommand.execute(storage);
    }
}
