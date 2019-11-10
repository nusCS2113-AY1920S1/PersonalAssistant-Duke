package command;

import common.DukeException;
import logic.command.Command;
import logic.command.CommandOutput;
import logic.parser.edit.EditTaskNameParser;
import model.Model;
import model.ModelController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

//@@author JasonChanWQ

public class EditTaskNameCommandTest {

    @Test
    public void editTaskNameCommand_indexNotInTaskList_throwsException() throws DukeException {
        Model model = new ModelController();
        model.getTaskList().clear();
        model.addTask("Create event poster");
        Command command = EditTaskNameParser.parseEditTaskName("5 /to Create swimming event poster");
        assertThrows(DukeException.class, () -> command.execute(model));
    }

    @Test
    public void editTaskNameCommand_negativeIndex_throwsException() throws DukeException {
        Model model = new ModelController();
        model.getTaskList().clear();
        model.addTask("Create event poster");
        Command command = EditTaskNameParser.parseEditTaskName("-1 /to Create swimming event poster");
        assertThrows(DukeException.class, () -> command.execute(model));
    }

    @Test
    public void editTaskNameCommand_alreadyInTaskList_throwsException() throws DukeException {
        Model model = new ModelController();
        model.getTaskList().clear();
        model.addTask("Create event poster");
        model.addTask("Create swimming event poster");
        Command command = EditTaskNameParser.parseEditTaskName("1 /to Create swimming event poster");
        assertThrows(DukeException.class, () -> command.execute(model));
    }

    @Test
    public void editTaskNameCommand_success() throws DukeException {
        Model model = new ModelController();
        model.getTaskList().clear();
        model.addTask("Create event poster");
        Command command = EditTaskNameParser.parseEditTaskName("1 /to Create swimming event poster");
        CommandOutput out = command.execute(model);
        assertEquals("Create event poster has been renamed to: Create swimming event poster",
                out.getOutputToUser());
    }
}
