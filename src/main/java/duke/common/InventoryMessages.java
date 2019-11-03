package duke.common;

public class InventoryMessages {
    public static final String COMMAND_ADD_TO_INVENTORY = "addtoinventory";
    public static final String COMMAND_LIST_INVENTORY = "listinventory";
    public static final String COMMAND_DELETE_FROM_INVENTORY = "deletefrominventory";
    public static final String COMMAND_CLEAR_INVENTORY = "clearinventory";
    public static final String COMMAND_USE_RECIPE = "userecipe";

    public static final String MESSAGE_INGREDIENT_DELETED = "Noted. I've removed this ingredient from the inventory:\n";
    public static final String MESSAGE_ADDED_TO_INVENTORY = "Got it. I've added this ingredient to the inventory.";
    public static final String MESSAGE_HERE_ARE_THE_INGREDIENTS = "Here are the ingredients currently in the inventory:\n";
    public static final String MESSAGE_INGREDIENTS_CLEARED = "All ingredients in the inventory has been cleared. The inventory list is now empty.";
    public static final String MESSAGE_USE_RECIPE_INVENTORY_UPDATED = "Your inventory list has been updated.";

    public static final String ERROR_MESSAGE_DELETING_FROM_EMPTY_LIST = "OOPS!!! The inventory list is empty. There is no ingredient to be deleted.";
    public static final String ERROR_MESSAGE_INGREDIENT_INCOMPLETE = "Please provide the ingredient name, quantity, unit and additional information if any.";
    public static final String ERROR_MESSAGE_ADD_INCORRECT_FORMAT = "Please input the command in the following format:\n"
            + "addtoinventory [ingredient name] q/ [quantity] u/ [unit] a/ [additional information]";
    public static final String ERROR_MESSAGE_INVALID_QUANTITY = "Quantity of ingredient should be a number. Please input the command again.";
    public static final String ERROR_MESSAGE_INVALID_UNIT = "Unit of the ingredient is not supported yet. Please input the command again with a supported unit.";
    public static final String ERROR_MESSAGE_INVALID_QUANTITY_OR_UNIT = "Either the quantity or unit of the ingredient to be added is not in the right format.\nPlease input the command again.";
    public static final String ERROR_MESSAGE_DELETE_INGREDIENT_NOT_FOUND = "The ingredient that you wish to delete is not in your inventory.";
    public static final String ERROR_MESSAGE_INVENTORY_ALREADY_EMPTY = "Your inventory is already empty. There is nothing to be cleared.";
    public static final String ERROR_MESSAGE_INVENTORY_IS_EMPTY = "Your inventory is empty.\n" +
            "Are you sure this recipe has been used?";
    public static final String ERROR_MESSAGE_HAS_RECIPE_BEEN_USED = "The amount of ingredients in the inventory is less than the amount required by this recipe.\n" +
            "Are you sure this recipe has been used?";

    public static final String NO_ADDITIONAL_INFO = "No additional information.";
}
