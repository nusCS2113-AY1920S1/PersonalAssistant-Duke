package duke.parser;

import duke.command.ByeCommand;
import duke.command.Command;
import duke.command.DeadlineCommand;
import duke.command.DeleteCommand;
import duke.command.DoneCommand;
import duke.command.EventCommand;
import duke.command.FindCommand;
import duke.command.ListCommand;
import duke.command.TodoCommand;
import duke.command.TentativeScheduleCommand;
import duke.command.ConfirmScheduleCommand;
import duke.command.DurationCommand;
import duke.command.PeriodCommand;
import duke.command.ViewScheduleCommand;
import duke.command.RemindCommand;
import duke.command.RecurringCommand;
import duke.command.SnoozeCommand;

import duke.exception.DukeException;

import static duke.common.Messages.ERROR_MESSAGE_RANDOM;
import static duke.common.Messages.COMMAND_BYE;
import static duke.common.Messages.COMMAND_DEADLINE;
import static duke.common.Messages.COMMAND_DELETE;
import static duke.common.Messages.COMMAND_DONE;
import static duke.common.Messages.COMMAND_EVENT;
import static duke.common.Messages.COMMAND_FIND;
import static duke.common.Messages.COMMAND_LIST;
import static duke.common.Messages.COMMAND_TODO;
import static duke.common.Messages.COMMAND_TENTATIVESCHEDULE;
import static duke.common.Messages.COMMAND_CONFIRM;
import static duke.common.Messages.COMMAND_CONFIRMSCHEDULE;
import static duke.common.Messages.COMMAND_VIEWSCHEDULE;
import static duke.common.Messages.COMMAND_PERIOD;
import static duke.common.Messages.COMMAND_REMIND;
import static duke.common.Messages.COMMAND_DURATION;
import static duke.common.Messages.COMMAND_RECURRING;
import static duke.common.Messages.COMMAND_SNOOZE;

/**
 * Making sense of the user input command.
 */
public class Parser {

    /**
     * Processes the different user input command.
     * @param userInputCommand String containing input command from user
     * @return the different command object corresponding to the user input
     * @throws DukeException if Duke cannot recognise the user input
     */
    public static Command parse(String userInputCommand) throws DukeException {
        if (userInputCommand.trim().equals(COMMAND_LIST)) {
            return new ListCommand(userInputCommand);
        } else if (userInputCommand.trim().equals(COMMAND_BYE)) {
            return new ByeCommand();
        } else if (userInputCommand.contains(COMMAND_DONE)) {
            return new DoneCommand(userInputCommand);
        } else if (userInputCommand.contains(COMMAND_DEADLINE)) {
            if (userInputCommand.trim().substring(0, 8).equals(COMMAND_DEADLINE)) {
                return new DeadlineCommand(userInputCommand);
            } else {
                throw new DukeException(ERROR_MESSAGE_RANDOM);
            }
        } else if (userInputCommand.contains(COMMAND_DELETE)) {
            if (userInputCommand.trim().substring(0, 6).equals(COMMAND_DELETE)) {
                return new DeleteCommand(userInputCommand);
            } else {
                throw new DukeException(ERROR_MESSAGE_RANDOM);
            }
        } else if (userInputCommand.contains(COMMAND_EVENT)) {
            if (userInputCommand.trim().substring(0, 5).equals(COMMAND_EVENT)) {
                return new EventCommand(userInputCommand);
            } else {
                throw new DukeException(ERROR_MESSAGE_RANDOM);
            }
        } else if (userInputCommand.contains(COMMAND_TODO)) {
            if (userInputCommand.trim().substring(0, 4).equals(COMMAND_TODO)) {
                return new TodoCommand(userInputCommand);
            } else {
                throw new DukeException(ERROR_MESSAGE_RANDOM);
            }
        } else if (userInputCommand.contains(COMMAND_FIND)) {
            if (userInputCommand.trim().substring(0, 4).equals(COMMAND_FIND)) {
                return new FindCommand(userInputCommand);
            } else {
                throw new DukeException(ERROR_MESSAGE_RANDOM);
            }
        } else if (userInputCommand.contains(COMMAND_DURATION)) {
            if (userInputCommand.trim().substring(0, 5).equals(COMMAND_DURATION)) {
                return new DurationCommand(userInputCommand);
            } else {
                throw new DukeException(ERROR_MESSAGE_RANDOM);
            }
        } else if (userInputCommand.contains(COMMAND_SNOOZE)) {
            if (userInputCommand.trim().substring(0, 6).equals(COMMAND_SNOOZE)) {
                return new SnoozeCommand(userInputCommand);
            } else {
                throw new DukeException(ERROR_MESSAGE_RANDOM);
            }
        } else if (userInputCommand.contains(COMMAND_PERIOD)) {
            if (userInputCommand.trim().substring(0, 6).equals(COMMAND_PERIOD)) {
                return new PeriodCommand(userInputCommand);
            } else {
                throw new DukeException(ERROR_MESSAGE_RANDOM);
            }
        } else if (userInputCommand.contains(COMMAND_REMIND)) {
            if (userInputCommand.trim().substring(0, 9).equals(COMMAND_REMIND)) {
                return new RemindCommand(userInputCommand);
            } else {
                throw new DukeException(ERROR_MESSAGE_RANDOM);
            }
        } else if (userInputCommand.contains(COMMAND_VIEWSCHEDULE)) {
            if (userInputCommand.trim().substring(0, 12).equals(COMMAND_VIEWSCHEDULE)) {
                return new ViewScheduleCommand(userInputCommand);
            } else {
                throw new DukeException(ERROR_MESSAGE_RANDOM);
            }
        } else if (userInputCommand.contains(COMMAND_TENTATIVESCHEDULE)) {
            if (userInputCommand.trim().substring(0, 17).equals(COMMAND_TENTATIVESCHEDULE)) {
                return new TentativeScheduleCommand(userInputCommand);
            } else {
                throw new DukeException(ERROR_MESSAGE_RANDOM);
            }
        } else if (userInputCommand.contains(COMMAND_CONFIRMSCHEDULE)) {
            if (userInputCommand.trim().substring(0, 15).equals(COMMAND_CONFIRMSCHEDULE)) {
                return new ConfirmScheduleCommand(userInputCommand);
            } else {
                throw new DukeException(ERROR_MESSAGE_RANDOM);
            }
        } else if (userInputCommand.contains(COMMAND_CONFIRM)) {
            if (userInputCommand.trim().substring(0, 7).equals(COMMAND_CONFIRM)) {
                return new ConfirmScheduleCommand(userInputCommand);
            } else {
                throw new DukeException(ERROR_MESSAGE_RANDOM);
            }
        } else if (userInputCommand.contains(COMMAND_RECURRING)) {
            if (userInputCommand.trim().substring(0, 9).equals(COMMAND_RECURRING)) {
                return new RecurringCommand(userInputCommand);
            } else {
                throw new DukeException(ERROR_MESSAGE_RANDOM);
            }
        } else {
            throw new DukeException(ERROR_MESSAGE_RANDOM);
        }
    }
}
