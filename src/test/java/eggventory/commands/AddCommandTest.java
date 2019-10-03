package eggventory.commands;

import eggventory.StockType;
import eggventory.Storage;
import eggventory.Ui;
import eggventory.enums.CommandType;
import eggventory.exceptions.BadInputException;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddCommandTest {
    private OutputStream os = new ByteArrayOutputStream();
    private PrintStream ps = new PrintStream(os);
    private StockType testStockType = new StockType();
    private Ui testUi = new Ui();
    private Storage testStorage = new Storage("");
    private String indent = "        ";

    @Test
    void execute() {
        System.setOut(ps);
        new AddCommand(CommandType.ADD, "testStockType", "t0000", 100,
                "testDescription").execute(testStockType, testUi, testStorage);
        assertEquals(indent + "____________________________________________________________\n"
                + indent + "I have added the following stock to your list:\n"
                + indent + "testStockType | t0000 | 100 | testDescription\n"
                + indent + "____________________________________________________________\n", os.toString());
    }

    @Test
    void testExecute() {
        StockType testList = new StockType();
        new AddCommand(CommandType.ADD, "testStockType", "t0000", 100,
                "testDescription").execute(testList);
        assertEquals("testDescription",testList.getStock(0).getDescription());

    }

    /*
    @Test
    public void execute_addTodoTask_success() throws BadInputException {
        StockType newList = new StockType();
        new AddCommand(CommandType.TODO, "Test TODO", "").execute(newList);
        assertEquals("Test TODO", newList.getStock(0).getDescription());
    }

    @Test
    public void execute_addDeadlineTask_success() throws BadInputException {
        StockType newList = new StockType();
        new AddCommand(CommandType.DEADLINE, "Test DEADLINE", "15/12/2019 1500").execute(newList);
        SimpleDateFormat formatted = new SimpleDateFormat("EEE MMM d yyyy K:mm a");
        String startDate = formatted.format(newList.getStock(0).getDate().getTime());
        assertEquals("Test DEADLINE", newList.getStock(0).getDescription());
        assertEquals("Sun Dec 15 2019 3:00 PM", startDate);
    }

    @Test
    public void execute_addEventTask_success() throws BadInputException {
        StockType newList = new StockType();
        new AddCommand(CommandType.EVENT, "Test EVENT", "15/12/2019 1500 to 17/12/2019 1500").execute(newList);
        SimpleDateFormat formatted = new SimpleDateFormat("EEE MMM d yyyy K:mm a");
        String startDate = formatted.format(newList.getStock(0).getDate().getTime());
        String endDate = formatted.format(newList.getStock(0).getEndDate().getTime());
        assertEquals("Test EVENT", newList.getStock(0).getDescription());
        assertEquals("Sun Dec 15 2019 3:00 PM", startDate);
        assertEquals("Tue Dec 17 2019 3:00 PM", endDate);
    }
    */
}
