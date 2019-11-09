package duchess.commands;

import duchess.exceptions.DuchessException;
import duchess.logic.commands.Command;
import duchess.logic.commands.DeleteModuleCommand;
import duchess.model.Module;
import duchess.model.task.Task;
import duchess.model.task.Todo;
import duchess.storage.Storage;
import duchess.storage.Store;
import duchess.ui.Ui;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DeleteModuleCommandTest {
    private final Store store = new Store();
    private final Storage storage = new Storage("data.json");
    private final Ui ui = new Ui();

    @Test
    public void execute_executes_whenModuleInRange() throws DuchessException {
        Module module = new Module("CS1231", "Module");
        store.getModuleList().add(module);
        assertTrue(store.getModuleList().size() == 1);

        Command deleteCommand = new DeleteModuleCommand(1);
        deleteCommand.execute(store, ui, storage);
        assertTrue(store.getModuleList().size() == 0);
    }

    @Test
    public void execute_whenModuleNotInRange_throwsException() throws DuchessException {
        Module module = new Module("CS1231", "Module");
        store.getModuleList().add(module);
        assertTrue(store.getModuleList().size() == 1);

        Command deleteCommand = new DeleteModuleCommand(2);
        assertThrows(
            DuchessException.class,
            () -> deleteCommand.execute(store, ui, storage));
    }

    @Test
    public void execute_doesNotDelete_whenModuleHasTasks() throws DuchessException {
        Module module = new Module("CS1231", "Module");
        store.getModuleList().add(module);
        assertTrue(store.getModuleList().size() == 1);

        Task todo = new Todo("Test");
        todo.setModule(module);
        store.getTaskList().add(todo);

        Command deleteCommand = new DeleteModuleCommand(1);
        deleteCommand.execute(store, ui, storage);
        assertTrue(store.getModuleList().size() == 1);
    }
}