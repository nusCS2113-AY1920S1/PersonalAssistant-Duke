package compal.logic.parser;

import org.junit.jupiter.api.Test;

import static compal.logic.parser.CommandParserTestUtil.assertParseFailure;

//@@author SholihinK
class ImportCommandParserTest {

    private ImportCommandParser parser = new ImportCommandParser();

    @Test
    void parse_invalidParam_fail() {
        assertParseFailure(parser, "", CommandParser.MESSAGE_MISSING_FILE_NAME_ARG);
        assertParseFailure(parser, "/file-name", CommandParser.MESSAGE_MISSING_FILE_NAME);
    }
}