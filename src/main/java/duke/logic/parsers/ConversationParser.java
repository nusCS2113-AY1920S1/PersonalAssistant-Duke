package duke.logic.parsers;

import duke.commons.exceptions.DukeUnknownCommandException;
import duke.logic.conversations.Conversation;
import duke.logic.conversations.DeleteConversation;
import duke.logic.conversations.FindConversation;
import duke.logic.conversations.FindPathConversation;
import duke.logic.conversations.FreeTimeConversation;
import duke.logic.conversations.GetBusStopConversation;
import duke.logic.conversations.MarkDoneConversation;
import duke.logic.conversations.RouteAddConversation;
import duke.logic.conversations.RouteDeleteConversation;
import duke.logic.conversations.RouteEditConversation;
import duke.logic.conversations.RouteGenerateConversation;
import duke.logic.conversations.RouteListConversation;
import duke.logic.conversations.RouteNodeAddConversation;
import duke.logic.conversations.RouteNodeDeleteConversation;
import duke.logic.conversations.RouteNodeEditConversation;
import duke.logic.conversations.RouteNodeListConversation;
import duke.logic.conversations.SearchConversation;

/**
 * Parser for conversations. Selects conversation based on user input.
 */
public class ConversationParser {
    /**
     * Parses the input and returns a Conversation object.
     * @param input The user input from Ui.
     * @return A conversation object.
     * @throws DukeUnknownCommandException If input is undefined.
     */
    public static Conversation parse(String input) throws DukeUnknownCommandException {
        switch (input) {
        case "done":
            return new MarkDoneConversation();
        case "delete":
            return new DeleteConversation();
        case "findtime":
            return new FreeTimeConversation();
        case "busStop":
            return new GetBusStopConversation();
        case "findPath":
            return new FindPathConversation();
        case "find":
            return new FindConversation();
        case "search":
            return new SearchConversation();
        case "routeAdd":
            return new RouteAddConversation();
        case "routeDelete":
            return new RouteDeleteConversation();
        case "routeEdit":
            return new RouteEditConversation();
        case "routeGenerate":
            return new RouteGenerateConversation();
        case "routeShow":
            return new RouteListConversation();
        case "routeNodeAdd":
            return new RouteNodeAddConversation();
        case "routeNodeDelete":
            return new RouteNodeDeleteConversation();
        case "routeNodeEdit":
            return new RouteNodeEditConversation();
        case "routeNodeShow":
            return new RouteNodeListConversation();
        default:
            throw new DukeUnknownCommandException();
        }
    }
}
