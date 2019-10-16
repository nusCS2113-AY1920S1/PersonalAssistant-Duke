package eggventory;

import eggventory.items.Stock;
import eggventory.items.StockType;
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

    @Test
    void getQuantity() {
        StockType testStockType = new StockType("Resistor");
        assertEquals(0,testStockType.getQuantity());
        testStockType.addStock("Resistor", "R50", 500, "Test Resistor");
        assertEquals(1,testStockType.getQuantity());
    }

    @Test
    void addStock() {
        StockType testStockType = new StockType("Resistor");
        assertTrue(testStockType.addStock("Resistor", "R50", 500,"Test Resistor"));

    }

    @Test
    void getStock() {
        StockType testStockType = new StockType("Resistor");
        testStockType.addStock("Resistor", "R50", 500, "Test Resistor");
        assertEquals("Test Resistor", testStockType.getStock(0).getDescription());
    }

    @Test
    void deleteStock() {
        ArrayList<Stock> testList = new ArrayList<>();
        testList.add(new Stock("Resistor", "R50", 500, "Test Resistor"));
        StockType testStockType = new StockType("Resistor");
        testStockType.addStock("Resistor", "R50", 500, "Test Resistor");
        assertEquals(1,testStockType.getQuantity());
        testStockType.deleteStock("R50");
        assertEquals(0,testStockType.getQuantity());
    }
}