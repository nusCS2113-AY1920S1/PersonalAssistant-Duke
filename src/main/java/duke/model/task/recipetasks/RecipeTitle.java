package duke.model.task.recipetasks;

public class RecipeTitle {

    private String recipeTitle;

    public RecipeTitle(String recipeTitle) {
        this.recipeTitle = recipeTitle;
    }

    public String getTitle() {
        return this.recipeTitle;
    }

    public String toSaveString() {
        return recipeTitle;
    }

    public String toString() {
        return recipeTitle;
    }
}
