//@author tygq13
package cube.storage;

import cube.model.sale.SalesHistory;

public class SaleStorage {
    private SalesHistory salesHistory;

    /**
     * Default constructor for SalesStorage
     */
    public SaleStorage() {
        this.salesHistory = new SalesHistory();
    }

    /**
     * Gets the sales history in this storage.
     * @return the sales history.
     */
    public SalesHistory getSalesHistory() {
        return salesHistory;
    }

    /**
     * Updates the SalesHistory in this storage.
     * @param salesHistory The list of sales record.
     */
    public void storeSalesHistory(SalesHistory salesHistory) {
        this.salesHistory = salesHistory;
    }
}