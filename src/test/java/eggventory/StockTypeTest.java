package eggventory;

import eggventory.items.Stock;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class StockTypeTest {

    @Test
    void getStockList() {
        ArrayList<Stock> testList = new ArrayList<>();
        testList.add(new Stock("Resistor", "R50", 500, "Test Resistor"));
        StockType testStockType = new StockType(testList);
        assertEquals(testList, testStockType.getStockList());
    }

    @Test
    void getSize() {
        StockType testStockType = new StockType();
        assertEquals(0,testStockType.getSize());
        testStockType.addStock("Resistor", "R50", 500, "Test Resistor");
        assertEquals(1,testStockType.getSize());
    }

    @Test
    void addStock() {
        StockType testStockType = new StockType();
        assertTrue(testStockType.addStock("Resistor", "R50", 500,
                "Test Resistor"));

    }

    @Test
    void getStock() {
        StockType testStockType = new StockType();
        testStockType.addStock("Resistor", "R50", 500, "Test Resistor");
        assertEquals("Test Resistor", testStockType.getStock(0).getDescription());
    }

    @Test
    void deleteStock() {
        ArrayList<Stock> testList = new ArrayList<>();
        testList.add(new Stock("Resistor", "R50", 500, "Test Resistor"));
        StockType testStockType = new StockType(testList);
        assertEquals(1,testStockType.getSize());
        testStockType.deleteStock(0);
        assertEquals(0,testStockType.getSize());
    }
}