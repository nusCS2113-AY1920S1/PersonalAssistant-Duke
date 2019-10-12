package duke.parsers;

import duke.commands.AddCommand;
import duke.commands.Command;
import duke.commands.DeleteCommand;
import duke.commands.ExitCommand;
import duke.commands.FindCommand;
import duke.commands.FindPathCommand;
import duke.commands.FreeTimeCommand;
import duke.commands.GetBusRouteCommand;
import duke.commands.GetBusStopCommand;
import duke.commands.HelpCommand;
import duke.commands.ListCommand;
import duke.commands.LocationSearchCommand;
import duke.commands.MarkDoneCommand;
import duke.commands.RecommendationsCommand;
import duke.commands.ReminderCommand;
import duke.commands.RescheduleCommand;
import duke.commands.ViewScheduleCommand;
import duke.commons.exceptions.DukeException;
import duke.commons.Messages;
import duke.model.ModelManager;
import duke.ui.Ui;


/**
 * Parser for duke.commands entered by the duke.Duke user. It reads from standard input and
 * returns Command objects.
 */
public class Parser {
    private ConversationManager conversationManager = new ConversationManager();
    private String result = null;

    /**
     * Tries to parse input and start or continue a conversation with Duke.
     * @param input The user input
     * @param ui The ui object
     * @return Command The command made by parser, if applicable
     */
    public Command parse(String input, Ui ui) throws DukeException {
        if (input != null) {
            if (parseSingleCommand(input) != null) {
                return parseSingleCommand(input);
            }

            conversationManager.converse(input, ui);
            if (conversationManager.isFinished()) {
                result = conversationManager.getResult();
                if (result != null) {
                    return createCommand(result);
                } else {
                    return null;
                }
            } else {
                return null;
            }

        } else {
            return null;
        }
    }

    /**
     * Parses the userInput and return a Command object.
     *
     * @param userInput The userInput read by the user interface.
     * @return The corresponding Command object.
     * @throws DukeException If userInput is undefined.
     */
    public static Command createCommand(String userInput) throws DukeException {
        String commandWord = getCommandWord(userInput);
        switch (commandWord) {
        case "todo":
            return new AddCommand(ParserUtil.createTodo(userInput));
        case "deadline":
            return new AddCommand(ParserUtil.createDeadline(userInput));
        case "event":
            return new AddCommand(ParserUtil.createEvent(userInput));
        case "done":
            return new MarkDoneCommand(ParserUtil.getIndex(userInput));
        case "delete":
            return new DeleteCommand(ParserUtil.getIndex(userInput));
        case "find":
            return new FindCommand(getWord(userInput));
        case "reminder":
            return new ReminderCommand();
        case "findtime":
            return new FreeTimeCommand(ParserUtil.getIndex(userInput));
        case "fetch":
            return new ViewScheduleCommand(ParserTimeUtil.parseStringToDate(getWord(userInput)));
        case "within":
            return new AddCommand(ParserUtil.createWithin(userInput));
        case "reschedule":
            return new RescheduleCommand(ParserUtil.getSafeIndex(userInput), ParserUtil.getScheduleDate(userInput));
        case "repeat":
            return new AddCommand(ParserUtil.createRecurringTask(userInput));
        case "fixed":
            return new AddCommand(ParserUtil.createFixed(userInput));
        case "search":
            return new LocationSearchCommand(getWord(userInput));
        case "busStop":
            return new GetBusStopCommand(getWord(userInput));
        case "busRoute":
            return new GetBusRouteCommand(getWord(userInput));
        case "holiday":
            return new AddCommand(ParserUtil.createHoliday(userInput));
        case "findPath":
            return new FindPathCommand(getWord(userInput),  getHolidayIndexInList(1, userInput),
                    getHolidayIndexInList(2, userInput));
        case "recommend":
            return new RecommendationsCommand(getWord(userInput));
        default:
            throw new DukeException(Messages.UNKNOWN_COMMAND);
        }
    }

    private Command parseSingleCommand(String userInput) {
        switch (userInput) {
        case "bye":
            return new ExitCommand();
        case "list":
            return new ListCommand();
        case "reminder":
            return new ReminderCommand();
        case "help":
            return new HelpCommand();
        default:
            return null;
        }
    }

    /**
     * Gets command word from the userInput.
     *
     * @param userInput The userInput read by the user interface.
     * @return The command word.
     */
    private static String getCommandWord(String userInput) {
        return userInput.strip().split(" & ")[0];
    }

    /**
     * Gets word from the userInput.
     *
     * @param userInput The userInput read by the user interface.
     * @return The word.
     */
    private static String getWord(String userInput) throws DukeException {
        try {
            return userInput.strip().split(" & ", 2)[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new DukeException(Messages.INVALID_FORMAT);
        }
    }

    private static String getHolidayIndexInList(int index, String userInput) {
        if (index == 1) {
            return userInput.strip().split(" & ", 4)[2];
        } else {
            return userInput.strip().split(" & ", 4)[3];
        }
    }

    public String getPrompt() {
        return conversationManager.getPrompt();
    }
}
