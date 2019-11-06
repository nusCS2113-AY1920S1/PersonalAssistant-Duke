package duchess.commands;

import duchess.exceptions.DuchessException;
import duchess.logic.commands.AddModuleCommand;
import duchess.model.Module;
import duchess.storage.Storage;
import duchess.storage.Store;
import duchess.ui.Ui;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class AddModuleCommandTest {
    private final Storage storage = new Storage("data.json");
    private final Store store = new Store();
    private final Ui ui = new Ui();

    @Test
    public void validCommand_executes() throws DuchessException {
        AddModuleCommand validCommand = new AddModuleCommand("Math", "MA1511");
        validCommand.execute(store, ui, storage);
        Module addedModule = store.getModuleList().get(0);
        assertTrue(addedModule.getCode().equalsIgnoreCase("MA1511"));
        assertTrue(addedModule.getName().equals("Math"));
    }
}