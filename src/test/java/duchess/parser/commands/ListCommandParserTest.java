package duchess.parser.commands;

import duchess.exceptions.DuchessException;
import duchess.logic.commands.ListModulesCommand;
import duchess.logic.commands.ListTasksCommand;
import duchess.parser.Util;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ListCommandParserTest {
    @Test
    void parse() throws DuchessException {
        Map<String, String> parameters1 = Util.parameterize("list modules");
        assertTrue(
                ListCommandParser.parse(parameters1) instanceof ListModulesCommand
        );

        Map<String, String> parameters2 = Util.parameterize("list tasks");
        assertTrue(
                ListCommandParser.parse(parameters2) instanceof ListTasksCommand
        );

        Map<String, String> parameters3 = Util.parameterize("list something");
        assertThrows(DuchessException.class, () -> ListCommandParser.parse(parameters3));
    }
}