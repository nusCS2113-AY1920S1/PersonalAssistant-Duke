package eggventory.commands.add;

import eggventory.StockList;
import eggventory.Storage;
import eggventory.ui.Cli;
import eggventory.enums.CommandType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AddStockTypeCommandTest {

    private StockList testStockList = new StockList();
    private Cli testCli = new Cli();
    private Storage testStorage = new Storage("");

    @Test
    void testExecute_AddStocktypeSuccess() {
        StockList testList = new StockList();
        testList.addStockType("testStockType");

        String output = new AddStockTypeCommand(CommandType.ADD, "testStockType")
                .execute(testList, testCli, testStorage);

        assertEquals("Nice! I have successfully added the stocktype: testStockType", output);
    }
}