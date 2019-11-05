package duke.logic.commands;

import duke.ModelStub;
import duke.commons.exceptions.DuplicateTaskException;
import duke.commons.exceptions.DukeException;
import duke.model.Event;
import duke.model.Model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AddCommandTest {

    @Test
    void execute() throws DukeException {
        Model model = new ModelStub();
        Event event = new Event("NUS", LocalDateTime.now(), LocalDateTime.now());
        AddCommand addCommand = new AddCommand(event);
        addCommand.execute(model);
        assertTrue(model.getEvents().contains(event));
        assertThrows(DuplicateTaskException.class, () -> addCommand.execute(model));
        Event event2 = new Event("NTU", LocalDateTime.now(), LocalDateTime.now());
        AddCommand addCommand2 = new AddCommand(event2);
        addCommand2.execute(model);
        assertTrue(model.getEvents().contains(event));
        assertTrue(model.getEvents().contains(event2));
    }
}
