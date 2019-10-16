package ui;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

public class WalletTest {

    @Test
    void addReceiptTest() {
        Wallet wallet = new Wallet();
        assertEquals(true, wallet.isReceiptsEmpty());

        Receipt receiptA = new Receipt(5.00);
        wallet.addReceipt(receiptA);
        assertEquals(false, wallet.isReceiptsEmpty());
        assertEquals(receiptA, wallet.getReceipts().get(0));
        assertEquals(5.00, wallet.getTotalExpenses());

        Date date = new Date();
        Receipt receiptB = new Receipt(12.00, date);
        wallet.addReceipt(receiptB);
        assertEquals(receiptA, wallet.getReceipts().get(0));
        assertEquals(receiptB, wallet.getReceipts().get(1));
        assertEquals(17.00, wallet.getTotalExpenses());

    }
}
