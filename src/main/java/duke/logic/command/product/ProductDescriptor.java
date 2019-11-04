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
        setIngredientItemList(toCopy.ingredientItemList);
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

    public void setIngredientItemList(IngredientItemList ingredientItemList) {
        this.ingredientItemList = ingredientItemList;
    }

    public Optional<String> getProductName() {
        return Optional.ofNullable(productName);
    }

    public Optional<Double> getIngredientCost() {
        return Optional.ofNullable(ingredientCost);
    }

    public Optional<IngredientItemList> getIngredientItemList() {
        return Optional.ofNullable(ingredientItemList);
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
        ProductDescriptor d = (ProductDescriptor) o;
        return getProductName().equals(d.getProductName())
                && getIngredientItemList().equals(d.getIngredientItemList())
                && getStatus().equals(d.getStatus())
                && getRetailPrice().equals(d.getRetailPrice())
                && getStatus().equals(d.getStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName, status);
    }
}