package eggventory.logic.commands.edit;

import eggventory.commons.enums.CommandType;
import eggventory.commons.exceptions.DuplicateEntryException;
import eggventory.model.StockList;
import eggventory.storage.Storage;
import eggventory.stubs.StorageStub;
import eggventory.stubs.UiStub;
import eggventory.ui.Ui;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

//@@author patwaririshab
class EditStockTypeCommandTest {
    StockList testStockList = new StockList();
    Ui testUi = new UiStub();
    Storage testStorage = new StorageStub();

    @Test
    public void testExecuteEditStockType_RepeatedStockType_ThrowsDuplicateEntryException() {
        String testNameA = "TestType1";
        String testNameB = "TestType2";
        testStockList.addStockType(testNameA);
        testStockList.addStockType(testNameB);
        assertTrue(testStockList.isExistingStockType(testNameA));
        assertTrue(testStockList.isExistingStockType(testNameB));
        EditStockTypeCommand cmd = new EditStockTypeCommand(CommandType.EDIT,
                testNameB, testNameA);
        assertThrows(DuplicateEntryException.class,() -> cmd.execute(testStockList, testUi, testStorage));
    }
    //@@author
}