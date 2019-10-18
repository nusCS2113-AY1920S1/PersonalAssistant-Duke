package duke.logic.command.product;

import duke.model.product.IngredientItemList;
import duke.model.product.Product;

import java.util.Objects;
import java.util.Optional;

/** Stores the details to edit the product with. Each non-empty field value will replace the previous
 * field value of the product.
 */
public class ProductDescriptor {
    private String productName;
    private IngredientItemList ingredientItemList;
    private Double ingredientCost;
    private Double retailPrice;
    private Product.Status status;

    public ProductDescriptor() {
    }

    /**
     * Copy constructor.
     *
     * @param toCopy the ProductDescriptor to copy from
     */
    public ProductDescriptor(ProductDescriptor toCopy) {
        setProductName(toCopy.productName);
        setIngredientCost(toCopy.ingredientCost);
        setRetailPrice(toCopy.retailPrice);
        System.out.println(toCopy.retailPrice);
        setStatus(toCopy.status);
    }

    public void setProductName(String newProductName) {
        this.productName = newProductName;
    }

    public void setIngredientCost(Double newIngredientCost) {
        this.ingredientCost = newIngredientCost;
    }

    public void setRetailPrice(Double newRetailPrice) {
        this.retailPrice = newRetailPrice;
    }

    public void setStatus(Product.Status newStatus) {
        this.status = newStatus;
    }

    public Optional<String> getProductName() {
        return Optional.ofNullable(productName);
    }

    public Optional<Double> getIngredientCost() {
        return Optional.ofNullable(ingredientCost);
    }

    public Optional<Double> getRetailPrice() {
        return Optional.ofNullable(retailPrice);
    }

    public Optional<Product.Status> getStatus() {
        return Optional.ofNullable(status);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductDescriptor that = (ProductDescriptor) o;
        return Objects.equals(productName, that.productName)
                //&& Objects.equals(items, that.items)
                && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName, status);
    }
}