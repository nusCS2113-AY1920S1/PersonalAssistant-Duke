package eggventory.commands;

import eggventory.ui.Cli;
import eggventory.StockList;
import eggventory.Storage;
import eggventory.enums.CommandType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommandTest {
    StockList newList = new StockList();
    Cli cli = new Cli();
    Storage storage = new Storage("");

    @Test
    void getTypeDefault() {
        assertEquals(CommandType.BAD, new Command().getType());
    }

    @Test
    void getType() {
        assertEquals(CommandType.DONE, new Command(CommandType.DONE).getType());
        assertEquals(CommandType.LIST, new Command(CommandType.LIST).getType());
        assertEquals(CommandType.BYE, new Command(CommandType.BYE).getType());
    }

    /*
    @Test
    void execute_printList_empty() throws BadInputException {
        OutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);
        System.setOut(ps);

        new Command(CommandType.LIST).execute(newList, cli, storage);
        assertEquals("The list is currently empty.\n", os.toString());
    }

    @Test
    void execute_printList_success() throws BadInputException {
        OutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);
        System.setOut(ps);

        new AddCommand(CommandType.TODO, "Test TODO", "").execute(newList, cli, storage);
        new Command(CommandType.LIST).execute(newList, cli, storage);
        assertEquals("1. [T] [X] Test TODO\n", os.toString());
    }*/
}
