package eggventory.logic;

import eggventory.commons.exceptions.BadInputException;
import eggventory.model.StockList;
import eggventory.model.items.Stock;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

//@@author cyanoei
class QuantityManagerTest {

    QuantityManager quantityManager = new QuantityManager();

    Stock stock1;
    Stock stock2;

    StockList list = new StockList();

    QuantityManagerTest() throws BadInputException {

        stock1 = new Stock("testStockType", "A123", 100, "test desc");
        stock1.setMinimum(100);
        stock2 = new Stock("testStockType", "A234", 200, "test desc");
        stock2.setMinimum(100);

        list.addStock("Uncategorised", "A1", 200, "test desc");
        list.addStock("Uncategorised", "A2", 400, "test desc");
        list.addStock("Uncategorised", "A3", 100, "test desc");
        list.addStock("Uncategorised", "A4", 2, "test desc");
    }

    @Test
    void quantityCheck_ExactQuantity_Success() {
        assertEquals(QuantityManager.checkMinimum(stock1), "");
    }

    @Test
    void quantityCheck_QuantitySufficient_Success() {
        assertEquals(QuantityManager.checkMinimum(stock2), "");
    }

    @Test
    void quantityCheck_QuantityInsufficient_OutputMessage() throws BadInputException {
        stock1.setMinimum(101);
        assertEquals(QuantityManager.checkMinimum(stock1),
                String.format("\nThe stock \"%s\" is currently below minimum quantity. "
                        + "Available quantity: %d. Minimum quantity: %d.", "test desc", 100, 101));

        stock1.setMinimum(500);
        assertEquals(QuantityManager.checkMinimum(stock1),
                String.format("\nThe stock \"%s\" is currently below minimum quantity. "
                        + "Available quantity: %d. Minimum quantity: %d.", "test desc", 100, 500));
    }

    @Test
    void getLessThanMinimumList_TestInput_Success() throws BadInputException {
        assertNotNull(quantityManager.getLessThanMinimumList(list));
        assertTrue(quantityManager.getLessThanMinimumList(list).size() == 0);

        list.get(0).getStock("A1").setMinimum(100);
        assertNotNull(quantityManager.getLessThanMinimumList(list));
        assertTrue(quantityManager.getLessThanMinimumList(list).size() == 0);

        list.get(0).getStock("A1").setMinimum(210);
        assertNotNull(quantityManager.getLessThanMinimumList(list));
        assertTrue(quantityManager.getLessThanMinimumList(list).size() == 1);

        list.get(0).getStock("A2").setMinimum(500);
        assertNotNull(quantityManager.getLessThanMinimumList(list));
        assertTrue(quantityManager.getLessThanMinimumList(list).size() == 2);

        list.get(0).getStock("A4").setMinimum(3);
        assertNotNull(quantityManager.getLessThanMinimumList(list));
        assertTrue(quantityManager.getLessThanMinimumList(list).size() == 3);
    }
}
