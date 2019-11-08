package sgtravel.logic.parsers;

import sgtravel.commons.Messages;
import sgtravel.commons.exceptions.ParseException;
import sgtravel.logic.conversations.Conversation;
import sgtravel.logic.conversations.DeleteConversation;
import sgtravel.logic.conversations.FindConversation;
import sgtravel.logic.conversations.GetBusStopConversation;
import sgtravel.logic.conversations.MarkDoneConversation;
import sgtravel.logic.conversations.RouteAddConversation;
import sgtravel.logic.conversations.RouteDeleteConversation;
import sgtravel.logic.conversations.RouteEditConversation;
import sgtravel.logic.conversations.RouteGenerateConversation;
import sgtravel.logic.conversations.RouteListConversation;
import sgtravel.logic.conversations.RouteNodeAddConversation;
import sgtravel.logic.conversations.RouteNodeDeleteConversation;
import sgtravel.logic.conversations.SearchConversation;
import sgtravel.logic.conversations.SetupProfileConversation;

/**
 * Parser for conversations. Selects conversation based on user input.
 */
public class ConversationParser {

    /**
     * Parses the input and returns a Conversation object.
     * @param input The user input from Ui.
     * @return A conversation object.
     * @throws ParseException If input is undefined.
     */
    public static Conversation parse(String input) throws ParseException {
        switch (input) {
        case "done":
            return new MarkDoneConversation();
        case "delete":
            return new DeleteConversation();
        case "busStop":
            return new GetBusStopConversation();
        case "find":
            return new FindConversation();
        case "search":
            return new SearchConversation();
        case "profile":
            return new SetupProfileConversation();
        case "routeDelete":
            return new RouteDeleteConversation();
        case "routeAdd":
            return new RouteAddConversation();
        case "routeEdit":
            return new RouteEditConversation();
        case "routeGenerate":
            return new RouteGenerateConversation();
        case "routeList":
            return new RouteListConversation();
        case "routeNodeAdd":
            return new RouteNodeAddConversation();
        case "routeNodeDelete":
            return new RouteNodeDeleteConversation();
        default:
            throw new ParseException(Messages.ERROR_INPUT_INVALID_FORMAT);
        }
    }
}
