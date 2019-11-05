package duke.logic.commands;

import duke.ModelStub;
import duke.commons.exceptions.DukeException;

import org.junit.jupiter.api.Test;

import java.awt.HeadlessException;

import static org.junit.jupiter.api.Assertions.assertThrows;

class HelpCommandTest {

    @Test
    void execute() throws DukeException {
        Command c = new HelpCommand();
        ModelStub modelStub = new ModelStub();
        assertThrows(HeadlessException.class, () -> c.execute(modelStub));
    }
}
