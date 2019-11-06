package sgtravel.logic.parsers;

import sgtravel.logic.commands.PromptCommand;

import sgtravel.logic.parsers.commandparsers.PromptParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PromptParserTest {

    @Test
    void parseCommand() {
        assertTrue(PromptParser.parseCommand("foo") instanceof PromptCommand);
        assertTrue(PromptParser.parseCommand("Nice! I need more.") instanceof PromptCommand);
    }
}
