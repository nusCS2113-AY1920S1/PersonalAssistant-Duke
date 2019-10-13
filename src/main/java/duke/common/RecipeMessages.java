package duke.common;

public class RecipeMessages {
    public static final String COMMAND_ADD_RECIPE_TITLE = "addrecipetitle";
    public static final String COMMAND_ADD_RECIPE_INGREDIENT = "addrecipeingredient";
    public static final String COMMAND_LIST_RECIPES = "listallrecipes";
    public static final String COMMAND_DELETE_RECIPE_INGREDIENT = "del";

    public static final String MESSAGE_DELETE_RECIPE = "     Noted. I've removed this ingredient:\n";

    public static final String ERROR_MESSAGE_INVALID_RECIPE_QUANTITY = "Please input a valid value for the quantity";
    public static final String ERROR_MESSAGE_RECIPE_INCOMPLETE = "     Please provide all information needed.";
    public static final String ERROR_MESSAGE_INVALID_RECIPE_FORMAT = "     Please input in this format:\n     Eg: addrecipeingredient garlic 100 grams.";
}
