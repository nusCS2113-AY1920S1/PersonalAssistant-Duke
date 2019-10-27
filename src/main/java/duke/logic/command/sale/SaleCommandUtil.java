package duke.logic.command.sale;

import duke.model.sale.Sale;

import java.util.Date;

public class SaleCommandUtil {
    /**
     * Modifies the {@code original} sale based on {@code saleDescriptor}.
     */
    static Sale modifySale(Sale original, SaleDescriptor saleDescriptor) {

        assert original != null;

        String newDescription = saleDescriptor.getDescription().orElse(original.getDescription());
        Date newDate = saleDescriptor.getSaleDate().orElse(original.getSaleDate());
        double newValue = saleDescriptor.getValue().orElse(original.getValue());
        boolean newIsSpend = saleDescriptor.isSpend().orElse(original.isSpend());
        String newRemarks = saleDescriptor.getRemarks().orElse(original.getRemarks());
        Sale sale = new Sale(newDescription, newValue, newIsSpend, newDate, newRemarks);
        return sale;
    }
}