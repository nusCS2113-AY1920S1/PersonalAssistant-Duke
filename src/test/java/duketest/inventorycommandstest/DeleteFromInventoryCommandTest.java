package duketest.inventorycommandstest;

import duke.logic.command.inventorycommands.AddToInventoryCommand;
import duke.logic.command.inventorycommands.DeleteFromInventoryCommand;
import duke.model.list.inventorylist.InventoryList;
import duke.storage.InventoryStorage;
import duke.ui.MainWindow;
import duke.ui.Ui;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.ArrayList;

import static duke.common.Messages.filePathInventoryTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeleteFromInventoryCommandTest {

    private Ui ui;
    private MainWindow mainWindow;
    private InventoryStorage inventoryStorage;
    private InventoryList inventoryList;

    @Test
    public void testDeleteFromInventoryCommand() throws ParseException {
        ui = new Ui(mainWindow);
        inventoryStorage = new InventoryStorage(filePathInventoryTest);
        inventoryList = new InventoryList(inventoryStorage.load());

        ArrayList<String> arrayListExpectedOutput = new ArrayList<>();
        String expected = "Noted. I've removed this ingredient from the inventory:\n" +
                "       testingredient\n" +
                "Now you have 2 ingredients(s) in the inventory.";
        arrayListExpectedOutput.add(expected);

        AddToInventoryCommand addInventoryCommand = new AddToInventoryCommand("addtoinventory testingredient q/1 u/kg");
        addInventoryCommand.execute(inventoryList, ui, inventoryStorage);

        DeleteFromInventoryCommand deleteFromInventoryCommand = new DeleteFromInventoryCommand("deleteFromInventory testingredient");
        ArrayList<String> arrayListActualOutput = new ArrayList<>(deleteFromInventoryCommand.execute(inventoryList, ui, inventoryStorage));
        assertEquals(arrayListExpectedOutput, arrayListActualOutput);
    }
}
