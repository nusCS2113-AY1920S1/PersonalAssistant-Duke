package eggventory.commands.delete;

import eggventory.model.StockList;
import eggventory.storage.Storage;
import eggventory.commons.enums.CommandType;
import eggventory.commons.exceptions.BadInputException;
import eggventory.logic.commands.delete.DeleteStockCommand;
import eggventory.ui.Cli;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DeleteStockCommandTest {
    StockList testStockList = new StockList();
    Cli testCli = new Cli();
    Storage testStorage = new Storage("");
    String testStockType;
    String testStockCode;
    int testQuantity;
    String testDescription;

    //@@author patwaririshab
    @Test
    void testExecuteDeleteStock_validStock_success() throws BadInputException {
        testStockType = "Uncategorised";
        testStockCode = "R5";
        testQuantity = 1000;
        testDescription = "A resistor";
        testStockList.addStock(testStockType, testStockCode, testQuantity, testDescription);
        String output = new DeleteStockCommand(CommandType.DELETE, testStockCode)
                .execute(testStockList, testCli, testStorage);
        assertEquals(String.format("I deleted the following stock: StockType: %s StockCode: %s Quantity: %d "
                        + "Description: %s", testStockType, testStockCode,
                testQuantity, testDescription), output);
    }

    //@@author patwaririshab
    @Test
    void testExecuteDeleteStock_nonexistentStock_throwsBadInputException() throws BadInputException {
        testStockType = "Uncategorised";
        testStockCode = "R5";
        testQuantity = 1000;
        testDescription = "A resistor";
        Exception exception = assertThrows(BadInputException.class, () -> new DeleteStockCommand(CommandType.DELETE,
                testStockCode).execute(testStockList, testCli, testStorage));
        assertEquals(String.format("Sorry, I cannot find the stock that stock code \"%s\" refers to. "
                + "Please try again.", testStockCode), exception.getMessage());
    }
}
