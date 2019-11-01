package duke.logic.parsers;

import duke.commons.Messages;
import duke.commons.exceptions.ParseException;
import duke.logic.commands.Command;
import duke.logic.commands.EditorCommand;
import duke.logic.commands.ExitCommand;
import duke.logic.commands.HelpCommand;
import duke.logic.commands.ListCommand;
import duke.logic.commands.ListItineraryCommand;
import duke.logic.commands.ProfileSetPreferenceCommand;
import duke.logic.commands.ProfileShowCommand;
import duke.logic.commands.RouteNodeNeighboursCommand;
import duke.logic.commands.RouteNodeShowCommand;
import duke.logic.commands.RouteShowCommand;
import duke.logic.commands.ShowItineraryCommand;
import duke.logic.commands.ViewScheduleCommand;
import duke.logic.parsers.commandparser.AddEventParser;
import duke.logic.parsers.commandparser.AddProfileParser;
import duke.logic.parsers.commandparser.CreateNewItineraryParser;
import duke.logic.parsers.commandparser.DeleteParser;
import duke.logic.parsers.commandparser.DoneParser;
import duke.logic.parsers.commandparser.FindParser;
import duke.logic.parsers.commandparser.GetBusRouteParser;
import duke.logic.parsers.commandparser.GetBusStopParser;
import duke.logic.parsers.commandparser.LocationSearchParser;
import duke.logic.parsers.commandparser.QuickEditParser;
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
     * @throws ParseException If userInput is undefined.
     */
    public static Command parseComplexCommand(String input) throws ParseException {
        String commandWord = getCommandWord(input);

        switch (commandWord) {
        case "bye":
            return new ExitCommand();
        case "list":
            return new ListCommand();
        case "help":
            return new HelpCommand();
        case "view":
            return new ViewScheduleCommand();
        case "edit":
            return new EditorCommand();
        case "e":
            return new QuickEditParser(getWord(input)).parse();
        case "done":
            return new DoneParser(getWord(input)).parse();
        case "delete":
            return new DeleteParser(getWord(input)).parse();
        case "find":
            return new FindParser(getWord(input)).parse();
        case "search":
            return new LocationSearchParser(getWord(input)).parse();
        case "busStop":
            return new GetBusStopParser(getWord(input)).parse();
        case "busRoute":
            return new GetBusRouteParser(getWord(input)).parse();
        case "event":
            return new AddEventParser(input).parse();
        case "recommend":
            return new RecommendationParser(input).parse();
        case "cancel":
            return PromptParser.parseCommand(Messages.PROMPT_CANCEL);
        case "map":
            return new StaticMapParser(getWord(input)).parse();
        case "routeAdd":
            return new RouteAddParser(getWord(input)).parse();
        case "routeNodeAdd":
            return new RouteNodeAddParser(getWord(input)).parse();
        case "routeEdit":
            return new RouteEditParser(getWord(input)).parse();
        case "routeNodeEdit":
            return new RouteNodeEditParser(getWord(input)).parse();
        case "routeDelete":
            return new RouteDeleteParser(getWord(input)).parse();
        case "routeNodeDelete":
            return new RouteNodeDeleteParser(getWord(input)).parse();
        case "routeList":
            return new RouteListParser(getWord(input)).parse();
        case "routeNodeList":
            return new RouteNodeListParser(getWord(input)).parse();
        case "routeGenerate":
            return ParserUtil.createRouteGenerateCommand(getWord(input));
        case "routeShow":
            return new RouteShowCommand(ParserUtil.getIntegerIndexInList(0, 2, getWord(input)));
        case "routeNodeShow":
            return new RouteNodeShowCommand(ParserUtil.getIntegerIndexInList(0, 2, getWord(input)),
                    ParserUtil.getIntegerIndexInList(1, 2, getWord(input)));
        case "routeNodeNearby":
            return new RouteNodeNeighboursCommand(ParserUtil.getIntegerIndexInList(0, 2, getWord(input)),
                    ParserUtil.getIntegerIndexInList(1, 2, getWord(input)));
        //case "addThisList":
            //return new AddSampleItineraryCommand();
        case "newItinerary":
            return new CreateNewItineraryParser(input).parse();
        case "listItinerary":
            return new ListItineraryCommand();
        case "showItinerary":
            return new ShowItineraryCommand(getWord(input));
        case "profile":
            return new AddProfileParser(getWord(input)).parse();
        case "profileShow":
            return new ProfileShowCommand();
        case "profileSet":
            return new ProfileSetPreferenceCommand(ParserUtil.getFieldInList(0,2,getWord(input)),
                        ParserUtil.getFieldInList(1,2,getWord(input)));
        default:
            throw new ParseException(Messages.ERROR_INPUT_INVALID_FORMAT);
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
    private static String getWord(String userInput) throws ParseException {
        try {
            return userInput.strip().split(" ", 2)[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ParseException(Messages.ERROR_DESCRIPTION_EMPTY);
        }
    }
}
