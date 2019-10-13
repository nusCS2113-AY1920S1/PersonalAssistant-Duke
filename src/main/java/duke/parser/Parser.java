package duke.parser;

import duke.command.*;

import duke.command.inventorycommands.AddIngredientCommand;
import duke.command.CommandIngredients;
import duke.command.recipecommands.*;
import duke.exception.DukeException;

import static duke.common.Messages.*;
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

    public static CommandTest parseTest(String input) throws DukeException {
        if (input.trim().equals(COMMAND_LIST)) {
            return new ListCommand(input);
        } else if (input.contains(COMMAND_FIND)) {
            if (input.trim().substring(0, 4).equals(COMMAND_FIND)) {
                return new FindCommand(input);
            } else {
                throw new DukeException(ERROR_MESSAGE_RANDOM);
            }
        } else {
            throw new DukeException(ERROR_MESSAGE_RANDOM);
        }
    }


    public static CommandBooking parseBooking(String userInputCommand) throws DukeException {

        if (userInputCommand.trim().equals("allbookings")) {
            return new AllBookingsCommand();
        } else if (userInputCommand.contains("addbooking")) {
            if (userInputCommand.trim().substring(0, 10).equals("addbooking")) {
                return new AddBookingCommand(userInputCommand);
            } else {
                throw new DukeException(ERROR_MESSAGE_RANDOM);
            }
        } else if (userInputCommand.contains("deletebooking")) {
            if (userInputCommand.trim().substring(0, 13).equals("deletebooking")) {
                return new DeleteBookingCommand(userInputCommand);
            } else {
                throw new DukeException(ERROR_MESSAGE_RANDOM);
            }
        } else if (userInputCommand.contains("viewbookingschedule")) {
            if (userInputCommand.trim().substring(0, 19).equals("viewbookingschedule")) {
                return new ViewBookingScheduleCommand(userInputCommand);
            } else {
                throw new DukeException(ERROR_MESSAGE_RANDOM);
            }
        } else if (userInputCommand.contains("findbooking")) {
            if (userInputCommand.trim().substring(0, 11).equals("findbooking")) {
                return new FindBookingCommand(userInputCommand);
            } else {
                throw new DukeException(ERROR_MESSAGE_RANDOM);
            }
        } else {
            throw new DukeException(ERROR_MESSAGE_RANDOM);
        }
    }

    /*
    /**
     * Processes the different user input command.
     * @param userInput String containing input command from user
     * @return the different command object corresponding to the user input
     * @throws DukeException if Duke cannot recognise the user input
     */
//    public static Command parse(String userInputCommand) throws DukeException {
//        if (userInputCommand.trim().equals(COMMAND_LIST)) {
//            return new DoneCommand(userInputCommand);
//        } else if (userInputCommand.trim().equals(COMMAND_BYE)) {
//            return new ByeCommand(userInputCommand);
//        } else if (userInputCommand.contains(COMMAND_DONE)) {
//            return new DoneCommand(userInputCommand);
//        } else if (userInputCommand.contains(COMMAND_DEADLINE)) {
//            if (userInputCommand.trim().substring(0, 8).equals(COMMAND_DEADLINE)) {
//                return new DeadlineCommand(userInputCommand);
//            } else {
//                throw new DukeException(ERROR_MESSAGE_RANDOM);
//            }
//        } else if (userInput.contains(COMMAND_DELETE)) {
//            if (userInput.trim().substring(0, 6).equals(COMMAND_DELETE)) {
//                return new DeleteCommand(userInput);
//            } else {
//                throw new DukeException(ERROR_MESSAGE_RANDOM);
//            }
//        } else if (userInput.contains(COMMAND_EVENT)) {
//            if (userInput.trim().substring(0, 5).equals(COMMAND_EVENT)) {
//                return new EventCommand(userInput);
//            } else {
//                throw new DukeException(ERROR_MESSAGE_RANDOM);
//            }
//        } else if (userInput.contains(COMMAND_TODO)) {
//            if (userInput.trim().substring(0, 4).equals(COMMAND_TODO)) {
//                return new TodoCommand(userInput);
//            } else {
//                throw new DukeException(ERROR_MESSAGE_RANDOM);
//            }
//        } else if (userInputCommand.contains(COMMAND_FIND)) {
//            if (userInputCommand.trim().substring(0, 4).equals(COMMAND_FIND)) {
//                return new DoneCommand(userInputCommand);
//            } else {
//                throw new DukeException(ERROR_MESSAGE_RANDOM);
//            }
//        } else if (userInput.contains(COMMAND_DURATION)) {
//            if (userInput.trim().substring(0, 5).equals(COMMAND_DURATION)) {
//                return new DurationCommand(userInput);
//            } else {
//                throw new DukeException(ERROR_MESSAGE_RANDOM);
//            }
//        } else if (userInput.contains(COMMAND_SNOOZE)) {
//            if (userInput.trim().substring(0, 6).equals(COMMAND_SNOOZE)) {
//                return new SnoozeCommand(userInput);
//            } else {
//                throw new DukeException(ERROR_MESSAGE_RANDOM);
//            }
//        } else if (userInput.contains(COMMAND_PERIOD)) {
//            if (userInput.trim().substring(0, 6).equals(COMMAND_PERIOD)) {
//                return new PeriodCommand(userInput);
//            } else {
//                throw new DukeException(ERROR_MESSAGE_RANDOM);
//            }
//        } else if (userInput.contains(COMMAND_REMIND)) {
//            if (userInput.trim().substring(0, 9).equals(COMMAND_REMIND)) {
//                return new RemindCommand(userInput);
//            } else {
//                throw new DukeException(ERROR_MESSAGE_RANDOM);
//            }
//        } else if (userInput.contains(COMMAND_VIEWSCHEDULE)) {
//            if (userInput.trim().substring(0, 12).equals(COMMAND_VIEWSCHEDULE)) {
//                return new ViewScheduleCommand(userInput);
//            } else {
//                throw new DukeException(ERROR_MESSAGE_RANDOM);
//            }
//        } else if (userInput.contains(COMMAND_TENTATIVESCHEDULE)) {
//            if (userInput.trim().substring(0, 17).equals(COMMAND_TENTATIVESCHEDULE)) {
//                return new TentativeScheduleCommand(userInput);
//            } else {
//                throw new DukeException(ERROR_MESSAGE_RANDOM);
//            }
//        } else if (userInput.contains(COMMAND_CONFIRMSCHEDULE)) {
//            if (userInput.trim().substring(0, 15).equals(COMMAND_CONFIRMSCHEDULE)) {
//                return new ConfirmScheduleCommand(userInput);
//            } else {
//                throw new DukeException(ERROR_MESSAGE_RANDOM);
//            }
//        } else if (userInput.contains(COMMAND_CONFIRM)) {
//            if (userInput.trim().substring(0, 7).equals(COMMAND_CONFIRM)) {
//                return new ConfirmScheduleCommand(userInput);
//            } else {
//                throw new DukeException(ERROR_MESSAGE_RANDOM);
//            }
//        } else if (userInput.contains(COMMAND_RECURRING)) {
//            if (userInput.trim().substring(0, 9).equals(COMMAND_RECURRING)) {
//                return new RecurringCommand(userInput);
//            } else {
//                throw new DukeException(ERROR_MESSAGE_RANDOM);
//            }
//        } else if (userInput.contains(COMMAND_DETECTCLASHES)) {
//            if (userInput.trim().substring(0, 11).equals(COMMAND_DETECTCLASHES)) {
//                return new DetectAnomaliesCommand(userInput);
//            } else {
//                throw new DukeException(ERROR_MESSAGE_RANDOM);
//            }
//        } else {
//            throw new DukeException(ERROR_MESSAGE_RANDOM);
//        }
//    }
}