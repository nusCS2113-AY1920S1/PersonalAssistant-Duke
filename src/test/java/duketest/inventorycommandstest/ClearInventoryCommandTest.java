package duketest.inventorycommandstest;

import duke.logic.command.inventorycommands.AddToInventoryCommand;
import duke.logic.command.inventorycommands.ClearInventoryCommand;
import duke.model.list.inventorylist.InventoryList;
import duke.storage.InventoryStorage;
import duke.ui.MainWindow;
import duke.ui.Ui;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.ArrayList;

import static duke.common.Messages.filePathInventoryTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClearInventoryCommandTest {

    private Ui ui;
    private MainWindow mainWindow;
    private InventoryStorage inventoryStorage;
    private InventoryList inventoryList;

    @Test
    public void testClearInventoryCommand() throws ParseException {
        ui = new Ui(mainWindow);
        inventoryStorage = new InventoryStorage(filePathInventoryTest);
        inventoryList = new InventoryList(inventoryStorage.load());

        ArrayList<String> arrayListExpectedOutput = new ArrayList<>();
        String expected = "All ingredients in the inventory has been cleared. The inventory list is now empty.";
        arrayListExpectedOutput.add(expected);

        ClearInventoryCommand clearInventoryCommand = new ClearInventoryCommand("clearinventory");
        ArrayList<String> arrayListActualOutput = new ArrayList<>(clearInventoryCommand.execute(inventoryList, ui, inventoryStorage));
        assertEquals(arrayListExpectedOutput, arrayListActualOutput);

        AddToInventoryCommand addInventoryCommand = new AddToInventoryCommand("addtoinventory salt q/1 u/kg");
        addInventoryCommand.execute(inventoryList, ui, inventoryStorage);
        AddToInventoryCommand addInventoryCommand1 = new AddToInventoryCommand("addtoinventory sugar q/5 u/kg");
        addInventoryCommand1.execute(inventoryList, ui, inventoryStorage);
    }
}
