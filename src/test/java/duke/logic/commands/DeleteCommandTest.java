package duke.logic.commands;

import duke.ModelStub;
import duke.commons.exceptions.DukeException;
import duke.model.events.Task;
import duke.model.planning.Todo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

class DeleteCommandTest {

    @Test
    void execute() throws DukeException {
        ModelStub model = new ModelStub();
        Task task = new Todo("homework");
        model.getTasks().add(task);
        DeleteCommand deleteCommand = new DeleteCommand(0);
        deleteCommand.execute(model);
        assertFalse(model.getTasks().contains(task));
    }
}
