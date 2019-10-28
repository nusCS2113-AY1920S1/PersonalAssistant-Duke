package duke.logic.parsers;

import duke.commons.Messages;
import duke.commons.exceptions.DukeException;
import duke.commons.exceptions.DukeUnknownCommandException;
import duke.logic.commands.AddCommand;
import duke.logic.commands.AddSampleItineraryCommand;
import duke.logic.commands.Command;
import duke.logic.commands.DeleteCommand;
import duke.logic.commands.EditorCommand;
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
import duke.logic.commands.QuickEditCommand;
import duke.logic.commands.RecommendationsCommand;
import duke.logic.commands.RouteDeleteCommand;
import duke.logic.commands.RouteEditCommand;
import duke.logic.commands.RouteListCommand;
import duke.logic.commands.RouteNodeDeleteCommand;
import duke.logic.commands.RouteNodeEditCommand;
import duke.logic.commands.RouteNodeListCommand;
import duke.logic.commands.StaticMapCommand;
import duke.logic.commands.ViewScheduleCommand;

/**
 * Parser for commands entered by the user. It reads from standard input and returns Command objects.
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
        String inputBody = getWord(input);

        switch (commandWord) {
        case "bye":
            return new ExitCommand();
        case "list":
            return new ListCommand();
        case "help":
            return new HelpCommand();
        case "fetch":
            return new ViewScheduleCommand();
        case "edit":
            return new EditorCommand();
        case "e":
            return new QuickEditCommand(ParserUtil.getIntegerIndexInList(0, 4, inputBody),
                    ParserUtil.getFieldInList(1, 4, inputBody),
                    ParserUtil.getFieldInList(2, 4, inputBody),
                    ParserUtil.getFieldInList(3, 4, inputBody));
        case "done":
            return new MarkDoneCommand(ParserUtil.getIntegerIndexInList(0, 1, inputBody));
        case "delete":
            return new DeleteCommand(ParserUtil.getIntegerIndexInList(0, 1, inputBody));
        case "find":
            return new FindCommand(getWord(inputBody));
        case "findtime":
            return new FreeTimeCommand(ParserUtil.getIntegerIndexInList(0, 1, inputBody));
        case "search":
            return new LocationSearchCommand(inputBody);
        case "busStop":
            return new GetBusStopCommand(inputBody);
        case "busRoute":
            return new GetBusRouteCommand(inputBody);
        case "event":
            return new AddCommand(ParserUtil.createEvent(input));
        case "findPath":
            return new FindPathCommand(ParserUtil.getFieldInList(0, 3, inputBody),
                    ParserUtil.getIntegerIndexInList(1, 3, inputBody),
                    ParserUtil.getIntegerIndexInList(2, 3, inputBody));
        case "recommend":
            return new RecommendationsCommand(ParserUtil.createRecommendation(input));
        case "cancel":
            return new PromptCommand(Messages.PROMPT_CANCEL);
        case "map":
            return new StaticMapCommand(inputBody);
        case "routeAdd":
            return ParserUtil.createRouteAddCommand(inputBody);
        case "routeNodeAdd":
            return ParserUtil.createRouteNodeAddCommand(inputBody);
        case "routeEdit":
            return new RouteEditCommand(ParserUtil.getIntegerIndexInList(0, 3, inputBody),
                    ParserUtil.getFieldInList(1, 3, inputBody),
                    ParserUtil.getFieldInList(2, 3, inputBody));
        case "routeNodeEdit":
            return new RouteNodeEditCommand(ParserUtil.getIntegerIndexInList(0, 3, inputBody),
                    ParserUtil.getIntegerIndexInList(1, 3, inputBody),
                    ParserUtil.getFieldInList(2, 4, inputBody),
                    ParserUtil.getFieldInList(3, 4, inputBody));
        case "routeDelete":
            return new RouteDeleteCommand(ParserUtil.getIntegerIndexInList(0, 1, inputBody));
        case "routeNodeDelete":
            return new RouteNodeDeleteCommand(ParserUtil.getIntegerIndexInList(0, 2, inputBody),
                    ParserUtil.getIntegerIndexInList(1, 2, inputBody));
        case "routeShow":
            return new RouteListCommand(ParserUtil.getIntegerIndexInList(0, 1, inputBody));
        case "routeNodeShow":
            return new RouteNodeListCommand(ParserUtil.getIntegerIndexInList(0, 2, inputBody),
                    ParserUtil.getIntegerIndexInList(1, 2, inputBody));
        case "routeGenerate":
            return ParserUtil.createRouteGenerateCommand(inputBody);
        case "addThisList":
            return new AddSampleItineraryCommand();
        default:
            throw new DukeUnknownCommandException();
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
            return userInput;
        }
    }

    /**
     * Gets the field at a given index in a String, delimited by whitespace.
     *
     * @param index The index of the field.
     * @param userInput The userInput read by the user interface.
     * @return The field.
     */
    private static String getEventIndexInList(int index, String userInput) {
        if (index == 1) {
            return userInput.strip().split(" ", 4)[2];
        } else {
            return userInput.strip().split(" ", 4)[3];
        }
    }
}
