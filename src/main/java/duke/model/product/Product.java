package duke.model.product;

import duke.commons.util.TestUtil;
import duke.logic.message.ProductMessageUtils;

import java.util.Objects;

import static duke.commons.util.AppUtil.checkEmpty;
import static duke.commons.util.CollectionUtil.requireAllNonNull;

public class Product {

    public static final Double DEFAULT_RETAIL_PRICE = 0.0;
    public static final Double DEFAULT_INGREDIENT_COST = 0.0;
    public static final Status DEFAULT_STATUS = Status.ACTIVE;

    public enum Status {
        ACTIVE,
        ARCHIVE
    }

    private String productName;
    private IngredientItemList ingredients;
    private Double ingredientCost;
    private Double retailPrice;
    private Status status;


    /** Constructor for ProductParserUtil*/
    public Product(String productName) {
        this.productName = productName;
        this.ingredients = new IngredientItemList();
        this.ingredientCost = DEFAULT_INGREDIENT_COST;
        this.retailPrice = DEFAULT_RETAIL_PRICE;
        this.status = DEFAULT_STATUS;
    }

    /**
     * Creates a Product with all field given.
     * This is only allowed in creating product for EditProductCommand as it provides status in the arguments.
     */
    public Product(String productName, Double retailPrice, Double ingredientCost,
                   IngredientItemList ingredientItemList, Product.Status status) {
        requireAllNonNull(productName);
        checkEmpty(productName, ProductMessageUtils.MESSAGE_MISSING_PRODUCT_NAME);
        this.productName = productName;
        this.ingredientCost = ingredientCost;
        this.retailPrice = retailPrice;
        this.status = status;
        this.ingredients = ingredientItemList;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductName() {
        return productName;
    }

    public Double getIngredientCost() {
        return ingredientCost;
    }

    public void setIngredientCost(double ingredientCost) {
        this.ingredientCost = ingredientCost;
    }

    public Double getRetailPrice() {
        return retailPrice;
    }

    public Double getProfit() {
        return retailPrice - ingredientCost;
    }

    public void setRetailPrice(double retailPrice) {
        this.retailPrice = retailPrice;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public IngredientItemList getIngredients() {
        return ingredients;
    }

    public void setIngredients(IngredientItemList ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return "{" +
                "Product Name: " + productName + "," + System.lineSeparator() +
                "Retail Price: $" + retailPrice  + "," + System.lineSeparator() +
                "Cost: $" + ingredientCost + "," + System.lineSeparator() +
                "Ingredients: " + ingredients.toString() + System.lineSeparator() +
                "Status: " + status +
                "}";
    }

    /**
     * Checks if two products have the same information for all fields
     * using toString() function which prints out all fields.
     * @param p another product
     * @return true if the provided product has the same information for all fields
     */
    public boolean hasSameInfo(Product p) {
        TestUtil.printToFile(toString() + System.lineSeparator() +"/////////////////////" + System.lineSeparator() + p.toString());
        return toString().equals(p.toString());
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        return productName.equals(product.getProductName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName);
    }
}

