package logic.command;

import logic.parser.AddTaskParser;
import model.Model;
import model.ModelController;
import model.Task;
import model.TasksManager;
import org.junit.jupiter.api.Test;
import common.DukeException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AddTaskCommandTest {
    public static final String TIME_PATTERN = "dd/MM/yyyy hhmm";

    //@@author JustinChia1997
    @Test
    public void normalAddingTask_normalUseCase_Success() throws DukeException {
        Model model = new ModelController();
        AddTaskCommand command = new AddTaskCommand("testTask");
        CommandOutput commandOutput = command.execute(model);
        TasksManager taskManager = model.getTasksManager();
        Task testingTask = taskManager.getTaskByName("testTask");
        assertEquals("testTask", testingTask.getName());
    }

    @Test
    public void normalAddingTask_normalUseCaseTime_Success() throws DukeException {
        Model model = new ModelController();
        AddTaskCommand command = AddTaskParser.parseAddTask("testing /at 14/12/2019 1130");
        command.execute(model);
        TasksManager taskManager = model.getTasksManager();
        Task testingTask = taskManager.getTaskByName("testing");
        Date testingDate = testingTask.getTime();
        String correctDate;
        DateFormat ft = new SimpleDateFormat(TIME_PATTERN);
        correctDate = ft.format(testingDate);
        assertEquals(correctDate, "14/12/2019 1130");
    }

    @Test
    public void setReqSkill_emptyString_failure() throws DukeException {
        AddTaskCommand command = new AddTaskCommand("testTask");
        assertThrows(DukeException.class, () -> command.setReqSkill(""));
    }

    @Test
    public void setReqSkill_normalUseCase_Success() throws DukeException {
        Model model = new ModelController();
        AddTaskCommand command = AddTaskParser.parseAddTask("2113 /skill coding communication");
        CommandOutput commandOutput = command.execute(model);
        TasksManager taskManager = model.getTasksManager();
        Task testingTask = taskManager.getTaskByName("2113");
        assertEquals(testingTask.getReqSkills().get(0), "coding");
        assertEquals(testingTask.getReqSkills().get(1), "communication");
    }
}
