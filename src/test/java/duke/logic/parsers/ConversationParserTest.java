package duke.logic.parsers;

import duke.commons.exceptions.DukeUnknownCommandException;
import duke.commons.exceptions.ParseException;
import duke.logic.RouteManager;
import duke.logic.conversations.DeleteConversation;
import duke.logic.conversations.FindConversation;
import duke.logic.conversations.FindPathConversation;
import duke.logic.conversations.GetBusStopConversation;
import duke.logic.conversations.MarkDoneConversation;
import duke.logic.conversations.SearchConversation;

import duke.model.lists.RouteList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConversationParserTest {

    @Test
    void parse() throws DukeUnknownCommandException, ParseException {
        assertTrue(ConversationParser.parse("done")
                instanceof MarkDoneConversation);
        assertTrue(ConversationParser.parse("delete")
                instanceof DeleteConversation);
        assertTrue(ConversationParser.parse("find")
                instanceof FindConversation);
        assertTrue(ConversationParser.parse("busStop")
                instanceof GetBusStopConversation);
        assertTrue(ConversationParser.parse("findPath")
                instanceof FindPathConversation);
        assertTrue(ConversationParser.parse("search")
                instanceof SearchConversation);
        assertThrows(ParseException.class,
            () -> ConversationParser.parse("sdasds"));
        assertThrows(ParseException.class,
            () -> ConversationParser.parse("deleteeee"));
        assertThrows(ParseException.class,
            () -> ConversationParser.parse("DElete"));
    }
}
