package sgtravel.logic.commands;

import sgtravel.ModelStub;
import sgtravel.commons.exceptions.SingaporeTravelException;
import sgtravel.logic.commands.results.CommandResultText;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertTrue;

class FindCommandTest {

    @Test
    void execute() throws SingaporeTravelException, FileNotFoundException {
        Command c = new FindCommand("meow");
        ModelStub modelStub = new ModelStub();
        assertTrue(c.execute(modelStub) instanceof CommandResultText);
        Command b = new FindCommand("moo");
        assertTrue(b.execute(modelStub) instanceof CommandResultText);
    }
}
