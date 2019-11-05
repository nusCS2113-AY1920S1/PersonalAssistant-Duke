package duke.logic.commands;

import duke.ModelStub;
import duke.commons.exceptions.DukeException;
import duke.commons.exceptions.OutOfBoundsException;
import duke.model.Event;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DeleteCommandTest {

    @Test
    void execute() throws DukeException {
        ModelStub model = new ModelStub();
        Event event = new Event("Sentosa", LocalDateTime.now(), LocalDateTime.now());
        model.getEvents().add(event);
        DeleteCommand deleteCommand = new DeleteCommand(0);
        deleteCommand.execute(model);
        assertFalse(model.getEvents().contains(event));
        assertThrows(OutOfBoundsException.class, () -> deleteCommand.execute(model));
        DeleteCommand d2 = new DeleteCommand(99999);
        assertThrows(OutOfBoundsException.class, () -> d2.execute(model));
        DeleteCommand d3 = new DeleteCommand(-1);
        assertThrows(OutOfBoundsException.class, () -> d3.execute(model));
        DeleteCommand d4 = new DeleteCommand(-2);
        assertThrows(OutOfBoundsException.class, () -> d4.execute(model));
        DeleteCommand d5 = new DeleteCommand(1);
        assertThrows(OutOfBoundsException.class, () -> d5.execute(model));
    }
}
