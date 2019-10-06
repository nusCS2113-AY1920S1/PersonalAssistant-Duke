package duke.commands;

import duke.UiStub;
import duke.commons.DukeException;
import duke.storage.Storage;
import duke.data.tasks.Task;
import duke.data.tasks.Todo;
import javafx.scene.layout.VBox;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class AddCommandTest {

    @Test
    void execute() throws DukeException {
        UiStub ui = new UiStub(new VBox());
        Storage storage = new Storage("tasks.txt", ui);
        Task task = new Todo("homework");
        AddCommand addCommand = new AddCommand(task);
        addCommand.execute(ui, storage);
        assertTrue(storage.getTasks().contains(task));
    }
}
