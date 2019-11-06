package sgtravel.logic.commands;

import sgtravel.ModelStub;
import sgtravel.commons.exceptions.DuplicateTaskException;
import sgtravel.commons.exceptions.DukeException;
import sgtravel.model.Event;
import sgtravel.model.Model;

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
