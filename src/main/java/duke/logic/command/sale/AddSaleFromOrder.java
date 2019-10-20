package duke.logic.command.sale;

import java.util.Date;

import duke.model.ModelManager;
import duke.model.sale.Sale;

/**
 * This class is used to create a Sale entry from completing an Order.
 */
public class AddSaleFromOrder {

    /**
     *
     * @param orderValue is total revenue from a completed Order
     * @param orderCompleteDate is the date of completion of the Order
     * @param orderRemarks contains a brief mention of all the items sold in a completed Order
     */
    public AddSaleFromOrder(double orderValue, Date orderCompleteDate, String orderRemarks) {

        String description = "Order completed.";
        Sale sale = new Sale(description, orderValue, orderCompleteDate, orderRemarks);


    }

}
