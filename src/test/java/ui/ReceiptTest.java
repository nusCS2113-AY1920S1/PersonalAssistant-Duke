package ui;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReceiptTest {

    @Test
    void addTagTest() {
        Receipt receipt = new Receipt(5.00);
        assertEquals(true, receipt.areTagsEmpty());

        receipt.addTag("Fire");
        assertEquals(false, receipt.areTagsEmpty());
        assertEquals("Fire", receipt.getTags().get(0));

        receipt.addTag("abc");
        assertEquals("Fire", receipt.getTags().get(0));
        assertEquals("abc", receipt.getTags().get(1));
    }
}
