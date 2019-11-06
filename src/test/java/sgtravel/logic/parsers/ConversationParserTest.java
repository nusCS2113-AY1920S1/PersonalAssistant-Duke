package sgtravel.logic.parsers;

import sgtravel.commons.exceptions.ParseException;
import sgtravel.logic.conversations.DeleteConversation;
import sgtravel.logic.conversations.FindConversation;
import sgtravel.logic.conversations.GetBusStopConversation;
import sgtravel.logic.conversations.MarkDoneConversation;
import sgtravel.logic.conversations.SearchConversation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConversationParserTest {

    @Test
    void parse() throws ParseException {
        assertTrue(ConversationParser.parse("done")
                instanceof MarkDoneConversation);
        assertTrue(ConversationParser.parse("delete")
                instanceof DeleteConversation);
        assertTrue(ConversationParser.parse("find")
                instanceof FindConversation);
        assertTrue(ConversationParser.parse("busStop")
                instanceof GetBusStopConversation);
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
