package eggventory.logic.commands;

import eggventory.commons.enums.CommandType;
import eggventory.model.StockList;
import eggventory.storage.Storage;
import eggventory.stubs.StorageStub;
import eggventory.stubs.UiStub;
import eggventory.ui.Ui;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

//@@author cyanoei
class ByeCommandTest {

    private StockList testStockList = new StockList();
    private Ui testCli = new UiStub();
    private Storage testStorage = new StorageStub();

    @Test
    void testByeCommand_CommandExecute_ReturnsNull() {
        Assertions.assertNull(new ByeCommand(CommandType.BYE).execute(testStockList, testCli, testStorage));
    }

    @Test
    void testByeCommand_CommandExecuteSaveMore_ReturnsNull() {
        Assertions.assertNull(new ByeCommand(CommandType.BYE).executeSaveMoreLists(testStockList, testCli, testStorage,
                testStorage.loadLoanList(), testStorage.loadPersonList(), testStorage.loadTemplateList()));
    }
    //@@author
}