package duke.logic.parser;

import duke.logic.command.Command;
import duke.logic.command.bookingcommands.AddBookingCommand;
import duke.logic.command.bookingcommands.AllBookingsCommand;
import duke.logic.command.bookingcommands.DeleteBookingCommand;
import duke.logic.command.bookingcommands.FindBookingCommand;
import duke.logic.command.bookingcommands.ViewBookingScheduleCommand;
import duke.logic.command.bookingcommands.ViewOrdersCommand;

import duke.logic.command.inventorycommands.UseRecipeCommand;
import duke.logic.command.inventorycommands.DeleteFromInventoryCommand;
import duke.logic.command.inventorycommands.AddToInventoryCommand;
import duke.logic.command.inventorycommands.ClearInventoryCommand;
import duke.logic.command.inventorycommands.ListInventoryCommand;

import duke.logic.command.recipecommands.AddRecipeCommand;
import duke.logic.command.recipecommands.EditRequiredIngredientCommand;
import duke.logic.command.recipecommands.DeleteRecipeCommand;
import duke.logic.command.recipecommands.ViewRecipeCommand;
import duke.logic.command.recipecommands.ListAllRecipeCommand;
import duke.logic.command.recipecommands.ViewRequiredIngredientCommand;
import duke.logic.command.recipecommands.EditFeedbackCommand;
import duke.logic.command.recipecommands.EditPrepTimeCommand;
import duke.logic.command.recipecommands.EditRatingCommand;
import duke.logic.command.recipecommands.EditPrepStepCommand;

import static duke.common.BookingMessages.COMMAND_LIST_BOOKINGS;
import static duke.common.BookingMessages.COMMAND_ADD_BOOKING;
import static duke.common.BookingMessages.COMMAND_DELETE_BOOKING;
import static duke.common.BookingMessages.COMMAND_VIEW_BOOKING_SCHEDULE;
import static duke.common.BookingMessages.COMMAND_FIND_BOOKING;
import static duke.common.BookingMessages.COMMAND_VIEW_ORDERS;

import static duke.common.InventoryMessages.COMMAND_ADD_TO_INVENTORY;
import static duke.common.InventoryMessages.COMMAND_LIST_INVENTORY;
import static duke.common.InventoryMessages.COMMAND_DELETE_FROM_INVENTORY;
import static duke.common.InventoryMessages.COMMAND_CLEAR_INVENTORY;
import static duke.common.InventoryMessages.COMMAND_USE_RECIPE;

import static duke.common.RecipeMessages.COMMAND_ADD_RECIPE;
import static duke.common.RecipeMessages.COMMAND_DELETE_RECIPE;
import static duke.common.RecipeMessages.COMMAND_EDIT_REQ_INGREDIENT;
import static duke.common.RecipeMessages.COMMAND_EDIT_RATING;
import static duke.common.RecipeMessages.COMMAND_EDIT_FEEDBACK;
import static duke.common.RecipeMessages.COMMAND_EDIT_PREPSTEP;
import static duke.common.RecipeMessages.COMMAND_EDIT_PREPTIME;
import static duke.common.RecipeMessages.COMMAND_VIEW_RECIPE;
import static duke.common.RecipeMessages.COMMAND_VIEW_REQ_INGREDIENT;

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