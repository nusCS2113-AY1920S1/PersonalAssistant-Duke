package eggventory.model;

import eggventory.commons.exceptions.BadInputException;
import eggventory.model.loans.Loan;
import org.junit.jupiter.api.Test;



class StateInterfaceTest {
    StockList testStockList = new StockList();

    @Test
    void pushStateHistoryList_ValidInput_ReturnsValidStringWhenPopped() throws BadInputException {

    }

    private void assertEquals(String s, String popStockSave) {
    }

    @Test
    void pushStateFutureList() {
    }

    @Test
    void executeUndoCommand() {
    }

    @Test
    void executeRedoCommand() {
    }

    @Test
    void updateStateHistory() {
    }
}