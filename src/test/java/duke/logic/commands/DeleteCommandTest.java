package duke.logic.commands;

import duke.ModelStub;
import duke.commons.exceptions.DukeException;
import duke.model.Event;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;

class DeleteCommandTest {

    @Test
    void execute() throws DukeException {
        ModelStub model = new ModelStub();
        Event event = new Event("Sentosa", LocalDateTime.now(), LocalDateTime.now());
        model.getEvents().add(event);
        DeleteCommand deleteCommand = new DeleteCommand(0);
        deleteCommand.execute(model);
        assertFalse(model.getEvents().contains(event));
    }
}
