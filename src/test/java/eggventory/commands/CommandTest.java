package eggventory.commands;

import eggventory.StockList;
import eggventory.items.StockType;
import eggventory.Storage;
import eggventory.Ui;
import eggventory.enums.CommandType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommandTest {
    StockList newList = new StockList();
    Ui ui = new Ui();
    Storage storage = new Storage("");

    @Test
    void getTypeDefault() {
        assertEquals(CommandType.BAD, new Command().getType());
    }

    @Test
    void getType() {
        assertEquals(CommandType.TODO, new Command(CommandType.TODO).getType());
        assertEquals(CommandType.DEADLINE, new Command(CommandType.DEADLINE).getType());
        assertEquals(CommandType.REMINDER, new Command(CommandType.REMINDER).getType());
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

        new Command(CommandType.LIST).execute(newList, ui, storage);
        assertEquals("The list is currently empty.\n", os.toString());
    }

    @Test
    void execute_printList_success() throws BadInputException {
        OutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);
        System.setOut(ps);

        new AddCommand(CommandType.TODO, "Test TODO", "").execute(newList, ui, storage);
        new Command(CommandType.LIST).execute(newList, ui, storage);
        assertEquals("1. [T] [X] Test TODO\n", os.toString());
    }*/
}
