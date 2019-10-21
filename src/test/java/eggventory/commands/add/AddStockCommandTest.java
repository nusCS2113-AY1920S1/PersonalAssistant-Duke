package eggventory.commands.add;

import eggventory.StockList;
import eggventory.Storage;
import eggventory.ui.Cli;
import eggventory.enums.CommandType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddStockCommandTest {
    //    private OutputStream os = new ByteArrayOutputStream();
    //    private PrintStream ps = new PrintStream(os);
    private StockList testStockList = new StockList();
    private Cli testCli = new Cli();
    private Storage testStorage = new Storage("");
    private String indent = "        ";

    @Test
    void execute() {
        //        System.setOut(ps);
        //                new AddCommand(CommandType.ADD, "testStockType", "t0000", 100,
        //                        "testDescription").execute( testStockType, testCli, testStorage);
        //        assertEquals(indent + "____________________________________________________________\n"
        //                + indent + "I have added the following stock to your list:\n"
        //                + indent + "testStockType | t0000 | 100 | testDescription\n"
        //                + indent + "____________________________________________________________\n", os.toString());
    }

    @Test
    void testExecute_AddStock_Success() {
        StockList testList = new StockList();
        testList.addStockType("testStockType");
        String output = new AddStockCommand(CommandType.ADD, "testStockType", "t0000", 100,
                "testDescription").execute(testList, testCli, testStorage);

        //Check whether a stock is added to the list correctly
        assertEquals("testStockType", testList.getStockType("testStockType").getName());
        //Check whether the

        //Check whether execute sends correct message to print
        assertEquals(String.format("Nice! I have successfully added the stock: StockType: %s StockCode: %s "
                + "Quantity: %d Description: %s","testStockType", "t0000", 100, "testDescription"), output);

    }

    //@@author cyanoei
    @Test
    void testExecute_AddDuplicateStock_Error() {
        StockList testList = new StockList();

        String testStockType = "Uncategorised";
        String testStockCode = "R5";
        int testQuantity = 1000;
        String testDescription = "A resistor";

        //Add an existing stock to the list.
        testList.addStock(testStockType, testStockCode, testQuantity, testDescription);

        //Add another stock with the same stock code. The other attributes can be different.
        String output = new AddStockCommand(CommandType.ADD, testStockType, testStockCode, 10,
                "Another resistor").execute(testList, testCli, testStorage);

        //Check whether execute prints the error message to CLI.
        assertEquals(String.format("Sorry, the stock code \"%s\" is already assigned to a stock in the system. "
                + "Please enter a different stock code.", testStockCode), output);
    }
    //@@author
}
