package eggventory.logic.commands;

import eggventory.model.StockList;
import eggventory.storage.Storage;
import eggventory.commons.enums.CommandType;
import eggventory.logic.commands.ByeCommand;
import eggventory.stubs.StorageStub;
import eggventory.ui.Cli;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author patwaririshab
class CommandTest {
    StockList newList = new StockList();
    Cli cli = new Cli();
    Storage storage = new StorageStub();

    @Test
    void getType() {
        //assertEquals(CommandType.DONE, new Command(CommandType.DONE).getType());
        //assertEquals(CommandType.LIST, new ListCommand(CommandType.LIST, null).getType());
        assertEquals(CommandType.BYE, new ByeCommand(CommandType.BYE).getType());
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
    //@@author
}
