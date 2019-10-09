package duke.commands;

import duke.UiStub;
import duke.commons.exceptions.DukeException;
import duke.storage.Storage;
import duke.data.tasks.Task;
import duke.data.tasks.Todo;
import javafx.scene.layout.VBox;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

class DeleteCommandTest {

    @Test
    void execute() throws DukeException {
        UiStub ui = new UiStub(new VBox());
        Storage storage = new Storage("tasks.txt", ui);
        Task task = new Todo("homework");
        DeleteCommand deleteCommand = new DeleteCommand(0);
        deleteCommand.execute(ui, storage);
        assertFalse(storage.getTasks().contains(task));
    }
}
