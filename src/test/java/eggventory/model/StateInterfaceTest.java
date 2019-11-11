package eggventory.model;

import eggventory.commons.enums.CommandType;
import eggventory.commons.exceptions.BadInputException;
import eggventory.logic.commands.Command;
import eggventory.logic.commands.add.AddPersonCommand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StateInterfaceTest {
    StateInterface testInterface;

    @BeforeEach
    public void setup() {
        StockList testStockList = new StockList();
        LoanList testLoanList = new LoanList();
        PersonList testPersonList = new PersonList();
        TemplateList testTemplateList = new TemplateList();
        testInterface = new StateInterface(testStockList, testLoanList, testPersonList, testTemplateList);
    }

    @Test
    void executeUndoCommand_EmptyHistoryList_ThrowsBadInputException() throws BadInputException {
        Command testCommand =  new AddPersonCommand(CommandType.ADD, "ABC123",
                "Test Validation Person");
        testCommand.updateState(testInterface);
        //Latest state in historylist
        assertFalse(testInterface.getHistoryList().isEmpty());
        assertDoesNotThrow(() -> testInterface.executeUndoCommand());
        //Latest state in futurelist
        assertTrue(testInterface.getHistoryList().isEmpty());
        assertThrows(BadInputException.class, () -> testInterface.executeUndoCommand());
    }

    @Test
    void executeRedoCommand_EmptyFutureList_ThrowsBadInputException() throws BadInputException {
        Command testCommand =  new AddPersonCommand(CommandType.ADD, "ABC123",
                "Test Validation Person");
        testCommand.updateState(testInterface);
        //Latest state in historylist, no state in futurelist yet
        assertFalse(testInterface.getHistoryList().isEmpty());
        assertTrue(testInterface.getFutureList().isEmpty());
        assertThrows(BadInputException.class, () -> testInterface.executeRedoCommand());
    }

}