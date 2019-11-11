package sgtravel.logic.commands;

import sgtravel.commons.exceptions.SingaporeTravelException;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class HelpCommandTest {

    @Test
    void execute() throws SingaporeTravelException {
        Command c = new HelpCommand();
        assertTrue(c instanceof HelpCommand);
    }
}
