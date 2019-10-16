package ui;

import duke.exception.DukeException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ReceiptTrackerTest {

    private ReceiptTracker initializeTracker() {
        ReceiptTracker tracker = new ReceiptTracker();

        Receipt receiptA = new Receipt(5.00);
        receiptA.addTag("loans");
        tracker.addReceipt(receiptA);

        Receipt receiptB = new Receipt(4.00);
        receiptB.addTag("loans");
        receiptB.addTag("ice");
        tracker.addReceipt(receiptB);

        Receipt receiptC = new IncomeReceipt(9.00);
        receiptC.addTag("ice");
        tracker.addReceipt(receiptC);

        Receipt receiptD = new Receipt(1.00);
        tracker.addReceipt(receiptD);
        return tracker;
    }

    @Test
    void constructorTest() {
        ReceiptTracker trackerA = new ReceiptTracker();
        assertEquals(0.0, trackerA.getTotalCashSpent());
        assertTrue(trackerA.isEmpty());
        assertTrue(trackerA.getFolders().isEmpty());

        ArrayList<Receipt> receiptList = new ArrayList<>();
        ReceiptTracker trackerB = new ReceiptTracker(receiptList);
        assertEquals(0.0, trackerB.getTotalCashSpent());
        assertTrue(trackerB.isEmpty());
        assertTrue(trackerB.getFolders().isEmpty());

        Receipt receipt = new Receipt(5.0);
        receiptList.add(receipt);
        ReceiptTracker trackerC = new ReceiptTracker(receiptList);
        assertFalse(trackerC.isEmpty());
        assertEquals(5.00, trackerC.getTotalCashSpent());
        assertEquals(5.00, trackerC.get(0).getCashSpent());
    }

    @Test
    void sumReceiptsTest() {
        ReceiptTracker tracker = new ReceiptTracker();
        for (double x = 1.0; x < 11.0; ++x) {
            Receipt receipt = new Receipt(x);
            tracker.add(receipt);
        }
        assertEquals(0.0, tracker.getTotalCashSpent());
        assertEquals(55.0, tracker.sumReceipts());
        tracker.updateTotalCashSpent();
        assertEquals(55.0, tracker.getTotalCashSpent());
    }

    @Test
    void addReceiptTest() {
        ReceiptTracker tracker = new ReceiptTracker();
        Receipt receiptA = new Receipt(5.00);
        tracker.addReceipt(receiptA);
        assertEquals(5.00, tracker.getTotalCashSpent());

        tracker.getFolders().put("tagged", new ReceiptTracker());
        Receipt receiptB = new Receipt(10.00);
        receiptB.getTags().add("tagged");
        tracker.addReceipt(receiptB);
        assertEquals(15.00, tracker.getTotalCashSpent());
        assertEquals(10.00, tracker.getFolders().get("tagged").getTotalCashSpent());
        assertTrue(tracker.getFolders().get("tagged").getFolders().isEmpty());
    }

    @Test
    void findReceiptsByTagTest() {
        ReceiptTracker tracker = initializeTracker();
        assertEquals(1.00, tracker.getTotalCashSpent());
        assertEquals(5.00, tracker.findReceiptsByTag("loans").get(0).getCashSpent());
        assertEquals(4.00, tracker.findReceiptsByTag("loans").get(1).getCashSpent());
        assertEquals(4.00, tracker.findReceiptsByTag("ice").get(0).getCashSpent());
        assertEquals(-9.00, tracker.findReceiptsByTag("ice").get(1).getCashSpent());
    }

    @Test
    void addFolderTest() {
        ReceiptTracker tracker = new ReceiptTracker();
        for (double x = 1.0; x < 6.0; ++x) {
            Receipt receipt = new Receipt(x);
            tracker.addReceipt(receipt);
        }
        ArrayList<String> tags = new ArrayList<>();
        tags.add("tagged");
        tags.add("Fire");
        for (double x = 6.0; x < 11.0; ++x) {
            Receipt taggedReceipt = new Receipt(x, null, tags);
            tracker.addReceipt(taggedReceipt);
        }
        assertTrue(tracker.getFolders().isEmpty());
        assertFalse(tracker.isRegisteredTag("tagged"));
        assertFalse(tracker.isRegisteredTag("Fire"));

        try {
            tracker.addFolder("tagged");
            assertFalse(tracker.getFolders().isEmpty());
            assertTrue(tracker.isRegisteredTag("tagged"));
            assertEquals(40.0, tracker.getFolders().get("tagged").getTotalCashSpent());
            assertFalse(tracker.isRegisteredTag("Fire"));
        } catch (DukeException e) {
            Ui.dukeSays(e.toString());
        }

        try {
            tracker.addFolder("fire");
            assertFalse(tracker.getFolders().isEmpty());
            assertTrue(tracker.isRegisteredTag("fire"));
            assertFalse(tracker.isRegisteredTag("Fire"));
            assertEquals(0.0, tracker.getFolders().get("fire").getTotalCashSpent());
        } catch (DukeException e) {
            Ui.dukeSays(e.toString());
        }

        try {
            tracker.addFolder("fire");
        } catch (DukeException e) {
            Ui.dukeSays(e.toString());
        }
    }

    @Test
    void getCashSpentByTagTest() {
        ReceiptTracker tracker = initializeTracker();
        assertTrue(tracker.getFolders().isEmpty());
        assertEquals(9.00, tracker.getCashSpentByTag("loans"));

        try {
            tracker.addFolder("ice");
        } catch (DukeException e) {
            Ui.dukeSays(e.toString());
        }
        assertEquals(-5.00, tracker.getCashSpentByTag("ice"));
        assertFalse(tracker.isRegisteredTag("loans"));
        assertTrue(tracker.isRegisteredTag("ice"));

        assertEquals(-5.00, tracker.getCashSpentByTag("ice"));
    }
}
