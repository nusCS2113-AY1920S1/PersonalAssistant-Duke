package duke.logic.commands;

import duke.ModelStub;
import duke.commons.exceptions.DukeException;
import duke.logic.commands.results.CommandResultText;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HelpCommandTest {

    @Test
    void execute() throws DukeException {
        Command c = new HelpCommand();
        ModelStub modelStub = new ModelStub();
        assertThrows(HeadlessException.class, () -> c.execute(modelStub));
    }
}
