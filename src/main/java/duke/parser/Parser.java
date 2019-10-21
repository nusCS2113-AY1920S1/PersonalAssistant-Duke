package duke.parser;

import duke.Duke;
import duke.command.Command;
import duke.command.CommandBooking;
import duke.command.CommandIngredients;
import duke.command.*;
import duke.command.bookingcommands.*;
import duke.command.inventorycommands.AddIngredientCommand;
import duke.command.inventorycommands.DeleteIngredientCommand;
import duke.command.inventorycommands.ListIngredientsCommand;
import duke.command.recipecommands.*;
import duke.exception.DukeException;
import duke.list.recipelist.RecipeIngredientList;
import duke.list.recipelist.RecipeList;
import duke.list.recipelist.RecipeTitleList;
import duke.storage.RecipeIngredientStorage;
import duke.storage.RecipeStorage;
import duke.storage.RecipeTitleStorage;
import duke.ui.Ui;

import static duke.common.BookingMessages.*;
import static duke.common.IngredientMessages.COMMAND_ADD_INGREDIENT;
import static duke.common.IngredientMessages.COMMAND_DELETE_INGREDIENT;
import static duke.common.RecipeMessages.*;


/**
 * Making sense of the user input command.
 */
public class Parser {

    public static CommandIngredients parseIngredient(String input) {
        if (input.trim().contains(COMMAND_ADD_INGREDIENT)) {
            return new AddIngredientCommand(input);
        } else if (input.trim().contains(COMMAND_DELETE_INGREDIENT)) {
            return new DeleteIngredientCommand(input);
        } else {
            return new ListIngredientsCommand(input);
        }
    }

    public static Command<RecipeTitleList, Ui, RecipeTitleStorage> parseRecipeTitle(String input) {
        if (input.trim().contains(COMMAND_ADD_RECIPE_TITLE)) {
            return new AddRecipeTitleCommand(input);
        } else {
            return new DeleteRecipeTitleCommand(input);
        }
    }

    public static Command<RecipeList, Ui, RecipeStorage> parseRecipe(String input) {
        if (input.trim().contains(COMMAND_ADD_RECIPE)) {
            return new AddRecipeCommand(input);
//        } else if (input.trim().contains(COMMAND_ADD_RECIPE_INGREDIENT)) {
//            return new AddRecipeIngredientCommand(input);
        } else if (input.trim().contains(COMMAND_DELETE_RECIPE)) {
            return new DeleteRecipeCommand(input);
        } else {
            System.out.println("went to listallrecipes");
            return new ListAllRecipeCommand(input);
        }
    }

//    public static Command<RecipeList, Ui, RecipeStorage> parseRecipeIngredient(String input) throws DukeException {
//        if (input.trim().contains(COMMAND_ADD_RECIPE_INGREDIENT)) {
//            return new AddRecipeIngredientCommand(input);
//        } else if (input.trim().contains(COMMAND_LIST_RECIPE_INGREDIENT)) {
//            return new ListRecipeIngredientCommand(input);
//        } else {
//            return new DeleteRecipeIngredientCommand(input);
//        }
//    }

    public static CommandBooking parseBooking(String userInputCommand) {
        if (userInputCommand.contains(COMMAND_FIND_BOOKING)) {
            return new FindBookingCommand(userInputCommand);
        } else if (userInputCommand.contains(COMMAND_ADD_BOOKING)) {
            return new AddBookingCommand(userInputCommand);
        } else if (userInputCommand.contains(COMMAND_DELETE_BOOKING)) {
            return new DeleteBookingCommand(userInputCommand);
        } else if (userInputCommand.contains(COMMAND_VIEW_BOOKING_SCHEDULE)) {
            return new ViewBookingScheduleCommand(userInputCommand);
        } else { //userInputCommand.equals(COMMAND_LIST_BOOKINGS)
            return new AllBookingsCommand();
        }
    }
}