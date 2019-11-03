package duke.testutil;

import duke.model.BakingHome;
import duke.model.product.Product;

import java.util.List;

public class BakingHomeBuilder {

    private BakingHome bakingHome;

    public BakingHomeBuilder() {
        bakingHome = new BakingHome();
    }

    public BakingHomeBuilder(BakingHome bakingHome) {
        this.bakingHome = bakingHome;
    }

    /**
     * Adds 4 lists to the BakingHome that we are building.
     */
    public BakingHomeBuilder withItems(Product product) {
        bakingHome.addProduct(product);
        //add init helper functions
        return this;
    }

    public BakingHome build() {
        return bakingHome;
    }

    /**
     * Returns a BakingHome with typical entity.
     * @return
     */
    public static BakingHome getTypicalBakingHome() {
        BakingHome bakingHome = new BakingHome();
        for (Product product : TypicalProducts.getTypicalProducts()) {
            bakingHome.addProduct(product);
        }
        return bakingHome;
    }
}
