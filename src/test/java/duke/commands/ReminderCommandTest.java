package duke.commands;

import duke.commons.DukeException;
import duke.storage.Storage;
import duke.ui.Ui;
import org.junit.jupiter.api.Test;

class ReminderCommandTest {

    @Test
    void execute() throws DukeException {
        Ui ui = new Ui();
        Storage storage = new Storage("tasks.txt", ui);
        ReminderCommand reminderCommand = new ReminderCommand();
        reminderCommand.execute(ui, storage);
    }
}
