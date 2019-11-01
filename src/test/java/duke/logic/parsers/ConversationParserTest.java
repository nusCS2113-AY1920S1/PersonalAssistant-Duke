package duke.logic.parsers;

import duke.commons.exceptions.DukeUnknownCommandException;
import duke.logic.conversations.DeleteConversation;
import duke.logic.conversations.FindConversation;
import duke.logic.conversations.GetBusStopConversation;
import duke.logic.conversations.MarkDoneConversation;
import duke.logic.conversations.SearchConversation;

import duke.model.lists.RouteList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConversationParserTest {

    @Test
    void parse() throws DukeUnknownCommandException {
        assertTrue(ConversationParser.parse("done", new RouteManager(new RouteList()))
                instanceof MarkDoneConversation);
        assertTrue(ConversationParser.parse("delete", new RouteManager(new RouteList()))
                instanceof DeleteConversation);
        assertTrue(ConversationParser.parse("find", new RouteManager(new RouteList()))
                instanceof FindConversation);
        assertTrue(ConversationParser.parse("findtime", new RouteManager(new RouteList()))
                instanceof FreeTimeConversation);
        assertTrue(ConversationParser.parse("busStop", new RouteManager(new RouteList()))
                instanceof GetBusStopConversation);
        assertTrue(ConversationParser.parse("findPath", new RouteManager(new RouteList()))
                instanceof FindPathConversation);
        assertTrue(ConversationParser.parse("search", new RouteManager(new RouteList()))
                instanceof SearchConversation);
        assertThrows(DukeUnknownCommandException.class,
            () -> ConversationParser.parse("sdasds", new RouteManager(new RouteList())));
        assertThrows(DukeUnknownCommandException.class,
            () -> ConversationParser.parse("deleteeee", new RouteManager(new RouteList())));
        assertThrows(DukeUnknownCommandException.class,
            () -> ConversationParser.parse("DElete", new RouteManager(new RouteList())));
    }
}
