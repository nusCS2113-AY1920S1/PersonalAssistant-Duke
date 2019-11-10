package duchess.parser.commands;

import duchess.exceptions.DuchessException;
import duchess.logic.commands.DoneGradeCommand;
import duchess.logic.commands.DoneTaskCommand;
import duchess.parser.Util;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DoneCommandParserTest {
    @Test
    void parse() throws DuchessException {
        Map<String, String> parameters1 = Util.parameterize("done task /no 1");
        assertTrue(
                DoneCommandParser.parse(parameters1) instanceof DoneTaskCommand
        );

        Map<String, String> parameters2 = Util.parameterize("done grade /module CS1231 /no 1 /marks 15/20");
        assertTrue(
                DoneCommandParser.parse(parameters2) instanceof DoneGradeCommand
        );

        Map<String, String> parameters3 = Util.parameterize("done task something");
        assertThrows(DuchessException.class, () -> DoneCommandParser.parse(parameters3));

        Map<String, String> parameters4 = Util.parameterize("done task /no something");
        assertThrows(DuchessException.class, () -> DoneCommandParser.parse(parameters4));

        Map<String, String> parameters5 = Util.parameterize("done grade something");
        assertThrows(DuchessException.class, () -> DoneCommandParser.parse(parameters5));

        Map<String, String> parameters6 = Util.parameterize("done grade /module something");
        assertThrows(DuchessException.class, () -> DoneCommandParser.parse(parameters6));

        Map<String, String> parameters7 = Util.parameterize("done grade /module something /no something");
        assertThrows(DuchessException.class, () -> DoneCommandParser.parse(parameters7));

        Map<String, String> parameters8 = Util.parameterize("done grade /module s /no 1 /marks s");
        assertThrows(DuchessException.class, () -> DoneCommandParser.parse(parameters8));

        Map<String, String> parameters9 = Util.parameterize("done grade /module s /no s /marks 14/31");
        assertThrows(DuchessException.class, () -> DoneCommandParser.parse(parameters9));

    }
}
