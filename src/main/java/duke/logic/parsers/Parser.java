package duke.logic.parsers;

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
import duke.commands.PromptCommand;
import duke.commands.RecommendationsCommand;
import duke.commands.ReminderCommand;
import duke.commands.RescheduleCommand;
import duke.commands.StaticMapCommand;
import duke.commands.ViewScheduleCommand;
import duke.commons.MessagesPrompt;
import duke.commons.exceptions.DukeException;
import duke.commons.exceptions.DukeUnknownCommandException;


/**
 * Parser for commands entered by the user. It reads from standard input and
 * returns Command objects.
 */
public class Parser {
    /**
     * Parses the userInput and return a Command object.
     *
     * @param input Input created by the ConversationManager object or user input.
     * @return The corresponding Command object.
     * @throws DukeException If userInput is undefined.
     */
    public static Command parseComplexCommand(String input) throws DukeException {
        String commandWord = getCommandWord(input);
        switch (commandWord) {
        case "todo":
            return new AddCommand(ParserUtil.createTodo(input));
        case "deadline":
            return new AddCommand(ParserUtil.createDeadline(input));
        case "done":
            return new MarkDoneCommand(ParserUtil.getIndex(input));
        case "delete":
            return new DeleteCommand(ParserUtil.getIndex(input));
        case "find":
            return new FindCommand(getWord(input));
        case "findtime":
            return new FreeTimeCommand(ParserUtil.getIndex(input));
        case "fetch":
            return new ViewScheduleCommand(ParserTimeUtil.parseStringToDate(getWord(input)));
        case "within":
            return new AddCommand(ParserUtil.createWithin(input));
        case "reschedule":
            return new RescheduleCommand(ParserUtil.getSafeIndex(input), ParserUtil.getScheduleDate(input));
        case "search":
            return new LocationSearchCommand(getWord(input));
        case "busStop":
            return new GetBusStopCommand(getWord(input));
        case "busRoute":
            return new GetBusRouteCommand(getWord(input));
        case "event":
            return new AddCommand(ParserUtil.createEvent(input));
        case "findPath":
            return new FindPathCommand(getWord(input), getHolidayIndexInList(1, input),
                    getHolidayIndexInList(2, input));
        case "recommend":
            return new RecommendationsCommand(ParserUtil.getIndex(input) + 1);
        case "cancel":
            return new PromptCommand(MessagesPrompt.CANCEL_PROMPT);
        case "map":
            return new StaticMapCommand(getWord(input));
        default:
            throw new DukeUnknownCommandException();
        }
    }

    /**
     * Parses the userInput and return a Command object.
     *
     * @param userInput Input created by the ConversationManager object or user input.
     * @return The corresponding Command object.
     * @throws DukeException If userInput is undefined.
     */
    public static Command parseSingleCommand(String userInput) throws DukeException {
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
            return parseComplexCommand(userInput);
        }
    }

    public static Command parsePromptCommand(String prompt) {
        return new PromptCommand(prompt);
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
            throw new DukeUnknownCommandException();
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
