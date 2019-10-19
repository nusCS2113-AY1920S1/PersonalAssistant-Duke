package duke.storage.decrpted;

import duke.model.product.Product;

import java.util.ArrayList;
import java.util.List;

public class RecipeList extends ArrayList<Product> {
    private List<Product> productList = new ArrayList<Product>();

    public List<Product> getProductList() {
        return productList;
    }
}
