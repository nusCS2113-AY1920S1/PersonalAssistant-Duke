package duke.commands;

import duke.commons.DukeException;
import duke.parsers.Parser;
import duke.storage.Storage;
import duke.tasks.Task;
import duke.tasks.Todo;
import duke.ui.Ui;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class AddCommandTest {

    @Test
    void execute() throws DukeException {
        Ui ui = new Ui();
        Storage storage = new Storage("data/tasks.txt", ui);
        Task task = new Todo("homework");
        AddCommand addCommand = new AddCommand(task);
        addCommand.execute(ui, storage);
        assertTrue(storage.getTasks().contains(task));
    }
}
