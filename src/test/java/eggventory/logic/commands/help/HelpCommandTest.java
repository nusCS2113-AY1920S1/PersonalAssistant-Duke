package eggventory.logic.commands;

import eggventory.stubs.StorageStub;
import eggventory.stubs.UiStub;
import eggventory.storage.Storage;
import eggventory.ui.Ui;
import eggventory.model.StockList;
import eggventory.commons.enums.CommandType;
import eggventory.commons.exceptions.BadInputException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class HelpCommandTest {
    StockList testStockList = new StockList();
    Ui testCli = new UiStub();
    Storage testStorage = new StorageStub();

    //test for "help x" command where x is not a valid command.
    @Test
    public void testExecuteHelpCommand_InvalidCommandName_ThrowsBadInputException() throws BadInputException {
        Exception exception = assertThrows(BadInputException.class, () -> new HelpCommand(CommandType.HELP, "invalid")
                                .execute(testStockList,testCli,testStorage));
        assertEquals("Your help command is not defined. Please enter 'help' for reference.", exception.getMessage());
    }

    @Test
    public void testExecuteHelpCommand_HelpBye_Success() throws BadInputException {
        String expected = "Exits EggVentory and bids user goodbye.\n"
                + "\n"
                + "bye:        bye"
                + "\n";
        String output = new HelpCommand(CommandType.HELP, "bye").execute(testStockList, testCli, testStorage);
        assertEquals(expected, output);
    }
}