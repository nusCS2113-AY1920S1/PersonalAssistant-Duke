package eggventory.logic.commands.find;

import eggventory.commons.enums.CommandType;
import eggventory.commons.exceptions.BadInputException;
import eggventory.stubs.StorageStub;
import eggventory.stubs.UiStub;
import eggventory.ui.Ui;
import eggventory.model.StockList;
import eggventory.storage.Storage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author yanprosobo
class FindDescriptionCommandTest {
    StockList testStockList = new StockList();
    Ui testCli = new UiStub();
    Storage testStorage = new StorageStub();

    //Test for search returning no result.
    @Test
    public void testExecuteFindDescription_UnmatchedInput_Success() throws BadInputException {
        String search = "search";
        testStockList.addStockType("TestType");
        testStockList.addStock("TestType", "#T", 1, "Test query");

        String expected = "Sorry, I could not find any stock containing the description \""
                        + search + "\".\nPlease try a different search string.";
        String output = new FindDescriptionCommand(CommandType.FIND, search)
                .execute(testStockList, testCli, testStorage);
        assertEquals(expected, output);
    }

    //Integration test with add stocktype and add stock. Using "search" as the word to be searching for.
    @Test
    public void testExecuteFindDescription_MatchedInput_Success() throws BadInputException {
        String search = "search";
        testStockList.addStockType("TestType");
        testStockList.addStock("TestType", "#T", 1, "Test search");
        testStockList.addStock("TestType", "#T2", 10, "Test query");
        testStockList.addStock("TestType", "#T3", 100, "Test search query");

        String expected = "1. TestType | #T | 1 | Test search\n"
                        + "2. TestType | #T3 | 100 | Test search query\n";
        String output = new FindDescriptionCommand(CommandType.FIND, search)
                .execute(testStockList, testCli, testStorage);
        assertEquals(expected, output);
    }
}
//@@author