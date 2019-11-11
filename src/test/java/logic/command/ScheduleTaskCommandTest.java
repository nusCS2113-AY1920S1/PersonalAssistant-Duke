package logic.command;

import common.DukeException;
import logic.parser.AddTaskParser;
import logic.parser.DoneCommandParser;
import logic.parser.schedule.ScheduleTeamParser;
import model.Model;
import model.ModelController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScheduleTaskCommandTest {
    private Model model = new ModelController();

    /**
     * This method is to generate test data
     * @throws DukeException throw exception during building data, no exception here
     */
    public void buildTestData() throws DukeException {
        model = new ModelController();
        model.getTaskList().clear();
        AddTaskParser.parseAddTask("task1 /at 01/12/2019 1111").execute(model);
        AddTaskParser.parseAddTask("task2 /at 04/12/2019 1112").execute(model);
        AddTaskParser.parseAddTask("task3 /at 03/12/2019 1113").execute(model);
        AddTaskParser.parseAddTask("task4 /at 04/12/2019 1011").execute(model);
        AddTaskParser.parseAddTask("task5 /at 03/12/2019 1122").execute(model);
        AddTaskParser.parseAddTask("task6 /at 04/12/2019 1311").execute(model);
        AddTaskParser.parseAddTask("task7 /at 03/12/2019 0911").execute(model);
        AddTaskParser.parseAddTask("task8 /at 05/12/2019 0911").execute(model);
        DoneCommandParser.parseDoneCommand("7 8").execute(model);
    }

    @Test
    public void scheduleTaskAll_empty() throws DukeException {
        Command command = ScheduleTeamParser.parseScheduleTeam("all");
        CommandOutput out = command.execute(model);
        assertEquals("No task for the whole team.", out.getOutputToUser());
    }

    @Test
    public void scheduleTaskTodo_empty1() throws DukeException {
        Command command = ScheduleTeamParser.parseScheduleTeam("todo");
        CommandOutput out = command.execute(model);
        assertEquals("No todo task for the whole team.", out.getOutputToUser());
    }

    @Test
    public void scheduleTaskTodo_empty2() throws DukeException {
        DoneCommandParser.parseDoneCommand("1 2 3 4 5 6").execute(model);
        Command command = ScheduleTeamParser.parseScheduleTeam("todo");
        CommandOutput out = command.execute(model);
        assertEquals("No todo task for the whole team.", out.getOutputToUser());
    }

    @Test
    public void scheduleTaskAll_success() throws DukeException {
        buildTestData();
        Command command = ScheduleTeamParser.parseScheduleTeam("all");
        CommandOutput out = command.execute(model);
        assertEquals("Schedule all tasks of the whole team: \n"
                + "1. [\u2715] task1 (due: Sun 01-12-2019 11:11H)\n"
                + "7. [\u2713] task7 (due: Tue 03-12-2019 09:11H)\n"
                + "3. [\u2715] task3 (due: Tue 03-12-2019 11:13H)\n"
                + "5. [\u2715] task5 (due: Tue 03-12-2019 11:22H)\n"
                + "4. [\u2715] task4 (due: Wed 04-12-2019 10:11H)\n"
                + "2. [\u2715] task2 (due: Wed 04-12-2019 11:12H)\n"
                + "6. [\u2715] task6 (due: Wed 04-12-2019 13:11H)\n"
                + "8. [\u2713] task8 (due: Thu 05-12-2019 09:11H)",
                out.getOutputToUser());
    }

    @Test
    public void scheduleTaskTodo_success() throws DukeException {
        buildTestData();
        Command command = ScheduleTeamParser.parseScheduleTeam("todo");
        CommandOutput out = command.execute(model);
        assertEquals("Schedule todo tasks of the whole team: \n"
                + "1. [\u2715] task1 (due: Sun 01-12-2019 11:11H)\n"
                + "3. [\u2715] task3 (due: Tue 03-12-2019 11:13H)\n"
                + "5. [\u2715] task5 (due: Tue 03-12-2019 11:22H)\n"
                + "4. [\u2715] task4 (due: Wed 04-12-2019 10:11H)\n"
                + "2. [\u2715] task2 (due: Wed 04-12-2019 11:12H)\n"
                + "6. [\u2715] task6 (due: Wed 04-12-2019 13:11H)",
                out.getOutputToUser());
    }
}
