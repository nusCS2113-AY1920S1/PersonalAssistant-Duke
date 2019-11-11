package duketest.inventorycommandstest;

import duke.logic.command.inventorycommands.ListInventoryCommand;
import duke.model.list.inventorylist.InventoryList;
import duke.storage.InventoryStorage;
import duke.ui.MainWindow;
import duke.ui.Ui;
import org.junit.jupiter.api.Test;

import static duke.common.InventoryMessages.MESSAGE_HERE_ARE_THE_INGREDIENTS;
import static duke.common.Messages.filePathInventoryTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

public class ListInventoryCommandTest {

    private Ui ui;
    private MainWindow mainWindow;
    private InventoryStorage inventoryStorage;
    private InventoryList inventoryList;

    @Test
    public void testListInventoryCommand() {
        ui = new Ui(mainWindow);
        inventoryStorage = new InventoryStorage(filePathInventoryTest);
        inventoryList = new InventoryList(inventoryStorage.load());

        ArrayList<String> arrayListExpectedOutput = new ArrayList<>();
        arrayListExpectedOutput.add(MESSAGE_HERE_ARE_THE_INGREDIENTS);
        arrayListExpectedOutput.add("     1. salt [1.0 | KG | No additional information.] ");
        arrayListExpectedOutput.add("     2. sugar [5.0 | KG | No additional information.] ");

        ListInventoryCommand listInventoryCommand = new ListInventoryCommand("listinventory");
        ArrayList<String> arrayListActualOutput = new ArrayList<>(listInventoryCommand.execute(inventoryList, ui, inventoryStorage));

        assertEquals(arrayListExpectedOutput, arrayListActualOutput);
    }
}
