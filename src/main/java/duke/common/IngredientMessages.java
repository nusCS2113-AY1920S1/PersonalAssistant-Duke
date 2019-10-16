package duke.common;

public class IngredientMessages {
    public static final String COMMAND_ADD_INGREDIENT = "addingredient";
    public static final String COMMAND_LIST_INGREDIENTS = "listinventory";
    public static final String COMMAND_DELETE_INGREDIENT = "deleteingredient";

    public static final String MESSAGE_INGREDIENT_DELETED = "     Noted. I've removed this ingredient:\n";
    public static final String MESSAGE_INGREDIENT_ADDED = "     Got it. I've added this ingredient:\n";
    public static final String MESSAGE_LIST_INGREDIENTS = "     Here are the ingredients in your inventory:";

    public static final String ERROR_MESSAGE_DELETING_FROM_EMPTY_LIST = "     OOPS!!! The inventory list is empty. There is no ingredient to be deleted.";
    public static final String ERROR_MESSAGE_INCOMPLETE = "     Please provide both the ingredient name and quantity.";
    public static final String ERROR_MESSAGE_ADD_INCORRECT_FORMAT = "     Please input the command in the following format:\n"
            + "     addingredient [ingredient name] q/ [quantity]";
    public static final String ERROR_MESSAGE_INVALID_QUANTITY = "     Quantity of ingredient should be a number. Please input the command again.";
}
