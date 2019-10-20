package duke.logic.command.sale;

import duke.model.sale.Sale;

import java.util.Date;

/**
 * This class is used to create a Sale entry from purchasing ingredients.
 */
public class AddSaleFromShopping {

    /**
     *
     * @param purchaseValue is the cost of all goods purchased.
     * @param purchaseDate is the date of purchasing these goods.
     * @param purchaseRemarks is the list of goods purchased.
     */
    public AddSaleFromShopping(double purchaseValue, Date purchaseDate, String purchaseRemarks) {

        String description = "Order completed.";
        Sale sale = new Sale(description, purchaseValue, purchaseDate, purchaseRemarks);


    }
}
