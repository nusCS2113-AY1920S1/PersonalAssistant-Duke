/**
 * This class supports sale-relevent methods for storage.
 */
package cube.storage;

import cube.model.sale.SalesHistory;

public class SaleStorage {
    private SalesHistory salesHistory;

    public SaleStorage() {
        this.salesHistory = new SalesHistory();
    }

    public SalesHistory getSalesHistory() {
        return salesHistory;
    }

    public void storeSalesHistory(SalesHistory salesHistory) {
        this.salesHistory = salesHistory;
    }
}