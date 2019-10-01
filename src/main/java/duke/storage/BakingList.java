package duke.storage;

import duke.entities.Order;
import duke.entities.recipe.Recipe;
import duke.storage.recipe.RecipeList;

import java.util.ArrayList;
import java.util.List;

public class BakingList {

    private List<Order> orderList = new ArrayList<>();

    private List<Recipe> recipeList = new ArrayList<>();

    public List<Order> getOrderList() {
        return orderList;
    }

    public List<Recipe> getRecipeList() {
        return recipeList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }
}
