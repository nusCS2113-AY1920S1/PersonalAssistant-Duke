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
        assertEquals(wallet.getReceipts().get(0), receiptA);

        Date date = new Date();
        Receipt receiptB = new Receipt(12.00, date);
        wallet.addReceipt(receiptB);
        assertEquals(wallet.getReceipts().get(0), receiptA);
        assertEquals(wallet.getReceipts().get(1), receiptB);

    }
}
