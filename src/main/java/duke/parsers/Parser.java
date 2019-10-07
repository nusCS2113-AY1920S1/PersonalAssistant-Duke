package duke.parsers;

import duke.commands.AddCommand;
import duke.commands.Command;
import duke.commands.DeleteCommand;
import duke.commands.ExitCommand;
import duke.commands.FindCommand;
import duke.commands.FreeTimeCommand;
import duke.commands.GetBusStopCommand;
import duke.commands.FindPathCommand;
import duke.commands.HelpCommand;
import duke.commands.ListCommand;
import duke.commands.LocationSearchCommand;
import duke.commands.MapCommand;
import duke.commands.MarkDoneCommand;
import duke.commands.ReminderCommand;
import duke.commands.RescheduleCommand;
import duke.commands.ViewScheduleCommand;
import duke.commons.DukeException;
import duke.commons.MessageUtil;


/**
 * Parser for duke.commands entered by the duke.Duke user. It reads from standard input and
 * returns Command objects.
 */
public class Parser {
    /**
     * Parses the userInput and return a Command object.
     *
     * @param userInput The userInput read by the user interface.
     * @return The corresponding Command object.
     * @throws DukeException If userInput is undefined.
     */
    public static Command parse(String userInput) throws DukeException {
        String commandWord = getCommandWord(userInput);
        switch (commandWord) {
        case "bye":
            return new ExitCommand();
        case "todo":
            return new AddCommand(ParserUtil.createTodo(userInput));
        case "deadline":
            return new AddCommand(ParserUtil.createDeadline(userInput));
        case "event":
            return new AddCommand(ParserUtil.createEvent(userInput));
        case "list":
            return new ListCommand();
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
        case "help":
            return new HelpCommand();
        case "search":
            return new LocationSearchCommand(getWord(userInput));
        case "bus":
            return new GetBusStopCommand(getWord(userInput));
        case "holiday":
            return new AddCommand(ParserUtil.createHoliday(userInput));
        case "findPath":
            return new FindPathCommand(getWord(userInput),  getHolidayIndexInList(1, userInput),
                    getHolidayIndexInList(2, userInput));
        case "map":
            return new MapCommand();
        default:
            throw new DukeException(MessageUtil.UNKNOWN_COMMAND);
        }
    }

    /**
     * Gets command word from the userInput.
     *
     * @param userInput The userInput read by the user interface.
     * @return The command word.
     */
    private static String getCommandWord(String userInput) {
        return userInput.strip().split(" ")[0];
    }

    /**
     * Gets word from the userInput.
     *
     * @param userInput The userInput read by the user interface.
     * @return The word.
     */
    private static String getWord(String userInput) throws DukeException {
        try {
            return userInput.strip().split(" ", 2)[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new DukeException(MessageUtil.INVALID_FORMAT);
        }
    }

    private static String getHolidayIndexInList(int index, String userInput) {
        if (index == 1) {
            return userInput.strip().split(" ", 4)[2];
        } else {
            return userInput.strip().split(" ", 4)[3];
        }
    }
}
