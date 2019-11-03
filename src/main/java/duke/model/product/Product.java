package duke.model.product;

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


    public Product() {
        this.ingredients = new IngredientItemList();
        this.ingredientCost = DEFAULT_INGREDIENT_COST;
        this.retailPrice = DEFAULT_RETAIL_PRICE;
        this.status = DEFAULT_STATUS;
    }

    /** Constructor for Order parser.util*/
    public Product(String productName) {
        this.productName = productName;
    }

    /**
     * Creates a Product.
     */
    public Product(String productName, String retailPrice, String ingredientCost) {
        requireAllNonNull(productName);
        checkEmpty(productName, ProductMessageUtils.MESSAGE_MISSING_PRODUCT_NAME);

        try {
            this.productName = productName;
            this.ingredients = new IngredientItemList();
            this.ingredientCost = Double.parseDouble(ingredientCost);
            this.retailPrice = Double.parseDouble(retailPrice);
            this.status = Status.ACTIVE;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a Product.
     */
    public Product(String productName, String retailPrice, String ingredientCost, IngredientItemList ingredientItemList) {
        requireAllNonNull(productName);
        checkEmpty(productName, ProductMessageUtils.MESSAGE_MISSING_PRODUCT_NAME);

        try {
            this.productName = productName;
            this.ingredientCost = Double.parseDouble(ingredientCost);
            this.retailPrice = Double.parseDouble(retailPrice);
            this.status = Status.ACTIVE;
            this.ingredients = ingredientItemList;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a Product with all field given.
     */
    public Product(String productName, Double retailPrice, Double ingredientCost,
                   IngredientItemList ingredientItemList, Product.Status status) {
        requireAllNonNull(productName);
        checkEmpty(productName, ProductMessageUtils.MESSAGE_MISSING_PRODUCT_NAME);

        try {
            this.productName = productName;
            this.ingredientCost = ingredientCost;
            this.retailPrice = retailPrice;
            this.status = status;
            this.ingredients = ingredientItemList;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    /** Constructor for edit Product */
    public Product(String productName, Double retailPrice, Double ingredientCost, Product.Status status) {
        requireAllNonNull(productName);
        checkEmpty(productName, ProductMessageUtils.MESSAGE_MISSING_PRODUCT_NAME);

        try {
            this.productName = productName;
            this.ingredientCost = ingredientCost;
            this.retailPrice = retailPrice;
            this.status = status;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
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

    public double getRetailPrice() {
        return retailPrice;
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
        return productName + ": " + retailPrice + "$" + ingredients.toString();
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

