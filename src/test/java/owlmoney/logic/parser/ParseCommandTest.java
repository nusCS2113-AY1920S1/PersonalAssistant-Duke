package owlmoney.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ParseCommandTest {
    @Test
    void hasNextLine_normalInput_success() {
        ParseCommand testParseCommand = new ParseCommand();
        boolean actualOutput = testParseCommand.hasNextLine();
        String expectedOutput = "false";
        assertEquals(expectedOutput, String.valueOf(actualOutput));
    }
}
