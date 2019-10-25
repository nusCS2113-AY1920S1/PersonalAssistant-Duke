package duke.logic.parsers;

import duke.commons.Messages;
import duke.commons.exceptions.DukeException;
import duke.logic.conversations.Conversation;
import duke.logic.conversations.DeleteConversation;
import duke.logic.conversations.FindConversation;
import duke.logic.conversations.FindPathConversation;
import duke.logic.conversations.FreeTimeConversation;
import duke.logic.conversations.GetBusStopConversation;
import duke.logic.conversations.MarkDoneConversation;
import duke.logic.conversations.SearchConversation;
import duke.logic.conversations.ToDoConversation;

/**
 * Parser for conversations. Selects conversation based on user input.
 */
public class ConversationParser {
    /**
     * Parses the input and returns a Conversation object.
     * @param input The user input from Ui.
     * @return A conversation object.
     * @throws DukeException If input is undefined.
     */
    public static Conversation parse(String input) throws DukeException {
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
        case "todo":
            return new ToDoConversation();
        case "find":
            return new FindConversation();
        case "search":
            return new SearchConversation();
        default:
            throw new DukeException(Messages.UNKNOWN_COMMAND);
        }
    }
}
