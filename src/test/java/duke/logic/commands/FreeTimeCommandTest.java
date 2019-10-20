package duke.logic.commands;

import duke.ModelStub;
import duke.commons.exceptions.DukeException;
import org.junit.jupiter.api.Test;

class FreeTimeCommandTest {

    @Test
    void execute() throws DukeException {
        ModelStub model = new ModelStub();
        FreeTimeCommand freeTimeCommand = new FreeTimeCommand(1);
        freeTimeCommand.execute(model);
    }
}
