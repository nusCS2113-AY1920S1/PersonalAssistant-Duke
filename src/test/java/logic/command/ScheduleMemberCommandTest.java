package logic.command;

import common.DukeException;
import logic.command.schedule.ScheduleMemberAllCommand;
import logic.parser.AddTaskParser;
import logic.parser.DoneCommandParser;
import logic.parser.LinkCommandParser;
import logic.parser.schedule.ScheduleMemberAllParser;
import logic.parser.schedule.ScheduleMemberTodoParser;
import model.Model;
import model.ModelController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ScheduleMemberCommandTest {
    private Model model = new ModelController();

    /**
     * This method is to generate test data
     * @throws DukeException throw exception during building data, no exception here
     */
    public void buildTestData() throws DukeException {
        model = new ModelController();
        model.getMemberList().clear();
        model.getTaskList().clear();
        model.addMember("test1");
        model.addMember("test2");
        model.addMember("test3");
        model.addMember("test4");
        model.addMember("test5");
        AddTaskParser.parseAddTask("task1 /at 01/12/2019 1111").execute(model);
        AddTaskParser.parseAddTask("task2 /at 04/12/2019 1112").execute(model);
        AddTaskParser.parseAddTask("task3 /at 03/12/2019 1113").execute(model);
        AddTaskParser.parseAddTask("task4 /at 04/12/2019 1011").execute(model);
        AddTaskParser.parseAddTask("task5 /at 03/12/2019 1122").execute(model);
        AddTaskParser.parseAddTask("task6 /at 04/12/2019 1311").execute(model);
        AddTaskParser.parseAddTask("task7 /at 03/12/2019 0911").execute(model);
        AddTaskParser.parseAddTask("task8 /at 05/12/2019 0911").execute(model);
        DoneCommandParser.parseDoneCommand("7 8").execute(model);
        LinkCommandParser.parseLinkCommand("1 3 2 4 6 5 8 /to test1").execute(model);
        LinkCommandParser.parseLinkCommand("1 2 3 5 /to test2").execute(model);
        LinkCommandParser.parseLinkCommand("3 5 7 /to test3").execute(model);
        LinkCommandParser.parseLinkCommand("2 /to test4").execute(model);
    }

    @Test
    public void scheduleMemberAllCommand_memberNameNotInList_throwException() throws DukeException {
        buildTestData();
        Command command = ScheduleMemberAllParser.parseScheduleMemberAll("test8");
        assertThrows(DukeException.class, () -> command.execute(model));
    }

    @Test
    public void scheduleMemberTodoCommand_memberNameNotInList_throwException() throws DukeException {
        buildTestData();
        Command command = ScheduleMemberTodoParser.parseScheduleMemberTodo("test8");
        assertThrows(DukeException.class, () -> command.execute(model));
    }

    @Test
    public void scheduleMemberAllCommand_empty() throws DukeException {
        buildTestData();
        Command command = ScheduleMemberAllParser.parseScheduleMemberAll("test5");
        CommandOutput out = command.execute(model);
        model.getMemberList().clear();
        model.getTaskList().clear();
        assertEquals("No task for member: test5", out.getOutputToUser());
    }

    @Test
    public void scheduleMemberTodoCommand_empty() throws DukeException {
        buildTestData();
        DoneCommandParser.parseDoneCommand("3 5").execute(model);
        Command command = ScheduleMemberTodoParser.parseScheduleMemberTodo("test3");
        CommandOutput out = command.execute(model);
        model.getMemberList().clear();
        model.getTaskList().clear();
        assertEquals("", out.getOutputToUser());
    }

    @Test
    public void scheduleMemberAllCommand_success() throws DukeException {
        buildTestData();
        Command command = ScheduleMemberAllParser.parseScheduleMemberAll("test1");
        CommandOutput out = command.execute(model);
        model.getMemberList().clear();
        model.getTaskList().clear();
        assertEquals("Schedule all tasks of member: test1\n"
                + "1. [\u2715] task1 (due: Sun 01-12-2019 11:11H)\n"
                + "3. [\u2715] task3 (due: Tue 03-12-2019 11:13H)\n"
                + "5. [\u2715] task5 (due: Tue 03-12-2019 11:22H)\n"
                + "4. [\u2715] task4 (due: Wed 04-12-2019 10:11H)\n"
                + "2. [\u2715] task2 (due: Wed 04-12-2019 11:12H)\n"
                + "6. [\u2715] task6 (due: Wed 04-12-2019 13:11H)\n"
                + "8. [\u2713] task8 (due: Thu 05-12-2019 09:11H)",
                out.getOutputToUser());
    }

    @Test
    public void scheduleMemberTodoCommand_success() throws DukeException {
        buildTestData();
        Command command = ScheduleMemberTodoParser.parseScheduleMemberTodo("test3");
        CommandOutput out = command.execute(model);
        model.getMemberList().clear();
        model.getTaskList().clear();
        assertEquals("Schedule todo tasks of member: test3\n"
                + "3. [\u2715] task3 (due: Tue 03-12-2019 11:13H)\n"
                + "5. [\u2715] task5 (due: Tue 03-12-2019 11:22H)",
                out.getOutputToUser());
    }
}
