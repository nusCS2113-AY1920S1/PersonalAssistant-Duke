package eggventory.logic;

import eggventory.commons.exceptions.BadInputException;
import eggventory.model.items.Stock;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author cyanoei
class QuantityManagerTest {

    Stock stock1;
    Stock stock2;

    QuantityManagerTest() throws BadInputException {

        stock1 = new Stock("testStockType", "A123", 100, "test desc");
        stock1.setMinimum(100);
        stock2 = new Stock("testStockType", "A234", 200, "test desc");
        stock2.setMinimum(100);
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

}
