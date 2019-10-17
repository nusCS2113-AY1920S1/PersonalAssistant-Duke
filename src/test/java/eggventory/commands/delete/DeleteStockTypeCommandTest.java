package eggventory.commands.delete;

import eggventory.StockList;
import eggventory.Storage;
import eggventory.Ui;
import eggventory.enums.CommandType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeleteStockTypeCommandTest {

    private StockList testStockList = new StockList();
    private Ui testUi = new Ui();
    private Storage testStorage = new Storage("");

    @Test
    void testExecute_deleteStocktypeNotFound_errorMessage() {

        String output = new DeleteStockTypeCommand(CommandType.DELETE, "nonexistentStockType")
                .execute(testStockList, testUi, testStorage);

        assertEquals("Sorry, I cannot find the stock type \"nonexistentStockType\" refers to. "
                + "Please try again.", output);
    }

    @Test
    void testExecute_deleteUncategorised_errorMessage() {

        String output = new DeleteStockTypeCommand(CommandType.DELETE, "Uncategorised")
                .execute(testStockList, testUi, testStorage);

        assertEquals("Sorry, Uncategorised is the default category, and cannot be deleted.", output);
    }

    @Test
    void testExecute_deleteStockType_success() {

        String testStockType = "Resistor";
        String testStockCode = "R5";
        int testQuantity = 1000;
        String testDescription = "A resistor";

        testStockList.addStockType(testStockType);
        testStockList.addStock(testStockType, testStockCode, testQuantity, testDescription);

        String output = new DeleteStockTypeCommand(CommandType.DELETE, testStockType)
                .execute(testStockList, testUi, testStorage);

        assertEquals(String.format("I deleted the following stockType: %s. "
                + "I also deleted the following stocks of that type: \n" + "1. %s | %s | %d | %s\n",
                testStockType, testStockType, testStockCode, testQuantity, testDescription), output);
    }

}
