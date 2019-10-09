package duke.model.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import duke.model.ingredient.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class Product {


    private String name;
    private List<Ingredient> ingredients = new ArrayList<>();

    public Product(@JsonProperty("name") String name) {
        this.name = name;
    }

    public Product() {

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

    public List<Ingredient> getIngredients() {
        return this.ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}

