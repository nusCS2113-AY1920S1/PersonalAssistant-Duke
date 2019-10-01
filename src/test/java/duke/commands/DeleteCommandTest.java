package duke.commands;

import duke.UiStub;
import duke.commons.DukeException;
import duke.storage.Storage;
import duke.tasks.Task;
import duke.tasks.Todo;
import javafx.scene.layout.VBox;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class DeleteCommandTest {

    @Test
    void execute() throws DukeException {
        UiStub ui = new UiStub(new VBox());
        Storage storage = new Storage("tasks.txt", ui);
        Task task = new Todo("homework");
        DeleteCommand deleteCommand = new DeleteCommand(0);
        deleteCommand.execute(ui, storage);
        assertTrue(!storage.getTasks().contains(task));
    }
}
