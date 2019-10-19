package duke.logic.commands;

import duke.ModelStub;
import duke.commons.exceptions.DukeException;
import org.junit.jupiter.api.Test;

class ReminderCommandTest {

    @Test
    void execute() throws DukeException {
        ModelStub model = new ModelStub();
        ReminderCommand reminderCommand = new ReminderCommand();
        reminderCommand.execute(model);
    }
}
