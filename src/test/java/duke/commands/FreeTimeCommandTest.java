package duke.commands;

import duke.StorageStub;
import duke.commons.exceptions.DukeException;
import duke.storage.Storage;
import org.junit.jupiter.api.Test;

class FreeTimeCommandTest {

    @Test
    void execute() throws DukeException {
        Storage storage = new StorageStub();
        FreeTimeCommand freeTimeCommand = new FreeTimeCommand(1);
        freeTimeCommand.execute(storage);
    }
}
