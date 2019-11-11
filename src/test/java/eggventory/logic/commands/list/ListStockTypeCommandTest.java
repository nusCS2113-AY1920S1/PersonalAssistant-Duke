package eggventory.logic.commands.list;

import eggventory.commons.enums.CommandType;
import eggventory.commons.exceptions.BadInputException;
import eggventory.stubs.StorageStub;
import eggventory.stubs.UiStub;
import eggventory.ui.Ui;
import eggventory.model.StockList;
import eggventory.storage.Storage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author yanprosobo

class ListStockTypeCommandTest {
    private StockList testStockList = new StockList();
    private Ui testCli = new UiStub();
    private Storage testStorage = new StorageStub();

    @BeforeEach
    void resetTestStockList() {
        if (testStockList.isEmpty()) {
            return;
        } else {
            testStockList.clearList();
        }
    }

    @Test
    void execute_InvalidStocktype_ThrowsBadInputException() throws BadInputException {
        String invalid = "InvalidType";
        String valid = "ValidType";

        testStockList.addStockType(valid);

        assertDoesNotThrow(() -> new ListStockTypeCommand(CommandType.LIST, valid)
                .execute(testStockList, testCli, testStorage));
        assertThrows(BadInputException.class, () -> new ListStockTypeCommand(CommandType.LIST, invalid)
                .execute(testStockList, testCli, testStorage));
    }

    @Test
    void execute_ValidStockTypeWithStocks_Success() throws BadInputException {
        String validWithStocks = "ValidType";
        String validWithoutStocks = "AlsoValidType";

        testStockList.addStockType(validWithStocks);
        testStockList.addStockType(validWithoutStocks);
        testStockList.addStock(validWithStocks, "#Test1", 1, "Test description");

        assertDoesNotThrow(() -> new ListStockTypeCommand(CommandType.LIST, validWithStocks)
                .execute(testStockList, testCli, testStorage));
        assertDoesNotThrow(() -> new ListStockTypeCommand(CommandType.LIST, validWithoutStocks)
                .execute(testStockList, testCli, testStorage));
        String output = new ListStockTypeCommand(CommandType.LIST, validWithStocks)
                .execute(testStockList, testCli, testStorage);
        String expected = validWithStocks
                + " INVENTORY\n"
                + "------------------------\n"
                + "1. " + validWithStocks + " | #Test1 | 1 | Test description\n"
                + "\n";
        assertEquals(expected, output);
    }

    @Test
    void execute_ValidZeroQuantiyStocktype_Success() throws BadInputException {
        String zeroQuantityStockType = "Zero";
        String someQuantityStockType = "Some";

        testStockList.addStockType(zeroQuantityStockType);
        testStockList.addStockType(someQuantityStockType);
        testStockList.addStock(someQuantityStockType, "#Test1", 1, "Test description");

        String output = new ListStockTypeCommand(CommandType.LIST, zeroQuantityStockType)
                .execute(testStockList, testCli, testStorage);
        String expected = "There is currently 0 stock with that stocktype.";
        assertEquals(expected, output);
    }
}
//@@author yanprosobo