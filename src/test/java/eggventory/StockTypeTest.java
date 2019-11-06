package eggventory;

import eggventory.commons.enums.StockProperty;
import eggventory.commons.exceptions.BadInputException;
import eggventory.model.items.Stock;
import eggventory.model.items.StockType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class StockTypeTest {

    /*
    @Test
    void getStockList() {
        ArrayList<Stock> testList = new ArrayList<>();
        testList.add(new Stock("Resistor", "R50", 500, "Test Resistor"));
        StockType testStockType = new StockType("Resistor");
        testStockType.addStock("Resistor", "R50", 500, "Test Resistor");
        assertEquals(testList, testStockType.getStockList());
    }
    */

    //@@author Deculsion
    @Test
    void getQuantity() throws BadInputException {
        StockType testStockType = new StockType("Resistor");
        assertEquals(0,testStockType.getQuantity());
        testStockType.addStock("Resistor", "R50", 500, "Test Resistor");
        assertEquals(1,testStockType.getQuantity());
    }

    //@@author patwaririshab
    @Test
    void addStock_Success() throws BadInputException {
        StockType testStockType = new StockType("Resistor");
        assertTrue(testStockType.addStock("Resistor", "R50", 500,"Test Resistor"));
    }

    @Test
    void getStock() throws BadInputException {
        StockType testStockType = new StockType("Resistor");
        testStockType.addStock("Resistor", "R50", 500, "Test Resistor");
        assertEquals("Test Resistor", testStockType.getStock(0).getDescription());
    }

    @Test
    void deleteStock() throws BadInputException {
        ArrayList<Stock> testList = new ArrayList<>();
        testList.add(new Stock("Resistor", "R50", 500, "Test Resistor"));
        StockType testStockType = new StockType("Resistor");
        testStockType.addStock("Resistor", "R50", 500, "Test Resistor");
        assertEquals(1,testStockType.getQuantity());
        testStockType.deleteStock("R50");
        assertEquals(0,testStockType.getQuantity());
    }

    @Test
    void setStock_StockCode_Success() throws BadInputException {
        StockType testStockType = new StockType("testList");
        testStockType.addStock("testList", "R50", 500, "Test Resistor");
        testStockType.setStock("R50", StockProperty.STOCKCODE, "R500");
        assertEquals("R500",testStockType.getStock("R500").getStockCode());
        assertEquals(null, testStockType.getStock("R50"));
    }

    @Test
    void setStock_Quantity_Success() throws BadInputException {
        StockType testStockType = new StockType("testList");
        testStockType.addStock("testList", "R50", 500, "Test Resistor");
        testStockType.setStock("R50", StockProperty.QUANTITY, "1950");
        assertEquals(1950, testStockType.getStock("R50").getQuantity());
    }

    @Test
    void setStock_Loaned_Success() {

    }

    @Test
    void setStock_Lost_Success() {

    }

    @Test
    void setStock_Description_Success() {

    }

    @Test
    void setStock_Minimum_Success() {

    }
}