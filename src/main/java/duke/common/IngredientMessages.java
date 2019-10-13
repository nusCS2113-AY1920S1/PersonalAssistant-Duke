package duke.common;

public class IngredientMessages {
    public static final String COMMAND_ADD_INGREDIENT = "addingredient";
    public static final String COMMAND_LIST_INGREDIENTS = "listingredients";
    public static final String COMMAND_DELETE_INGREDIENT = "deleteingredient";

    public static final String ERROR_MESSAGE_INVALID_QUANTITY = "Please input an integer for the quantity";
    public static final String ERROR_MESSAGE_INCOMPLETE = "     Please provide both the ingredient name and quantity.";
    public static final String ERROR_MESSAGE_INVALID_FORMAT = "     Please input in this format:\n     Eg: addingredient apple q/ 10.";
}
