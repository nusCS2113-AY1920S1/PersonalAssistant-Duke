package duke.logic.parsers;

import duke.commons.MessagesPrompt;
import duke.commons.exceptions.DukeException;
import duke.commons.exceptions.DukeUnknownCommandException;
import duke.logic.commands.AddCommand;
import duke.logic.commands.AddSampleItineraryCommand;
import duke.logic.commands.Command;
import duke.logic.commands.DeleteCommand;
import duke.logic.commands.ExitCommand;
import duke.logic.commands.FindCommand;
import duke.logic.commands.FindPathCommand;
import duke.logic.commands.FreeTimeCommand;
import duke.logic.commands.GetBusRouteCommand;
import duke.logic.commands.GetBusStopCommand;
import duke.logic.commands.HelpCommand;
import duke.logic.commands.ListCommand;
import duke.logic.commands.LocationSearchCommand;
import duke.logic.commands.MarkDoneCommand;
import duke.logic.commands.PromptCommand;
import duke.logic.commands.RecommendationsCommand;
import duke.logic.commands.RouteAddCommand;
import duke.logic.commands.RouteDeleteCommand;
import duke.logic.commands.RouteEditCommand;
import duke.logic.commands.RouteListCommand;
import duke.logic.commands.RouteNodeDeleteCommand;
import duke.logic.commands.RouteNodeEditCommand;
import duke.logic.commands.RouteNodeListCommand;
import duke.logic.commands.StaticMapCommand;
import duke.logic.commands.ViewScheduleCommand;

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
        case "done":
            return new MarkDoneCommand(ParserUtil.getIndex(input));
        case "delete":
            return new DeleteCommand(ParserUtil.getIndex(input));
        case "find":
            return new FindCommand(getWord(input));
        case "findtime":
            return new FreeTimeCommand(ParserUtil.getIndex(input));
        case "search":
            return new LocationSearchCommand(getWord(input));
        case "busStop":
            return new GetBusStopCommand(getWord(input));
        case "busRoute":
            return new GetBusRouteCommand(getWord(input));
        case "event":
            return new AddCommand(ParserUtil.createEvent(input));
        case "findPath":
            return new FindPathCommand(input.strip().split(" ")[1], getEventIndexInList(1, input),
                    getEventIndexInList(2, input));
        case "recommend":
            return new RecommendationsCommand(ParserUtil.createRecommendation(input));
        case "cancel":
            return new PromptCommand(MessagesPrompt.CANCEL_PROMPT);
        case "map":
            return new StaticMapCommand(getWord(input));
        case "routeAdd":
            return new RouteAddCommand(getWord(input));
        case "routeNodeAdd":
            return ParserUtil.createRouteNodeAddCommand(getWord(input));
        case "routeEdit":
            return new RouteEditCommand(ParserUtil.getFirstIndex(getWord(input)), getEventIndexInList(1, input),
                    getEventIndexInList(0, input));
        case "routeNodeEdit":
            return new RouteNodeEditCommand(ParserUtil.getFirstIndex(getWord(input)),
                    ParserUtil.getSecondIndex(getWord(input)), ParserUtil.getFieldInList(3, 4, getWord(input)),
                    ParserUtil.getFieldInList(4, 4, getWord(input)));
        case "routeDelete":
            return new RouteDeleteCommand(ParserUtil.getIndex(input));
        case "routeNodeDelete":
            return new RouteNodeDeleteCommand(ParserUtil.getFirstIndex(getWord(input)),
                    ParserUtil.getSecondIndex(getWord(input)));
        case "routeShow":
            return new RouteListCommand(ParserUtil.getIndex(getWord(input)));
        case "routeNodeShow":
            return new RouteNodeListCommand(ParserUtil.getFirstIndex(getWord(input)),
                    ParserUtil.getSecondIndex(getWord(input)));
        case "addThisList":
            return new AddSampleItineraryCommand();
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
        case "help":
            return new HelpCommand();
        case "fetch":
            return new ViewScheduleCommand();
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

    private static String getEventIndexInList(int index, String userInput) {
        if (index == 1) {
            return userInput.strip().split(" ", 4)[2];
        } else {
            return userInput.strip().split(" ", 4)[3];
        }
    }
}
