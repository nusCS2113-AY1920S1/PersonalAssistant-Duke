package eggventory.logic.commands.delete;

import eggventory.commons.enums.CommandType;
import eggventory.commons.exceptions.BadInputException;
import eggventory.model.StockList;
import eggventory.storage.Storage;
import eggventory.stubs.StorageStub;
import eggventory.ui.Cli;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author patwaririshab
public class DeleteStockTypeCommandTest {

    private StockList testStockList = new StockList();
    private Cli testCli = new Cli();
    private Storage testStorage = new StorageStub();

    @Test
    void testExecute_deleteStocktypeNotFound_errorMessage() {

        String output = new DeleteStockTypeCommand(CommandType.DELETE, "nonexistentStockType")
                .execute(testStockList, testCli, testStorage);

        assertEquals("Sorry, I cannot find the stock type \"nonexistentStockType\" refers to. "
                + "Please try again.", output);
    }

    @Test
    void testExecute_deleteUncategorised_errorMessage() {

        String output = new DeleteStockTypeCommand(CommandType.DELETE, "Uncategorised")
                .execute(testStockList, testCli, testStorage);

        assertEquals("Sorry, Uncategorised is the default category, and cannot be deleted.", output);
    }

    @Test
    void testExecute_deleteStockType_success() throws BadInputException {

        String testStockType = "Resistor";
        String testStockCode = "R5";
        int testQuantity = 1000;
        String testDescription = "A resistor";

        testStockList.addStockType(testStockType);
        testStockList.addStock(testStockType, testStockCode, testQuantity, testDescription);

        String output = new DeleteStockTypeCommand(CommandType.DELETE, testStockType)
                .execute(testStockList, testCli, testStorage);

        assertEquals(String.format("I deleted the following stockType: %s. "
                + "I also deleted the following stocks of that type: \n" + "1. %s | %s | %d | %s\n",
                testStockType, testStockType, testStockCode, testQuantity, testDescription), output);
    }
    //@@author
}
