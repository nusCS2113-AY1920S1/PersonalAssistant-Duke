package duke.model.task.recipetasks;

public class RecipeTitle {

    private String recipeTitle;
    private int index;

    public RecipeTitle(String recipeTitle) {
        this.recipeTitle = recipeTitle;
    }

    public RecipeTitle(Integer index, String recipeTitle) {
        this.recipeTitle = recipeTitle;
        this.index = index;
    }

    public RecipeTitle(int index) {
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }

    public String getTitle() {
        return this.recipeTitle;
    }

    public String toSaveString() {
        return index + " | " + recipeTitle;
    }

    public String toString() {
        return "[" + index + "]" + "[Recipe Title: " + recipeTitle + "]";
    }
}
