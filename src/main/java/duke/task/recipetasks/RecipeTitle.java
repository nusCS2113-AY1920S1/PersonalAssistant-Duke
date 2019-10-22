package duke.task.recipetasks;

public class RecipeTitle {

    private String recipeTitle;
    private int index;

    public RecipeTitle(String recipeTitle) {
        this.recipeTitle = recipeTitle;
    }

    public RecipeTitle(int index, String recipeTitle) {
        this.recipeTitle = recipeTitle;
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }

    public String getTitle() {
        return this.recipeTitle;
    }

    public String toSaveString() {
        return recipeTitle;
    }

    public String toString() {
        return "[" + index + "]" + "[Recipe Title: " + recipeTitle + "]";
    }
}
