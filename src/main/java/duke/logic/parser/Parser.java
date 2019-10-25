package duke.logic.parser;

import duke.logic.command.Command;
import duke.logic.command.CommandBooking;
import duke.logic.command.*;
import duke.logic.command.bookingcommands.*;
import duke.logic.command.inventorycommands.AddToInventoryCommand;
import duke.logic.command.inventorycommands.DeleteFromInventoryCommand;
import duke.logic.command.inventorycommands.ListInventoryCommand;
import duke.logic.command.recipecommands.*;
import duke.model.list.recipelist.RecipeIngredientList;
import duke.model.list.recipelist.RecipeList;
import duke.storage.RecipeIngredientStorage;
import duke.storage.RecipeStorage;
import duke.ui.Ui;

import static duke.common.BookingMessages.*;
import static duke.common.InventoryMessages.COMMAND_ADD_TO_INVENTORY;
import static duke.common.InventoryMessages.COMMAND_DELETE_FROM_INVENTORY;
import static duke.common.RecipeMessages.*;


/**
 * Making sense of the user input command.
 */
public class Parser {

    public static CommandInventory parseIngredient(String input) {
        if (input.trim().contains(COMMAND_ADD_TO_INVENTORY)) {
            return new AddToInventoryCommand(input);
        } else if (input.trim().contains(COMMAND_DELETE_FROM_INVENTORY)) {
            return new DeleteFromInventoryCommand(input);
        } else {
            return new ListInventoryCommand(input);
        }
    }

    public static CommandRecipe<RecipeIngredientList, RecipeList, RecipeIngredientStorage, RecipeStorage> parseRecipeIngredient(String input) {
        return new AddRecipeIngredientCommand(input);
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