package duchess.commands;

import duchess.exceptions.DuchessException;
import duchess.logic.commands.FindCommand;
import duchess.model.task.Todo;
import duchess.storage.Storage;
import duchess.storage.Store;
import duchess.ui.Ui;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class FindCommandTest {
    private final Storage storage = new Storage("data.json");
    private final Store store = new Store();
    private final Ui ui = new Ui();

    @Test
    public void execute_noSearchTerm_duchessExceptionThrown() {
        List<String> words = new ArrayList<>();
        FindCommand fc = new FindCommand(words);

        assertThrows(
            DuchessException.class,
            () -> fc.execute(store, ui, storage)
        );
    }

    @Test
    public void execute_noMatchingTasks_duchessExceptionThrown() {
        store.getTaskList().add(new Todo("read book"));
        store.getTaskList().add(new Todo("watch lectures"));
        List<String> words = new ArrayList<>();
        words.add("shopping");
        FindCommand fc = new FindCommand(words);
        assertThrows(
            DuchessException.class,
            () -> fc.execute(store, ui, storage)
        );
    }
}
