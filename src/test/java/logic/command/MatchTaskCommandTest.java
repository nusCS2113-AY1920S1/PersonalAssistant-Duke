package logic.command;

import common.DukeException;
import logic.command.match.MatchMemberCommand;
import logic.command.match.MatchTaskCommand;
import model.Model;
import model.ModelController;
import model.Task;
import model.TasksManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MatchTaskCommandTest {
    //@@author JustinChia1997
    @Test
    public void matchTaskCommand_NoSuchTask_Failure() throws DukeException {
        Model model = new ModelController();
        Command matchTaskCommand = new MatchTaskCommand(-1);
        assertThrows(DukeException.class, () -> matchTaskCommand.execute(model));
    }

    @Test
    public void matchTaskCommand_OneMatch_Success() throws DukeException {
        //Create the basic objects first
        Model model = new ModelController();
        model.addMember("Johnny");
        model.addMemberSkill("Johnny", "coding");
        model.addTask("2113");
        model.addTaskReqSkill("2113", "coding");
        TasksManager tasklist = model.getTasksManager();
        Task testTask = tasklist.getTaskByName("2113");
        int id = tasklist.getIndexInListByTask(testTask);
        Command command = new MatchTaskCommand(id);
        CommandOutput output = command.execute(model);
        String textOutput = output.getOutputToUser();
        assertTrue(textOutput.contains("Johnny"));
    }

    @Test
    public void matchMemberCommand_OneMatch_Success() throws DukeException {
        //Create the basic objects first
        Model model = new ModelController();
        model.addMember("Marlin");
        model.addMemberSkill("Marlin", "coding");
        model.addTask("2113T");
        model.addTaskReqSkill("2113T", "coding");
        Command command = new MatchMemberCommand("Marlin");
        CommandOutput output = command.execute(model);
        String textOutput = output.getOutputToUser();
        assertTrue(textOutput.contains("2113T"));
    }

    @Test
    public void matchTaskCommand_TwoMatch_Success() throws DukeException {
        //Create the basic objects first
        Model model = new ModelController();
        model.addMember("Adam");
        model.addMemberSkill("Adam", "coding");
        model.addMember("Joseph");
        model.addMemberSkill("Joseph", "coding");
        model.addTask("project");
        model.addTaskReqSkill("project", "coding");
        TasksManager tasklist = model.getTasksManager();
        Task testTask = tasklist.getTaskByName("project");
        int id = tasklist.getIndexInListByTask(testTask);
        Command command = new MatchTaskCommand(id);
        CommandOutput output = command.execute(model);
        String textOutput = output.getOutputToUser();
        assertTrue(textOutput.contains("Adam"));
        assertTrue(textOutput.contains("Joseph"));
    }

}
