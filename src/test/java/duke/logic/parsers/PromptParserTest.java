package duke.logic.parsers;

import duke.logic.commands.PromptCommand;

import duke.logic.parsers.commandparser.PromptParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PromptParserTest {

    @Test
    void parseCommand() {
        assertTrue(PromptParser.parseCommand("foo") instanceof PromptCommand);
        assertTrue(PromptParser.parseCommand("Nice! I need more.") instanceof PromptCommand);
    }
}
