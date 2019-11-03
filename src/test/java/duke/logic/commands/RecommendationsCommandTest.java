package duke.logic.commands;

import duke.ModelStub;
import duke.commons.exceptions.DukeException;
import duke.logic.commands.results.CommandResultText;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertTrue;

class RecommendationsCommandTest {

    @Test
    void execute() throws DukeException, FileNotFoundException {
        Command c = new HelpCommand();
        ModelStub modelStub = new ModelStub();
        assertTrue(c.execute(modelStub) instanceof CommandResultText);
    }
}
