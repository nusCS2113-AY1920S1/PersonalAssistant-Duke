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
    private OutputStream os = new ByteArrayOutputStream();
    private PrintStream ps = new PrintStream(os);
    private StockType testStockType = new StockType("testStockType");
    private Ui testUi = new Ui();
    private Storage testStorage = new Storage("");
    private String indent = "        ";

    @Test
    void execute() {
        //        System.setOut(ps);
        //        new AddCommand(CommandType.ADD, "testStockType", "t0000", 100,
        //                "testDescription").execute(testStockType, testUi, testStorage);
        //        assertEquals(indent + "____________________________________________________________\n"
        //                + indent + "I have added the following stock to your list:\n"
        //                + indent + "testStockType | t0000 | 100 | testDescription\n"
        //                + indent + "____________________________________________________________\n", os.toString());
    }

    @Test
    void testExecute() {
        StockList testList = new StockList();
        new AddCommand(CommandType.ADD, "testStockType", "t0000", 100,
                "testDescription").execute(testList);
        assertEquals("testDescription",testList.getList().get(0).getStock(0).getDescription());
    }
}
