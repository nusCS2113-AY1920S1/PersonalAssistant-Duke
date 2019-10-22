package duke.logic.commands;

import duke.ModelStub;
import duke.commons.exceptions.DukeException;
import duke.logic.commands.results.CommandResultExit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ExitCommandTest {

    @Test
    void execute() throws DukeException {
        ModelStub model = new ModelStub();
        Command c = new ExitCommand();
        assertTrue(c.execute(model) instanceof CommandResultExit);
    }
}
