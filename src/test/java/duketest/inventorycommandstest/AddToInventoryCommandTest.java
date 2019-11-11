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

public class AddToInventoryCommandTest {

    private Ui ui;
    private MainWindow mainWindow;
    private InventoryStorage inventoryStorage;
    private InventoryList inventoryList;

    @Test
    public void testAddToInventory() throws ParseException {
        ui = new Ui(mainWindow);
        inventoryStorage = new InventoryStorage(filePathInventoryTest);
        inventoryList = new InventoryList(inventoryStorage.load());

        ArrayList<String> arrayListExpectedOutput = new ArrayList<>();
        String expected = "Got it. I've added this ingredient to the inventory.\n" +
                "       testingredient\n" +
                "Now you have 3 ingredient(s) in your inventory";
        arrayListExpectedOutput.add(expected);

        AddToInventoryCommand addToInventoryCommand = new AddToInventoryCommand("addtoinventory testingredient q/ 1 u/kg");
        ArrayList<String> arrayListActualOutput = new ArrayList<>(addToInventoryCommand.execute(inventoryList, ui, inventoryStorage));

        assertEquals(arrayListExpectedOutput, arrayListActualOutput);
        DeleteFromInventoryCommand deleteFromInventoryCommand = new DeleteFromInventoryCommand("deleterecipe testingredient");
        deleteFromInventoryCommand.execute(inventoryList, ui, inventoryStorage);
    }
}
