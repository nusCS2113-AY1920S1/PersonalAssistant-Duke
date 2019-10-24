package eggventory.commands;

import eggventory.ui.Cli;
import eggventory.StockList;
import eggventory.Storage;

import static org.junit.jupiter.api.Assertions.assertEquals;



class FindCommandTest {
    Cli cli = new Cli();
    Storage storage = new Storage("");
    StockList stockList = new StockList();

    /*
    @Test
    public void execute_Task_success() {
        OutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);
        System.setOut(ps);
        stockType.addItem(TaskType.TODO, "Test FIND");
        new FindCommand(CommandType.FIND, "FIND").execute(stockType, cli, storage);
        assertEquals("1. [T] " + stockType.getTask(0).getStatusIcon() + "Test FIND", os.toString().trim());
    }*/
}
