package duke.logic.commands;

import duke.ModelStub;
import duke.commons.exceptions.DukeException;
import duke.logic.commands.results.CommandResultText;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class FindCommandTest {

    @Test
    void execute() throws DukeException {
        Command c = new FindCommand("meow");
        ModelStub modelStub = new ModelStub();
        assertTrue(c.execute(modelStub) instanceof CommandResultText);
    }
}
