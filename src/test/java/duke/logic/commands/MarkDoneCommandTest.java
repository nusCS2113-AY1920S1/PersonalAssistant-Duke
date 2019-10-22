package duke.logic.commands;

import duke.ModelStub;
import duke.commons.exceptions.DukeException;
import duke.model.events.Task;
import duke.model.planning.Todo;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;


class MarkDoneCommandTest {

    @Test
    void execute() throws DukeException {
        ModelStub model = new ModelStub();
        Task task = new Todo("homework1");
        AddCommand addCommand = new AddCommand(task);
        addCommand.execute(model);
        MarkDoneCommand markDoneCommand = new MarkDoneCommand(0);
        markDoneCommand.execute(model);
        assertTrue(model.getTasks().get(0).isDone());
        DeleteCommand deleteCommand = new DeleteCommand(0);
        deleteCommand.execute(model);
    }
}
