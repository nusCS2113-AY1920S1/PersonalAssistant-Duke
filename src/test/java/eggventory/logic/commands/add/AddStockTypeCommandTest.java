package eggventory.logic.commands.add;

import eggventory.model.StockList;
import eggventory.storage.Storage;
import eggventory.commons.exceptions.BadInputException;
import eggventory.logic.commands.add.AddStockTypeCommand;
import eggventory.stubs.StorageStub;
import eggventory.ui.Cli;
import eggventory.commons.enums.CommandType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

//@@author patwaririshab
class AddStockTypeCommandTest {

    private StockList testStockList = new StockList();
    private Cli testCli = new Cli();
    private Storage testStorage = new StorageStub();

    @Test
    void testExecuteAddStockType_ValidStockType_Succeeds() throws BadInputException {
        String output = new AddStockTypeCommand(CommandType.ADD, "testStockType")
                .execute(testStockList, testCli, testStorage);

        assertEquals("Nice! I have successfully added the stocktype: testStockType", output);
    }

    //@@author cyanoei
    @Test
    void testExecuteAddStockType_RepeatedStockType_ThrowsBadInputException() throws BadInputException {
        testStockList.addStockType("testStockType");
        Exception exception = assertThrows(BadInputException.class, () ->
            new AddStockTypeCommand(CommandType.ADD, "testStockType")
                    .execute(testStockList, testCli, testStorage)
        );
        assertEquals(String.format("Sorry, \"testStockType\" is already an existing stock type."),
                exception.getMessage());
    }
    //@@author

}