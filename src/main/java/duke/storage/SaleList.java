package duke.storage;

import duke.entities.Sale;

import java.util.ArrayList;
import java.util.List;

public class SaleList {

    private List<Sale> saleList = new ArrayList<>();

    public List<Sale> getSaleList() {
        return saleList;
    }

    public void setSaleList(List<Sale> saleList) {
        this.saleList = saleList;
    }
}