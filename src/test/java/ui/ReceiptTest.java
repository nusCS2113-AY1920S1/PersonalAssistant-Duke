package ui;

import org.junit.jupiter.api.Test;
import storage.wallet.Receipt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReceiptTest {

    @Test
    void addTagTest() {
        Receipt receipt = new Receipt(5.00);
        assertTrue(receipt.containsTag("Expenses"));

        receipt.addTag("Fire");
        assertFalse(receipt.areTagsEmpty());
        assertEquals("Fire", receipt.getTags().get(1));

        receipt.addTag("abc");
        assertEquals("Fire", receipt.getTags().get(1));
        assertEquals("abc", receipt.getTags().get(2));
    }
}
