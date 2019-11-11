package logic.command;

import common.DukeException;
import logic.parser.FindCommandParser;
import logic.parser.edit.EditTaskDesParser;
import model.Model;
import model.ModelController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author yuyanglin28

public class FindCommandTest {
    @Test
    public void findCommand_notFind() throws DukeException {
        Model model = new ModelController();
        model.getTaskList().clear();
        model.addTask("test task");
        Command command = FindCommandParser.parseFindCommand("test2");
        CommandOutput out = command.execute(model);
        model.getTaskList().clear();
        assertEquals("No such task with keyword: test2", out.getOutputToUser());
    }

    @Test
    public void findCommand_byName() throws DukeException {
        Model model = new ModelController();
        model.getTaskList().clear();
        model.addTask("test task");
        Command command = FindCommandParser.parseFindCommand("tes");
        CommandOutput out = command.execute(model);
        model.getTaskList().clear();
        assertEquals("The tasks below are with keyword: tes\n"
                        + "1. [✕]*tes*t task",
                out.getOutputToUser());
    }

    @Test
    public void findCommand_byDes() throws DukeException {
        Model model = new ModelController();
        model.getTaskList().clear();
        model.addTask("test task");
        Command command1 = EditTaskDesParser.parseEditTaskDes("1 /to useless description");
        command1.execute(model);
        Command command2 = FindCommandParser.parseFindCommand("use");
        CommandOutput out = command2.execute(model);
        model.getTaskList().clear();
        assertEquals("The tasks below are with keyword: use\n"
                        + "1. [✕] test task\n"
                        + "Description: *use*less description",
                out.getOutputToUser());

    }

    @Test
    public void findCommand_keyWordAtEnd() throws DukeException {
        Model model = new ModelController();
        model.getTaskList().clear();
        model.addTask("test task");
        Command command = FindCommandParser.parseFindCommand("sk");
        CommandOutput out = command.execute(model);
        model.getTaskList().clear();
        assertEquals("The tasks below are with keyword: sk\n"
                        + "1. [✕]test ta*sk*",
                out.getOutputToUser());

    }
}
