package duke.commands;

import duke.StorageStub;
import duke.UiStub;
import duke.commons.exceptions.DukeException;
import duke.storage.Storage;
import org.junit.jupiter.api.Test;

class ReminderCommandTest {

    @Test
    void execute() throws DukeException {
        Storage storage = new StorageStub();
        ReminderCommand reminderCommand = new ReminderCommand();
        reminderCommand.execute(storage);
    }
}
