package duke.model.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import duke.logic.parser.exceptions.ParseException;
import duke.model.ingredient.Ingredient;
import duke.model.ingredient.IngredientList;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static duke.commons.util.AppUtil.checkEmpty;
import static duke.commons.util.CollectionUtil.requireAllNonNull;

public class Product {

    public enum Status {
        ACTIVE,
        ARCHIVED
    }

    public static final String MESSAGE_CONSTRAINTS = "Product name can take any values, "
            + "and should not be blank";

    private String name;
    //private List<Ingredient> ingredients = new ArrayList<>();
    private IngredientList ingredients = new IngredientList() {};
    private double cost;
    private double price;
    private Status status;

    public Product(@JsonProperty("name") String name, @JsonProperty("cost") String cost,
                   @JsonProperty("price") String price) {
        requireAllNonNull(name);
        checkEmpty(name, MESSAGE_CONSTRAINTS);

        try {
            this.name = name;
            this.cost = Double.parseDouble(cost);
            this.price = Double.parseDouble(price);
            this.status = Status.ACTIVE;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }



    public Product init() {
        name = "Cheese Cake";
        return this;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Status getStatus() {
        return this.status;
    }

/*
    public List<Ingredient> getIngredients() {
        return this.ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
*/
@Override
public String toString() {
    return name + ": " + price + "$";
}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        duke.model.product.Product product = (duke.model.product.Product) o;
        return name.equals(product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

}

