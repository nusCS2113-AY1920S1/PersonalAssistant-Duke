package duke.commands;

import duke.storage.Storage;
import duke.tasks.Task;
import duke.tasks.Todo;
import duke.ui.Ui;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class AddCommandTest {

    @Test
    void execute() {
        Ui ui = new Ui();
        Storage storage = new Storage("tasks.txt", ui);
        Task task = new Todo("homework");
        AddCommand addCommand = new AddCommand(task);
        addCommand.execute(ui, storage);
        assertTrue(storage.getTasks().contains(task));
    }
}