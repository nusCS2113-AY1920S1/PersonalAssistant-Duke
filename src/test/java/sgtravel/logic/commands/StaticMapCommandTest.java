package sgtravel.logic.commands;

import sgtravel.ModelStub;
import sgtravel.commons.exceptions.FileLoadFailException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class StaticMapCommandTest {
    @Test
    void command() throws FileLoadFailException {
        ModelStub model = new ModelStub();
        String query = "Sentosa";

        StaticMapCommand command = new StaticMapCommand(query);
        assertThrows(NoClassDefFoundError.class, () -> {
            command.execute(model);
        });
    }
}
