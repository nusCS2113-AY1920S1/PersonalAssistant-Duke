package duke.parser;

import duke.command.*;

import duke.command.inventorycommands.AddIngredientCommand;
import duke.command.CommandIngredients;
import duke.command.inventorycommands.DeleteIngredientCommand;
import duke.command.recipecommands.*;
import duke.exception.DukeException;

import static duke.common.BookingMessages.*;
import static duke.common.RecipeMessages.*;

import duke.command.bookingcommands.*;

import static duke.common.Messages.ERROR_MESSAGE_RANDOM;


/**
 * Making sense of the user input command.
 */
public class Parser {

    public static CommandIngredients parseIngredient(String input) throws DukeException {
        return new AddIngredientCommand(input);
    }

    public static CommandRecipeTitle parseRecipeTitle(String input) throws DukeException {
        if (input.trim().contains(COMMAND_ADD_RECIPE_TITLE)) {
            return new AddRecipeTitleCommand(input);
        } else if (input.trim().contains(COMMAND_DELETE_RECIPE_TITLE)) {
            return new DeleteRecipeTitleCommand(input);
        } else {
            return new ListAllRecipeCommand(input);
        }
    }

    public static CommandRecipeIngredient parseRecipeIngredient(String input) throws DukeException {
        if (input.trim().contains(COMMAND_ADD_RECIPE_INGREDIENT)) {
            return new AddRecipeIngredientCommand(input);
        } else if (input.trim().contains(COMMAND_LIST_RECIPE_INGREDIENT)) {
            return new ListRecipeIngredientCommand(input);
        } else {
            return new DeleteRecipeIngredientCommand(input);
        }
    }

    public static CommandBooking parseBooking(String userInputCommand) throws DukeException {

        if (userInputCommand.trim().equals(COMMAND_LIST_BOOKINGS)) {
            return new AllBookingsCommand();
        } else if (userInputCommand.contains(COMMAND_ADD_BOOKING)) {
            if (userInputCommand.trim().substring(0, 10).equals(COMMAND_ADD_BOOKING)) {
                return new AddBookingCommand(userInputCommand);
            } else {
                throw new DukeException(ERROR_MESSAGE_RANDOM);
            }
        } else if (userInputCommand.contains(COMMAND_DELETE_BOOKING)) {
            if (userInputCommand.trim().substring(0, 13).equals(COMMAND_DELETE_BOOKING)) {
                return new DeleteBookingCommand(userInputCommand);
            } else {
                throw new DukeException(ERROR_MESSAGE_RANDOM);
            }
        } else if (userInputCommand.contains(COMMAND_VIEW_BOOKING_SCHEDULE)) {
            if (userInputCommand.trim().substring(0, 19).equals(COMMAND_VIEW_BOOKING_SCHEDULE)) {
                return new ViewBookingScheduleCommand(userInputCommand);
            } else {
                throw new DukeException(ERROR_MESSAGE_RANDOM);
            }
        } else if (userInputCommand.contains(COMMAND_FIND_BOOKING)) {
            if (userInputCommand.trim().substring(0, 11).equals(COMMAND_FIND_BOOKING)) {
                return new FindBookingCommand(userInputCommand);
            } else {
                throw new DukeException(ERROR_MESSAGE_RANDOM);
            }
        } else {
            throw new DukeException(ERROR_MESSAGE_RANDOM);
        }
    }

}