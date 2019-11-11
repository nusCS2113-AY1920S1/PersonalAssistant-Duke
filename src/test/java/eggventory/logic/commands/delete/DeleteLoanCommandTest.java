package eggventory.logic.commands.delete;

import eggventory.commons.enums.CommandType;
import eggventory.commons.exceptions.BadInputException;
import eggventory.logic.commands.Command;
import eggventory.logic.commands.add.AddLoanCommand;
import eggventory.logic.commands.add.AddPersonCommand;
import eggventory.logic.commands.add.AddStockCommand;
import eggventory.model.LoanList;
import eggventory.model.StockList;
import eggventory.storage.Storage;
import eggventory.stubs.StorageStub;
import eggventory.ui.Cli;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author patwaririshab
class DeleteLoanCommandTest {
    private StockList testStockList = new StockList();
    private Cli testCli = new Cli();
    private Storage testStorage = new StorageStub();

    @Test
    void executeDeleteLoan_MissingLoan_ThrowsBadInputException() {
        Command testCommand = new DeleteLoanCommand(CommandType.DELETE, "ABC123",
                "T1");
        try {
            testCommand.execute(testStockList, testCli, testStorage);
        } catch (BadInputException e) {
            assertEquals(BadInputException.class, e.getClass());
            assertEquals("Sorry, there is no loan of T1 to ABC123. ",e.getMessage());
        }
    }

    @Test
    void executeDeleteLoan_ValidLoan_ReturnsConfirmationString() throws BadInputException {
        new AddStockCommand(CommandType.ADD, "Uncat", "T1", 1000, "Test",
                0).execute(testStockList, testCli, testStorage);
        new AddPersonCommand(CommandType.ADD, "ABC123", "TESTP1").execute(testStockList, testCli,
                testStorage);
        new AddLoanCommand(CommandType.ADD, "ABC123", "T1", 100).execute(testStockList, testCli,
                testStorage);

        Command testCommand = new DeleteLoanCommand(CommandType.DELETE, "ABC123", "T1");
        String output = testCommand.execute(testStockList, testCli, testStorage);
        assertEquals("Nice, I have deleted this loan for you: \nMatricNo: ABC123 | Stock: T1 | Quantity: 100",
                output);
    }
}
//@@author