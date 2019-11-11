package ui;

import org.junit.jupiter.api.Test;
import storage.wallet.Receipt;
import storage.wallet.Wallet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

public class WalletTest {

    @Test
    void addReceiptTest() {
        Wallet wallet = new Wallet();
        assertEquals(true, wallet.isReceiptsEmpty());

        Receipt receiptA = new Receipt(5.00);
        wallet.addReceipt(receiptA);
        assertEquals(false, wallet.isReceiptsEmpty());
        assertEquals(receiptA, wallet.getReceipts().get(0));
        assertTrue(wallet.getReceipts().getFolders().containsKey("Expenses"));
        assertEquals(false, wallet.getReceipts().getFolders().get("Expenses").isEmpty());
        assertEquals(5.00, wallet.getTotalExpenses());

        LocalDate date = LocalDate.now();
        Receipt receiptB = new Receipt(12.00, date);
        wallet.addReceipt(receiptB);
        assertEquals(receiptA, wallet.getReceipts().get(0));
        assertEquals(receiptB, wallet.getReceipts().get(1));
        assertEquals(17.00, wallet.getTotalExpenses());

    }
}
