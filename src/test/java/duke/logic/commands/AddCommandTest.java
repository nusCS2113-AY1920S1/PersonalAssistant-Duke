package duke.logic.commands;

import duke.ModelStub;
import duke.commons.exceptions.DukeException;
import duke.model.Event;
import duke.model.Model;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;

class AddCommandTest {

    @Test
    void execute() throws DukeException {
        Model model = new ModelStub();
        Event event = new Event("NUS", LocalDateTime.now(), LocalDateTime.now());
        AddCommand addCommand = new AddCommand(event);
        addCommand.execute(model);
        assertTrue(model.getEvents().contains(event));
    }
}
