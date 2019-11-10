/**
 * Testing for ProfitStorage utilities
 *
 * @author kuromono
 */

package cube.storage;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProfitStorageTest {

    /**
     * Test Getters/Setters for ProfitStorage
     */
    @Test
    public void profit_storage_test() {
        double testValue = 888.88;
        ProfitStorage.setAnnualProfit(testValue);
        ProfitStorage.setAnnualRevenue(testValue);

        assertEquals(ProfitStorage.getAnnualProfit(), testValue);
        assertEquals(ProfitStorage.getAnnualRevenue(), testValue);
    }
}
