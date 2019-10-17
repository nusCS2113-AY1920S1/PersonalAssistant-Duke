package eggventory.commands;

import eggventory.StockList;
import eggventory.items.StockType;
import eggventory.Storage;
import eggventory.Ui;
import eggventory.enums.CommandType;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddCommandTest {
    //    private OutputStream os = new ByteArrayOutputStream();
    //    private PrintStream ps = new PrintStream(os);
    private StockList testStockList = new StockList();
    private Ui testUi = new Ui();
    private Storage testStorage = new Storage("");
    private String indent = "        ";

    @Test
    void execute() {
        //        System.setOut(ps);
        //                new AddCommand(CommandType.ADD, "testStockType", "t0000", 100,
        //                        "testDescription").execute( testStockType, testUi, testStorage);
        //        assertEquals(indent + "____________________________________________________________\n"
        //                + indent + "I have added the following stock to your list:\n"
        //                + indent + "testStockType | t0000 | 100 | testDescription\n"
        //                + indent + "____________________________________________________________\n", os.toString());
    }

    @Test
    void testExecute_AddStock_Success() {
        StockList testList = new StockList();
        testList.addStockType("testStockType");
        String output = new AddCommand(CommandType.ADD, "testStockType", "t0000", 100,
                "testDescription").execute(testList, testUi, testStorage);

        //Check whether a stock is added to the list correctly
        assertEquals("testStockType", testList.getStockType("testStockType").getName());
        //Check whether the

        //Check whether execute sends correct message to print
        assertEquals(String.format("Nice! I have successfully added the stock: StockType: %s StockCode: %s "
                + "Quantity: %d Description: %s","testStockType", "t0000", 100, "testDescription"), output);

    }
}
