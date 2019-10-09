package duke.parser;

import duke.command.ByeCommand;
import duke.command.Command;

import duke.command.inventorycommands.AddIngredientCommand;
import duke.command.inventorycommands.ListIngredientsCommand;
import duke.exception.DukeException;

import static duke.common.IngredientMessages.COMMAND_ADD_INGREDIENT;
import static duke.common.IngredientMessages.COMMAND_LIST_INGREDIENTS;
import static duke.common.Messages.ERROR_MESSAGE_RANDOM;
import static duke.common.Messages.COMMAND_BYE;


/**
 * Making sense of the user input command.
 */
public class Parser {

    public static Command parse(String userInput) throws DukeException {
        if (userInput.trim().equals(COMMAND_BYE)) {
            return new ByeCommand();
        } else if (userInput.trim().equals(COMMAND_LIST_INGREDIENTS)) {
            return new ListIngredientsCommand(userInput);
        } else if (userInput.contains(COMMAND_ADD_INGREDIENT)) {
            if (userInput.trim().substring(0, 13).equals(COMMAND_ADD_INGREDIENT)) {
                return new AddIngredientCommand(userInput);
            } else {
                throw new DukeException(ERROR_MESSAGE_RANDOM);
            }
        }
        else {
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

    /*
    public static Command parse(String userInput) throws DukeException {
        if (userInput.trim().equals(COMMAND_LIST)) {
            return new ListCommand(userInput);
        } else if (userInput.trim().equals(COMMAND_BYE)) {
            return new ByeCommand();
        } else if (userInput.contains(COMMAND_DONE)) {
            return new DoneCommand(userInput);
        } else if (userInput.contains(COMMAND_DEADLINE)) {
            if (userInput.trim().substring(0, 8).equals(COMMAND_DEADLINE)) {
                return new DeadlineCommand(userInput);
            } else {
                throw new DukeException(ERROR_MESSAGE_RANDOM);
            }
        } else if (userInput.contains(COMMAND_DELETE)) {
            if (userInput.trim().substring(0, 6).equals(COMMAND_DELETE)) {
                return new DeleteCommand(userInput);
            } else {
                throw new DukeException(ERROR_MESSAGE_RANDOM);
            }
        } else if (userInput.contains(COMMAND_EVENT)) {
            if (userInput.trim().substring(0, 5).equals(COMMAND_EVENT)) {
                return new EventCommand(userInput);
            } else {
                throw new DukeException(ERROR_MESSAGE_RANDOM);
            }
        } else if (userInput.contains(COMMAND_TODO)) {
            if (userInput.trim().substring(0, 4).equals(COMMAND_TODO)) {
                return new TodoCommand(userInput);
            } else {
                throw new DukeException(ERROR_MESSAGE_RANDOM);
            }
        } else if (userInput.contains(COMMAND_FIND)) {
            if (userInput.trim().substring(0, 4).equals(COMMAND_FIND)) {
                return new FindCommand(userInput);
            } else {
                throw new DukeException(ERROR_MESSAGE_RANDOM);
            }
        } else if (userInput.contains(COMMAND_DURATION)) {
            if (userInput.trim().substring(0, 5).equals(COMMAND_DURATION)) {
                return new DurationCommand(userInput);
            } else {
                throw new DukeException(ERROR_MESSAGE_RANDOM);
            }
        } else if (userInput.contains(COMMAND_SNOOZE)) {
            if (userInput.trim().substring(0, 6).equals(COMMAND_SNOOZE)) {
                return new SnoozeCommand(userInput);
            } else {
                throw new DukeException(ERROR_MESSAGE_RANDOM);
            }
        } else if (userInput.contains(COMMAND_PERIOD)) {
            if (userInput.trim().substring(0, 6).equals(COMMAND_PERIOD)) {
                return new PeriodCommand(userInput);
            } else {
                throw new DukeException(ERROR_MESSAGE_RANDOM);
            }
        } else if (userInput.contains(COMMAND_REMIND)) {
            if (userInput.trim().substring(0, 9).equals(COMMAND_REMIND)) {
                return new RemindCommand(userInput);
            } else {
                throw new DukeException(ERROR_MESSAGE_RANDOM);
            }
        } else if (userInput.contains(COMMAND_VIEWSCHEDULE)) {
            if (userInput.trim().substring(0, 12).equals(COMMAND_VIEWSCHEDULE)) {
                return new ViewScheduleCommand(userInput);
            } else {
                throw new DukeException(ERROR_MESSAGE_RANDOM);
            }
        } else if (userInput.contains(COMMAND_TENTATIVESCHEDULE)) {
            if (userInput.trim().substring(0, 17).equals(COMMAND_TENTATIVESCHEDULE)) {
                return new TentativeScheduleCommand(userInput);
            } else {
                throw new DukeException(ERROR_MESSAGE_RANDOM);
            }
        } else if (userInput.contains(COMMAND_CONFIRMSCHEDULE)) {
            if (userInput.trim().substring(0, 15).equals(COMMAND_CONFIRMSCHEDULE)) {
                return new ConfirmScheduleCommand(userInput);
            } else {
                throw new DukeException(ERROR_MESSAGE_RANDOM);
            }
        } else if (userInput.contains(COMMAND_CONFIRM)) {
            if (userInput.trim().substring(0, 7).equals(COMMAND_CONFIRM)) {
                return new ConfirmScheduleCommand(userInput);
            } else {
                throw new DukeException(ERROR_MESSAGE_RANDOM);
            }
        } else if (userInput.contains(COMMAND_RECURRING)) {
            if (userInput.trim().substring(0, 9).equals(COMMAND_RECURRING)) {
                return new RecurringCommand(userInput);
            } else {
                throw new DukeException(ERROR_MESSAGE_RANDOM);
            }
        } else if (userInput.contains(COMMAND_DETECTCLASHES)) {
            if (userInput.trim().substring(0, 11).equals(COMMAND_DETECTCLASHES)) {
                return new DetectAnomaliesCommand(userInput);
            } else {
                throw new DukeException(ERROR_MESSAGE_RANDOM);
            }
        } else {
            throw new DukeException(ERROR_MESSAGE_RANDOM);
        }
    }
    */
}
