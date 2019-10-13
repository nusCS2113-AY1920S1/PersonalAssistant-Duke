package duke.common;

public class RecipeMessages {
    public static final String COMMAND_ADD_RECIPE = "addrecipe";
    public static final String COMMAND_ADD_RECIPE_INGREDIENT = "addrecipeingredient";
    public static final String COMMAND_LIST_RECIPES = "listallrecipes";
    public static final String COMMAND_DELETE_RECIPE = "deleterecipe";

    public static final String ERROR_MESSAGE_INVALID_RECIPE_QUANTITY = "Please input an integer for the quantity";
    public static final String ERROR_MESSAGE_RECIPE_INCOMPLETE = "     Please provide all information needed.";
    public static final String ERROR_MESSAGE_INVALID_RECIPE_FORMAT = "     Please input in this format:\n     Eg: addingredient garlic 100 grams.";
}
