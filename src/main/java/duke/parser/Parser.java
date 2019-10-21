package duke.parser;

import duke.command.*;
import duke.command.bookingcommands.*;
import duke.command.inventorycommands.AddToInventoryCommand;
import duke.command.inventorycommands.DeleteFromInventoryCommand;
import duke.command.inventorycommands.ListInventoryCommand;
import duke.command.recipecommands.*;

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

    public static CommandRecipeTitle parseRecipeTitle(String input) {
        if (input.trim().contains(COMMAND_ADD_RECIPE_TITLE)) {
            return new AddRecipeTitleCommand(input);
        } else {
            return new DeleteRecipeTitleCommand(input);
        }
    }

    public static CommandRecipe parseRecipe(String input) {
        if (input.trim().contains(COMMAND_ADD_RECIPE)) {
            return new AddRecipeCommand(input);
        } else if (input.trim().contains(COMMAND_DELETE_RECIPE)) {
            return new DeleteRecipeCommand(input);
        } else {
            System.out.println("went to listallrecipes");
            return new ListAllRecipeCommand(input);
        }
    }

    public static CommandRecipeIngredient parseRecipeIngredient(String input) {
        if (input.trim().contains(COMMAND_ADD_RECIPE_INGREDIENT)) {
            return new AddRecipeIngredientCommand(input);
        } else if (input.trim().contains(COMMAND_LIST_RECIPE_INGREDIENT)) {
            return new ListRecipeIngredientCommand(input);
        } else {
            return new DeleteRecipeIngredientCommand(input);
        }
    }

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