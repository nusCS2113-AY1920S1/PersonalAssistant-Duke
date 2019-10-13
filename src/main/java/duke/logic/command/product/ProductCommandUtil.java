package duke.logic.command.product;

import duke.model.product.Product;

public class ProductCommandUtil {

    /**
     * Creates a new Product from a productDescriptor.
     * @param toEdit the product to be edited.
     * @param productDescriptor contains the information to edit with.
     * @return edited product.
     */
    public static Product createNewProduct(Product toEdit, ProductDescriptor productDescriptor) {
        assert toEdit != null;

        String newProductName = productDescriptor.getProductName().orElse(toEdit.getProductName());
        Double newRetailPrice = productDescriptor.getRetailPrice().orElse(toEdit.getRetailPrice());
        Double newIngredientCost =
                productDescriptor.getIngredientCost().orElse(toEdit.getIngredientCost());
        Product.Status newStatus = productDescriptor.getStatus().orElse(toEdit.getStatus());
        return new Product(newProductName, newRetailPrice, newIngredientCost, newStatus);
    }
}
