/**
 * This class supports sale-relevant methods for storage.
 *
 * @author tygq13
 */
package cube.storage;

import cube.model.sale.SalesHistory;

public class SaleStorage {
    private SalesHistory salesHistory;

    /**
     * Default constructor for SaleStorage.
     */
    public SaleStorage() {
        this.salesHistory = new SalesHistory();
    }

    /**
     * Getter for SalesHistory.
     * @return SalesHistory object containing previous sales histories.
     */
    public SalesHistory getSalesHistory() {
        return salesHistory;
    }

    /**
     * Setter for SalesHistory.
     * @param salesHistory object containing sales histories.
     */
    public void storeSalesHistory(SalesHistory salesHistory) {
        this.salesHistory = salesHistory;
    }
}