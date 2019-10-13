package duke.logic.command.product;

import duke.model.product.Product;

import java.util.Optional;

public class ProductCommandUtil {

     static Product createNewProduct(Product toEdit, ProductDescriptor productDescriptor) {
        assert toEdit != null;

        Optional<String> a = productDescriptor.getProductName();
        String aa = toEdit.getProductName();
        String newProductName = productDescriptor.getProductName().orElse(toEdit.getProductName());
        Optional<Double> d = productDescriptor.getRetailPrice();
        Double dd = toEdit.getRetailPrice();
        Double newRetailPrice = productDescriptor.getRetailPrice().orElse(toEdit.getRetailPrice());

         Optional<Double> c = productDescriptor.getIngredientCost();
         Double cc = toEdit.getIngredientCost();
         //if (productDescriptor.getIngredientCost() )
        Double newIngredientCost =
                productDescriptor.getIngredientCost().orElse(toEdit.getIngredientCost());
        Product.Status newStatus = productDescriptor.getStatus().orElse(toEdit.getStatus());
        return new Product(newProductName, newRetailPrice, newIngredientCost, newStatus);
    }
}
