package eggventory.commands.delete;

import eggventory.StockList;
import eggventory.Storage;
import eggventory.enums.CommandType;
import eggventory.ui.Cli;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeleteStockCommandTest {

    private StockList testStockList = new StockList();
    private Cli testCli = new Cli();
    private Storage testStorage = new Storage("");
    private String indent = "        ";

    @Test
    void testExecute_DeleteStock_Success() {
        StockList testList = new StockList();

        String testStockType = "Uncategorised";
        String testStockCode = "R5";
        int testQuantity = 1000;
        String testDescription = "A resistor";


        testList.addStock(testStockType, testStockCode, testQuantity, testDescription);

        String output = new DeleteStockCommand(CommandType.DELETE, testStockCode)
                .execute(testList, testCli, testStorage);

        assertEquals(String.format("I deleted the following stock: StockType: %s StockCode: %s Quantity: %d "
                        + "Description: %s", testStockType, testStockCode,
                testQuantity, testDescription), output);
    }

    @Test
    void testExecute_DeleteNonexistentStock_Error() {
        StockList testList = new StockList();

        String testStockType = "Uncategorised";
        String testStockCode = "R5";
        int testQuantity = 1000;
        String testDescription = "A resistor";

        String output = new DeleteStockCommand(CommandType.DELETE, testStockCode)
                .execute(testList, testCli, testStorage);

        assertEquals(String.format("Sorry, I cannot find the stock that stock code \"%s\" refers to. "
                + "Please try again.", testStockCode), output);
    }
}
