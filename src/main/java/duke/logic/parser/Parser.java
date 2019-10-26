package duke.logic.parser;

import duke.logic.command.CommandBooking;
import duke.logic.command.*;
import duke.logic.command.bookingcommands.*;
import duke.logic.command.inventorycommands.AddToInventoryCommand;
import duke.logic.command.inventorycommands.DeleteFromInventoryCommand;
import duke.logic.command.inventorycommands.ListInventoryCommand;
import duke.logic.command.recipecommands.*;


import static duke.common.BookingMessages.*;
import static duke.common.InventoryMessages.*;
import static duke.common.RecipeMessages.*;



/**
 * Making sense of the user input command.
 */
public class Parser {

    public static Command parse(String input) {
        if (input.trim().contains(COMMAND_ADD_TO_INVENTORY)) {
            return new AddToInventoryCommand(input);
        } else if (input.trim().contains(COMMAND_DELETE_FROM_INVENTORY)) {
            return new DeleteFromInventoryCommand(input);
        } else if (input.trim().contains(COMMAND_LIST_INVENTORY)) {
            return new ListInventoryCommand(input);
        } else if (input.trim().contains(COMMAND_ADD_RECIPE)) {
            return new AddRecipeCommand(input);
        } else if (input.trim().contains(COMMAND_DELETE_RECIPE)) {
            return new DeleteRecipeCommand(input);
        } else if (input.trim().contains(COMMAND_VIEW_RECIPE)) {
            return new ViewRecipeCommand(input);
        } else if (input.trim().contains(COMMAND_EDIT_REQ_INGREDIENT)) {
            return new EditRequiredIngredientCommand(input);
        } else if (input.trim().contains(COMMAND_EDIT_RATING)) {
            return new EditRatingCommand(input);
        } else if (input.trim().contains(COMMAND_EDIT_FEEDBACK)) {
            return new EditFeedbackCommand(input);
        } else {
            System.out.println("went to listallrecipes");
            return new ListAllRecipeCommand(input);
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
        } else if (userInputCommand.contains(COMMAND_VIEW_ORDERS)) {
            return new ViewOrdersCommand(userInputCommand);
        } else {
            return new AllBookingsCommand();
        }
    }
}