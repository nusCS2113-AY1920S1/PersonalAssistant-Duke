package duchess.parser.commands;

import duchess.exceptions.DuchessException;
import duchess.logic.commands.DeleteGradeCommand;
import duchess.logic.commands.DeleteModuleCommand;
import duchess.logic.commands.DeleteTaskCommand;
import duchess.parser.Util;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DeleteCommandParserTest {
    @Test
    void parse() throws DuchessException {
        Map<String, String> parameters1 = Util.parameterize("delete task /no 1");
        assertTrue(
                DeleteCommandParser.parse(parameters1) instanceof DeleteTaskCommand
        );

        Map<String, String> parameters2 = Util.parameterize("delete module /no 1");
        assertTrue(
                DeleteCommandParser.parse(parameters2) instanceof DeleteModuleCommand
        );

        Map<String, String> parameters3 = Util.parameterize("delete grade /module CS1231 /no 1");
        assertTrue(
                DeleteCommandParser.parse(parameters3) instanceof DeleteGradeCommand
        );

        Map<String, String> parameters4 = Util.parameterize("delete task something");
        assertThrows(DuchessException.class, () -> DeleteCommandParser.parse(parameters4));

        Map<String, String> parameters5 = Util.parameterize("delete task /no something");
        assertThrows(DuchessException.class, () -> DeleteCommandParser.parse(parameters5));

        Map<String, String> parameters6 = Util.parameterize("delete module something");
        assertThrows(DuchessException.class, () -> DeleteCommandParser.parse(parameters6));

        Map<String, String> parameters7 = Util.parameterize("delete module /no something");
        assertThrows(DuchessException.class, () -> DeleteCommandParser.parse(parameters7));

        Map<String, String> parameters8 = Util.parameterize("delete grade something");
        assertThrows(DuchessException.class, () -> DeleteCommandParser.parse(parameters8));

        Map<String, String> parameters9 = Util.parameterize("delete grade /module something");
        assertThrows(DuchessException.class, () -> DeleteCommandParser.parse(parameters9));

        Map<String, String> parameters10 = Util.parameterize("delete grade /module something /no something");
        assertThrows(DuchessException.class, () -> DeleteCommandParser.parse(parameters10));

    }
}
