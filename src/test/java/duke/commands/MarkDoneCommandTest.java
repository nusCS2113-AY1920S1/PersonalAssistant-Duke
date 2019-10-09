package duke.commands;

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
        UiStub ui = new UiStub(new VBox());
        Storage storage = new Storage("tasks.txt", ui);
        Task task = new Todo("homework1");
        AddCommand addCommand = new AddCommand(task);
        addCommand.execute(ui, storage);
        MarkDoneCommand markDoneCommand = new MarkDoneCommand(0);
        markDoneCommand.execute(ui, storage);
        assertTrue(storage.getTasks().get(0).isDone());
        DeleteCommand deleteCommand = new DeleteCommand(0);
        deleteCommand.execute(ui, storage);
    }
}
