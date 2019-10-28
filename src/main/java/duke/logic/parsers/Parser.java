package duke.logic.parsers;

import duke.commons.exceptions.DukeException;
import duke.commons.exceptions.DukeUnknownCommandException;
import duke.logic.commands.AddSampleItineraryCommand;
import duke.logic.commands.Command;
import duke.logic.commands.EditorCommand;
import duke.logic.commands.ExitCommand;
import duke.logic.commands.HelpCommand;
import duke.logic.commands.ListCommand;
import duke.logic.commands.ViewScheduleCommand;
import duke.logic.parsers.commandparser.AddEventParser;
import duke.logic.parsers.commandparser.AddProfileParser;
import duke.logic.parsers.commandparser.DeleteParser;
import duke.logic.parsers.commandparser.DoneParser;
import duke.logic.parsers.commandparser.FindParser;
import duke.logic.parsers.commandparser.FindPathParser;
import duke.logic.parsers.commandparser.FreeTimeParser;
import duke.logic.parsers.commandparser.GetBusRouteParser;
import duke.logic.parsers.commandparser.GetBusStopParser;
import duke.logic.parsers.commandparser.LocationSearchParser;
import duke.logic.parsers.commandparser.PromptParser;
import duke.logic.parsers.commandparser.RecommendationParser;
import duke.logic.parsers.commandparser.RouteAddParser;
import duke.logic.parsers.commandparser.RouteDeleteParser;
import duke.logic.parsers.commandparser.RouteEditParser;
import duke.logic.parsers.commandparser.RouteListParser;
import duke.logic.parsers.commandparser.RouteNodeAddParser;
import duke.logic.parsers.commandparser.RouteNodeDeleteParser;
import duke.logic.parsers.commandparser.RouteNodeEditParser;
import duke.logic.parsers.commandparser.RouteNodeListParser;
import duke.logic.parsers.commandparser.StaticMapParser;

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
        case "done":
            return new DoneParser(input).parse();
        case "delete":
            return new DeleteParser(input).parse();
        case "find":
            return new FindParser(input).parse();
        case "findtime":
            return new FreeTimeParser(input).parse();
        case "search":
            return new LocationSearchParser(input).parse();
        case "busStop":
            return new GetBusStopParser(input).parse();
        case "busRoute":
            return new GetBusRouteParser(input).parse();
        case "event":
            return new AddEventParser(input).parse();
        case "findPath":
            return new FindPathParser(input).parse();
        case "recommend":
            return new RecommendationParser(input).parse();
        case "cancel":
            return new PromptParser().parse();
        case "map":
            return new StaticMapParser(input).parse();
        case "routeAdd":
            return new RouteAddParser(input).parse();
        case "routeNodeAdd":
            return new RouteNodeAddParser(input).parse();
        case "routeEdit":
            return new RouteEditParser(input).parse();
        case "routeNodeEdit":
            return new RouteNodeEditParser(input).parse();
        case "routeDelete":
            return new RouteDeleteParser(input).parse();
        case "routeNodeDelete":
            return new RouteNodeDeleteParser(input).parse();
        case "routeShow":
            return new RouteListParser(input).parse();
        case "routeNodeShow":
            return new RouteNodeListParser(input).parse();
        case "addThisList":
            return new AddSampleItineraryCommand();
        case "profile":
            return new AddProfileParser(input).parse();
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

}
