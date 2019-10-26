package duke.common;

public class RecipeMessages {
    public static final String COMMAND_ADD_RECIPE = "addrecipe";
    public static final String COMMAND_ADD_RECIPE_INGREDIENT = "addtorecipeingredient";
    public static final String COMMAND_EDIT_RECIPE = "editrecipe";
    public static final String COMMAND_LIST_RECIPES = "listallrecipes";
    public static final String COMMAND_VIEW_RECIPE = "viewrecipe";
    public static final String COMMAND_EDIT_REQ_INGREDIENT = "editreqingredient";
    public static final String COMMAND_EDIT_RATING = "editrating";
    public static final String COMMAND_EDIT_FEEDBACK = "editfeedback";

    public static final String COMMAND_LIST_RECIPE_INGREDIENT = "listingredient";
    public static final String COMMAND_DELETE_RECIPE = "deleterecipe";
    public static final String COMMAND_DELETE_RECIPE_INGREDIENT = "delri";
    public static final String COMMAND_DELETE_RECIPE_TITLE = "delrt";
    public static final String COMMAND_DELETE_PREPSTEP = "delp";

    public static final String COMMAND_ADD_FEEDBACK = "addfeedback";
    public static final String COMMAND_ADD_RATING = "addrating";
    public static final String COMMAND_ADD_PREPSTEP = "addprepstep";

    public static final String MESSAGE_DELETE_PREPSTEP = "     Noted. I've removed this step:\n";
    public static final String MESSAGE_DELETE_RECIPE = "Noted. I've removed this ingredient:\n";

    public static final String MESSAGE_RECIPE_DELETED = "Noted. I've removed this recipe:\n";
    public static final String MESSAGE_RECIPE_ADDED = "Got it. I've added this recipe:\n";
    public static final String MESSAGE_ADDED_TO_REQ_INGREDIENTS = "Got it. I've added this ingredient to the list of required ingredients.";
    public static final String MESSAGE_RECIPE_TO_BE_VIEWED = "Here are the details for the recipe:";
    public static final String MESSAGE_HERE_ARE_THE_RECIPES = "Here are the recipe(s) currently in the list:\n";
    public static final String MESSAGE_DELETED_FROM_REQ_INGREDIENTS = "Noted. I've removed this ingredient from the list of required ingredients.";
    public static final String MESSAGE_CLEARED_REQ_INGREDIENTS = "The required ingredient list for the recipe has been cleared.";

    public static final String ERROR_MESSAGE_INVALID_RECIPE_QUANTITY = "Please input a valid value for the quantity";
    public static final String ERROR_MESSAGE_DELETE_RECIPE_NOT_FOUND = "The recipe that you wish to delete is not in your recipe list.";
    public static final String ERROR_MESSAGE_INVALID_POSITION = "Please input a valid value for the position you wish to insert the ingredient in.";
    public static final String ERROR_MESSAGE_RECIPE_INCOMPLETE = "     Please provide all information needed.";
    public static final String ERROR_MESSAGE_INVALID_RECIPE_FORMAT = "     Please input in this format:\n     Eg: addrecipeingredient garlic 100 grams.";
    public static final String ERROR_MESSAGE_RECIPE_ALREADY_EXISTS = "Sorry, the recipe you wish to add already exists.";
    public static final String ERROR_MESSAGE_RECIPE_LIST_IS_EMPTY = "The recipe list is already empty.";
    public static final String ERROR_MESSAGE_RECIPE_DOES_NOT_EXIST = "The recipe list does not contain this recipe.";
    public static final String ERROR_MESSAGE_EDIT_INGREDIENT_INS_INCORRECT_FORMAT = "Please input the command in the following format:\n"
            + "editreqingredient [recipe name] ins/ [position] n/ [ingredient name] q/ [quantity] u/ [unit] a/ [additional information]";
    public static final String ERROR_MESSAGE_EDIT_INGREDIENT_INS_INCOMPLETE = "Please provide the position, ingredient name, quantity, unit and additional information if any.";
    public static final String ERROR_MESSAGE_REQ_INGREDIENT_NO_EDIT_COMMAND = "Please include the type of edit command that you want to perform.\n"
            + "Supported edit commands for required ingredients include: 'ins/', 'del/', 'app/', 'clr/'.";
    public static final String ERROR_MESSAGE_EDIT_RECIPE_INCORRECT_FORMAT = "Please input the command in the following format:\n"
            + "editreqingredient [recipe name] ins/ [position] n/ [ingredient name] q/ [quantity] u/ [unit] a/ [additional information]\n"
            + "editreqingredient [recipe name] del/ [position]\n"
            + "editreqingredient [recipe name] app/ n/ [ingredient name] q/ [quantity] u/ [unit] a/ [additional information]\n"
            + "editreqingredient [recipe name] clr/";
    public static final String ERROR_MESSAGE_EDIT_INGREDIENT_CLEAR_INCOMPLETE = "Please provide the name of the recipe which you wish to clear the required ingredient list.";
    public static final String ERROR_MESSAGE_EDIT_RATING_INCOMPLETE = "Please provide the title of the recipe which you wish to edit the rating.";
    public static final String ERROR_MESSAGE_EDIT_FEEDBACK_INCOMPLETE = "Please provide the title of the recipe which you wish to edit the feedback.";
    public static final String ERROR_MESSAGE_INVALID_RATING = "Only unrated, average, good and delicious ratings are supported.";
    public static final String ERROR_MESSAGE_RATING_INCORRECT_FORMAT = "Please input the command in the following format:\n"
            + "editrating [recipe name] r/ [rating]\n"
            + "If no [rating] is input, the rating will be set to unrated.";
    public static final String ERROR_MESSAGE_FEEDBACK_INCORRECT_FORMAT = "Please input the command in the following format:\n"
            + "editfeedback [recipe name] f/ [feedback]\n"
            + "If no [feedback] is input, the feedback will be set to 'No feedback yet.'.";
}
