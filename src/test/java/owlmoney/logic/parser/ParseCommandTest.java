package owlmoney.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.jupiter.api.Test;

import owlmoney.logic.command.Command;
import owlmoney.logic.parser.exception.ParserException;

class ParseCommandTest {
    @Test
    void hasNextLine_normalInput_success() {
        ParseCommand testParseCommand = new ParseCommand();
        boolean actualOutput = testParseCommand.hasNextLine();
        String expectedOutput = "false";
        assertEquals(expectedOutput, String.valueOf(actualOutput));
    }
}
