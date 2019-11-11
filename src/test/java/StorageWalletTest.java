import duke.exception.DukeException;
import storage.StorageWallet;
import storage.wallet.Wallet;
import storage.wallet.Receipt;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class StorageWalletTest {

    @Test
    void loadData() {
        StorageWallet storageWallet = new StorageWallet("testWalletDataLoad.txt");
        Wallet wallet = new Wallet();
        try {
            storageWallet.loadData(wallet);
        } catch (DukeException e) {
            System.out.println(e.getMessage());
        }

        //In 5.00 /date 2019-01-29 /tags street
        Receipt firstReceipt = wallet.getReceipts().get(0);
        assertEquals(-5.00, firstReceipt.getCashSpent(),
                "Loaded wrong cashSpent");
        assertEquals("2019-08-30", firstReceipt.getDate().toString(),
                "Loaded wrong date");
        assertEquals("street", firstReceipt.getTags().get(1),
                "Loaded wrong tag");

        //out $10 /date 2001-08-25
        Receipt secondReceipt = wallet.getReceipts().get(1);
        assertEquals(10, secondReceipt.getCashSpent(),
                "Loaded wrong cashSpent");
        assertEquals("2001-08-25", secondReceipt.getDate().toString(),
                "Loaded wrong date");

        //Out $15.00 /date 2019-12-10 /tags dogfood puppy monthly
        Receipt thirdReceipt = wallet.getReceipts().get(2);
        assertEquals(15, thirdReceipt.getCashSpent(),
                "Loaded wrong cashSpent");
        assertEquals("2019-12-10", thirdReceipt.getDate().toString(),
                "Loaded wrong date");
        assertEquals("dogfood", thirdReceipt.getTags().get(1),
                "Loaded wrong tag");
        assertEquals("puppy", thirdReceipt.getTags().get(2),
                        "Loaded wrong tag");
        assertEquals("monthly", thirdReceipt.getTags().get(3),
                        "Loaded wrong tag");

        //in $3.00 /date 2018-03-14 /tags refund clothes
        Receipt fourthReceipt = wallet.getReceipts().get(3);
        assertEquals(-3.00, fourthReceipt.getCashSpent(),
                "Loaded wrong cashSpent");
        assertEquals("2018-03-14", fourthReceipt.getDate().toString(),
                "Loaded wrong date");
        assertEquals("refund", fourthReceipt.getTags().get(1),
                "Loaded wrong tag");
        assertEquals("clothes", fourthReceipt.getTags().get(2),
                        "Loaded wrong tag");
    }

    @Test
    void saveData() {
        // testDataLoad is the test Data
        // Follow the Storage Format when inputting new test cases
        StorageWallet storageExpected = new StorageWallet("testWalletDataLoad.txt");
        StorageWallet storageSaved = new StorageWallet("testWalletDataSave.txt");
        Wallet wallet = new Wallet();
        try {
            storageExpected.loadData(wallet);
        } catch (DukeException e) {
            System.out.println(e.getMessage());
        }
        // Check file content manually, as input may differ from standard format (Input: $5 Saved: $5.00)
        try {
            storageSaved.saveData(wallet);
        } catch (DukeException e) {
            System.out.println(e.toString());
        }
    }
}
