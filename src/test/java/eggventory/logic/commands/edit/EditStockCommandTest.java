package eggventory.logic.commands.edit;

import eggventory.commons.enums.CommandType;
import eggventory.commons.enums.StockProperty;
import eggventory.commons.exceptions.BadInputException;
import eggventory.model.StockList;
import eggventory.storage.Storage;
import eggventory.stubs.UiStub;
import eggventory.ui.Ui;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

//@@author patwaririshab
class EditStockCommandTest {
    StockList testStockList = new StockList();
    Ui testCli = new UiStub();
    Storage testStorage = new Storage("","");

    //@@author cyanoei
    @Test
    void testExecuteEditStockCode_RepeatedStockCode_ThrowsBadInputException() throws BadInputException {
        testStockList.addStockType("TestType");
        testStockList.addStock("TestType", "#T", 100, "Test");
        testStockList.addStock("TestType2", "#T2", 100, "Test");

        Exception exception = assertThrows(BadInputException.class, () -> new EditStockCommand(CommandType.EDIT,
                "#T2", StockProperty.STOCKCODE, "#T").execute(testStockList,testCli,testStorage));
        assertEquals(String.format("Sorry, the stock code \"%s\" is already assigned to a stock in the system. "
                + "Please enter a different stock code.", "#T"), exception.getMessage());
    }

    //@@author patwaririshab
    @Test
    void testExecuteEditStockCode_ValidStockCode_Success() throws BadInputException {
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
    void testExecuteEditStockQuantity_Success() throws BadInputException {
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
    void testExecuteEditStockDescription_Success() throws BadInputException {
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