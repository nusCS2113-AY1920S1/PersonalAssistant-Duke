package sgtravel.logic.commands;

import sgtravel.ModelStub;
import sgtravel.commons.exceptions.SingaporeTravelException;
import sgtravel.logic.commands.results.CommandResultExit;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ExitCommandTest {

    @Test
    void execute() throws SingaporeTravelException, FileNotFoundException {
        ModelStub model = new ModelStub();
        Command c = new ExitCommand();
        assertTrue(c.execute(model) instanceof CommandResultExit);
    }
}
