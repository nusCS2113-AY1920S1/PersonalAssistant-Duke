package logic.command;

import common.DukeException;
import logic.parser.edit.EditTaskDesParser;
import model.Model;
import model.ModelController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

//@@author yuyanglin28

public class EditTaskDesCommandTest {

    @Test
    public void editTaskDesCommand_inputIndexNotInTaskList_throwsException() throws DukeException {
        Model model = new ModelController();
        model.getTaskList().clear();
        model.addTask("test1");
        Command command = EditTaskDesParser.parseEditTaskDes("2 /to test des");
        assertThrows(DukeException.class, () -> command.execute(model));
    }

    @Test
    public void editTaskDesCommand_set() throws DukeException {
        Model model = new ModelController();
        model.getTaskList().clear();
        model.addTask("test");
        Command command = EditTaskDesParser.parseEditTaskDes("1 /to test des");
        CommandOutput out = command.execute(model);
        assertEquals("You have set the description of task: [test] to [[test des]]", out.getOutputToUser());
    }

    @Test
    public void editTaskDesCommand_noUpdate() throws DukeException {
        Model model = new ModelController();
        model.getTaskList().clear();
        model.addTask("test");
        Command command1 = EditTaskDesParser.parseEditTaskDes("1 /to test des");
        command1.execute(model);
        Command command2 = EditTaskDesParser.parseEditTaskDes("1 /to test des");
        CommandOutput out = command2.execute(model);
        assertEquals("No update, they are the same.", out.getOutputToUser());
    }

    @Test
    public void editTaskDesCommand_update() throws DukeException {
        Model model = new ModelController();
        model.getTaskList().clear();
        model.addTask("test");
        Command command1 = EditTaskDesParser.parseEditTaskDes("1 /to test des1");
        command1.execute(model);
        Command command2 = EditTaskDesParser.parseEditTaskDes("1 /to test des2");
        CommandOutput out = command2.execute(model);
        assertEquals("You have update the description of task: "
                + "[test] from [[test des1]] to [[test des2]]", out.getOutputToUser());
    }
}
