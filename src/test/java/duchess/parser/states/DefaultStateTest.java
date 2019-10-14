package duchess.parser.states;

import duchess.exceptions.DuchessException;
import duchess.logic.commands.ListModulesCommand;
import duchess.logic.commands.ListTasksCommand;
import duchess.parser.Parser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class DefaultStateTest {
    private final Parser parser;
    private final DefaultState defaultState;

    public DefaultStateTest() {
        this.parser = new Parser();
        this.defaultState = new DefaultState(this.parser);
    }

    @Test
    public void list_command() throws DuchessException {
        assertTrue(defaultState.parse("list modules") instanceof ListModulesCommand);
        assertTrue(defaultState.parse("list tasks") instanceof ListTasksCommand);
    }
}