package ui;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class IncomeReceiptTest {

    @Test
    void generalTest() {
        IncomeReceipt receipt = new IncomeReceipt(5.00);
        assertEquals(-5.00, receipt.getCashSpent());
        assertEquals(5.00, receipt.getCashGained());
        receipt.setCashSpent(27.00);
        assertEquals(27.00, receipt.getCashSpent());
        assertEquals(-27.00, receipt.getCashGained());
        receipt.setCashGained(19.00);
        assertEquals(-19.00, receipt.getCashSpent());
        assertEquals(19.00, receipt.getCashGained());

        Receipt receipt1 = new IncomeReceipt(7.00);
        assertEquals(-7.00, receipt1.getCashSpent());
        receipt1.setCashSpent(-9.00);
        assertEquals(-9.00, receipt1.getCashSpent());
    }
}
