package eggventory.logic.commands.list;

import eggventory.commons.enums.CommandType;
import eggventory.commons.exceptions.BadInputException;
import eggventory.model.StockList;
import eggventory.storage.Storage;
import eggventory.stubs.StorageStub;
import eggventory.stubs.UiStub;
import eggventory.ui.Ui;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ListStockCommandTest {

    private String testStockCode = "TestCode";
    private String testStockType = "TestType";
    private int testQuantity = 1;
    private String testDescription = "TestDesc";


    private StockList testStockList = new StockList();
    private Ui testCli = new UiStub();
    private Storage testStorage = new StorageStub();

    @Test
    void execute_StockListHasItem_ReturnsListString() throws BadInputException {
        String expectedOutput = "CURRENT INVENTORY\n"
                + "------------------------\n"
                + "1. " + testStockType + " | " + testStockCode + " | "
                + testQuantity + " | " + testDescription + "\n\n";

        testStockList.addStockType(testStockType);
        testStockList.addStock(testStockType, testStockCode, testQuantity, testDescription);

        ListStockCommand commandUnderTest = new ListStockCommand(CommandType.LIST);
        assertEquals(expectedOutput, commandUnderTest.execute(testStockList, testCli, testStorage));
    }

    @Test
    void execute_StockListEmpty_ReturnsListEmptyString() {
        String expectedOutput = "The list is currently empty.";
        ListStockCommand commandUnderTest = new ListStockCommand(CommandType.LIST);

        assertEquals(expectedOutput, commandUnderTest.execute(testStockList, testCli, testStorage));
    }
}