package duke.logic.parser;

import duke.logic.command.Command;
import duke.logic.command.bookingcommands.*;
import duke.logic.command.inventorycommands.*;
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
        } else if (input.trim().contains(COMMAND_CLEAR_INVENTORY)) {
            return new ClearInventoryCommand(input);
        } else if (input.trim().contains(COMMAND_USE_RECIPE)) {
            return new UseRecipeCommand(input);
        } else if (input.trim().contains(COMMAND_ADD_RECIPE)) {
            return new AddRecipeCommand(input);
        } else if (input.trim().contains(COMMAND_DELETE_RECIPE)) {
            return new DeleteRecipeCommand(input);
        } else if (input.trim().contains(COMMAND_VIEW_RECIPE)) {
            return new ViewRecipeCommand(input);
        } else if (input.trim().contains(COMMAND_VIEW_REQ_INGREDIENT)) {
            return new ViewRequiredIngredientCommand(input);
        } else if (input.trim().contains(COMMAND_EDIT_REQ_INGREDIENT)) {
            return new EditRequiredIngredientCommand(input);
        } else if (input.trim().contains(COMMAND_EDIT_RATING)) {
            return new EditRatingCommand(input);
        } else if (input.trim().contains(COMMAND_EDIT_FEEDBACK)) {
            return new EditFeedbackCommand(input);
        } else if (input.trim().contains(COMMAND_EDIT_PREPSTEP)) {
            return new EditPrepStepCommand(input);
        } else if (input.trim().contains(COMMAND_EDIT_PREPTIME)) {
            return new EditPrepTimeCommand(input);
        } else if (input.contains(COMMAND_FIND_BOOKING)) {
            return new FindBookingCommand(input);
        } else if (input.contains(COMMAND_ADD_BOOKING)) {
            return new AddBookingCommand(input);
        } else if (input.contains(COMMAND_DELETE_BOOKING)) {
            return new DeleteBookingCommand(input);
        } else if (input.contains(COMMAND_VIEW_BOOKING_SCHEDULE)) {
            return new ViewBookingScheduleCommand(input);
        } else if (input.contains(COMMAND_VIEW_ORDERS)) {
            return new ViewOrdersCommand(input);
        } else if (input.contains(COMMAND_LIST_BOOKINGS)) {
            return new AllBookingsCommand();
        } else {
            return new ListAllRecipeCommand(input);
        }
    }
}