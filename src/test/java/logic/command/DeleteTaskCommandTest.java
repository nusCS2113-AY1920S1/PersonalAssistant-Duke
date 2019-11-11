package logic.command;

import common.DukeException;
import logic.parser.delete.DeleteTaskParser;
import model.Model;
import model.ModelController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author yuyanglin28

public class DeleteTaskCommandTest {

    @Test
    public void deleteTaskCommand_taskNotInList() throws DukeException {
        Model model = new ModelController();
        model.getTaskList().clear();
        model.addTask("test");
        Command command = DeleteTaskParser.parseDeleteTask("2");
        CommandOutput out = command.execute(model);
        model.getTaskList().clear();
        assertEquals("2 index is not within the task list.\n", out.getOutputToUser());
    }

    @Test
    public void deleteTaskCommand_success() throws DukeException {
        Model model = new ModelController();
        model.getTaskList().clear();
        model.addTask("test");
        Command command = DeleteTaskParser.parseDeleteTask("1");
        CommandOutput out = command.execute(model);
        model.getTaskList().clear();
        assertEquals("You have removed a task: 1. test\n", out.getOutputToUser());
    }
}
