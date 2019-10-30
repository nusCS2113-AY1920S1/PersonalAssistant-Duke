package eggventory.logic.commands.edit;

import eggventory.commons.enums.CommandType;
import eggventory.commons.enums.StockProperty;
import eggventory.model.StockList;
import eggventory.model.items.Stock;
import eggventory.storage.Storage;
import eggventory.stubs.UiStub;
import eggventory.ui.Ui;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author patwaririshab
class EditStockCommandTest {
    StockList testStockList = new StockList();
    Ui testCli = new UiStub();
    Storage testStorage = new Storage("","");

    @Test
    void testExecuteEditStockCode_RepeatedStockCode_ReturnsErrorMessage() {
        testStockList.addStockType("TestType");
        testStockList.addStock("TestType", "#T", 100, "Test");
        testStockList.addStock("TestType2", "#T2", 100, "Test");
        String expected = String.format("Sorry, the stock code \"%s\" is already assigned to a stock in the system. "
                + "Please enter a different stock code.", "#T");
        String output = new EditStockCommand(CommandType.EDIT, "#T2", StockProperty.STOCKCODE,
                "#T").execute(testStockList,testCli,testStorage);
        assertEquals(expected, output);
    }

    @Test
    void testExecuteEditStockCode_ValidStockCode_Success() {
        testStockList.addStockType("TestType");
        testStockList.addStock("TestType", "#T", 100, "Test");
        String expected = "Awesome! I have successfully updated the following stock:\n"
                + "stocktype: TestType\n"
                + "stockcode: #TNEW\n"
                + "quantity: 100\n"
                + "description: Test\n"
                + "minimum: 0\n"
                + "lost: 0\n"
                + "loaned: 0\n"
                + "available: 100";
        String output = new EditStockCommand(CommandType.EDIT, "#T", StockProperty.STOCKCODE,
                "#TNEW").execute(testStockList,testCli,testStorage);
        assertEquals(expected,output);
    }

    @Test
    void testExecuteEditStockQuantity_Success() {
        testStockList.addStockType("TestType");
        testStockList.addStock("TestType", "#T", 100, "Test");
        String expected = "Awesome! I have successfully updated the following stock:\n"
                + "stocktype: TestType\n"
                + "stockcode: #T\n"
                + "quantity: 5000\n"
                + "description: Test\n"
                + "minimum: 0\n"
                + "lost: 0\n"
                + "loaned: 0\n"
                + "available: 5000";
        String output = new EditStockCommand(CommandType.EDIT, "#T", StockProperty.QUANTITY,
                "5000").execute(testStockList,testCli,testStorage);
        assertEquals(expected,output);
    }

    @Test
    void testExecuteEditStockDescription_Success() {
        testStockList.addStockType("TestType");
        testStockList.addStock("TestType", "#T", 100, "Test");
        String expected = "Awesome! I have successfully updated the following stock:\n"
                + "stocktype: TestType\n"
                + "stockcode: #T\n"
                + "quantity: 100\n"
                + "description: Test NEW\n"
                + "minimum: 0\n"
                + "lost: 0\n"
                + "loaned: 0\n"
                + "available: 100";
        String output = new EditStockCommand(CommandType.EDIT, "#T", StockProperty.DESCRIPTION,
                "Test NEW").execute(testStockList,testCli,testStorage);
        assertEquals(expected,output);
    }

    @Test
    void testExecuteEditStockMinimum_Success(){

    }

    @Test
    void testExecuteEditStockLoaned_Success(){

    }

    @Test
    void testExecuteEditStockLost_Success(){

    }


}