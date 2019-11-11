package logic.command;

import common.DukeException;
import gui.UiController;
import logic.LogicController;
import logic.parser.AddTaskParser;
import logic.parser.DoneCommandParser;
import logic.parser.LinkCommandParser;
import logic.parser.list.ListTasksAllParser;
import logic.parser.list.ListTasksTodoParser;
import model.Model;
import model.ModelController;
import org.junit.jupiter.api.Test;
import storage.Storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author yuyanglin28

public class ListTaskCommandTest {

    private Model model;
    private Storage storage;
    private LogicController logicController;
    private UiController uiController;


    /**
     * This method is to generate test data
     * @throws DukeException throw exception during building data, no exception here
     */
    public void buildTestData() throws DukeException {
        model = new ModelController();
        logicController = new LogicController(model);
        uiController = new UiController(logicController, storage);
        uiController.start();
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
    public void listTask_all_empty() throws DukeException {
        model = new ModelController();
        logicController = new LogicController(model);
        uiController = new UiController(logicController, storage);
        uiController.start();
        model.getMemberList().clear();
        model.getTaskList().clear();
        Command command = ListTasksAllParser.parseListTasksAll("");
        CommandOutput out = command.execute(model);
        assertEquals("There are currently no tasks in project manager.", out.getOutputToUser());
    }

    @Test
    public void listTask_todo_empty() throws DukeException {
        model = new ModelController();
        logicController = new LogicController(model);
        uiController = new UiController(logicController, storage);
        uiController.start();
        model.getMemberList().clear();
        model.getTaskList().clear();
        Command command = ListTasksTodoParser.parseListTasksTodo("");
        CommandOutput out = command.execute(model);
        assertEquals("There are currently no todo tasks in project manager.", out.getOutputToUser());
    }

    @Test
    public void listTask_allPicNum_empty() throws DukeException {
        model = new ModelController();
        logicController = new LogicController(model);
        uiController = new UiController(logicController, storage);
        uiController.start();
        model.getMemberList().clear();
        model.getTaskList().clear();
        Command command = ListTasksAllParser.parseListTasksAll("picnum");
        CommandOutput out = command.execute(model);
        assertEquals("There are currently no tasks in project manager.", out.getOutputToUser());
    }

    @Test
    public void listTask_todoPicNum_empty() throws DukeException {
        buildTestData();
        DoneCommandParser.parseDoneCommand("1 2 3 4 5 6").execute(model);
        Command command = ListTasksTodoParser.parseListTasksTodo("picnum");
        CommandOutput out = command.execute(model);
        assertEquals("There are currently no todo tasks in project manager.", out.getOutputToUser());
    }

    @Test
    public void listTask_all_success() throws DukeException {
        buildTestData();
        Command command = ListTasksAllParser.parseListTasksAll("");
        CommandOutput out = command.execute(model);
        assertEquals("Here are all tasks in project manager:\n" 
                + "1. [\u2715] task1 (due: Sun 01-12-2019 11:11H)\n" 
                + "2. [\u2715] task2 (due: Wed 04-12-2019 11:12H)\n" 
                + "3. [\u2715] task3 (due: Tue 03-12-2019 11:13H)\n" 
                + "4. [\u2715] task4 (due: Wed 04-12-2019 10:11H)\n" 
                + "5. [\u2715] task5 (due: Tue 03-12-2019 11:22H)\n" 
                + "6. [\u2715] task6 (due: Wed 04-12-2019 13:11H)\n" 
                + "7. [\u2713] task7 (due: Tue 03-12-2019 09:11H)\n" 
                + "8. [\u2713] task8 (due: Thu 05-12-2019 09:11H)", 
                out.getOutputToUser());
        model.getMemberList().clear();
        model.getTaskList().clear();
        model.save();
    }

    @Test
    public void listTask_todo_success() throws DukeException {
        buildTestData();
        DoneCommandParser.parseDoneCommand("2").execute(model);
        Command command = ListTasksTodoParser.parseListTasksTodo("");
        CommandOutput out = command.execute(model);
        assertEquals("Here are todo tasks in project manager:\n" 
                + "1. [\u2715] task1 (due: Sun 01-12-2019 11:11H)\n" 
                + "3. [\u2715] task3 (due: Tue 03-12-2019 11:13H)\n" 
                + "4. [\u2715] task4 (due: Wed 04-12-2019 10:11H)\n" 
                + "5. [\u2715] task5 (due: Tue 03-12-2019 11:22H)\n" 
                + "6. [\u2715] task6 (due: Wed 04-12-2019 13:11H)", 
                out.getOutputToUser());
        model.getMemberList().clear();
        model.getTaskList().clear();
        model.save();
    }

    @Test
    public void listTask_allPicNum_success() throws DukeException {
        buildTestData();
        AddTaskParser.parseAddTask("task9").execute(model);
        Command command = ListTasksAllParser.parseListTasksAll("picnum");
        CommandOutput out = command.execute(model);
        assertEquals("Here are all the tasks in order of num of PICs:\n" 
                + "9. [\u2715] task9 has 0 PICs.\n" 
                + "4. [\u2715] task4 (due: Wed 04-12-2019 10:11H) has 1 PICs.\n" 
                + "6. [\u2715] task6 (due: Wed 04-12-2019 13:11H) has 1 PICs.\n" 
                + "7. [\u2713] task7 (due: Tue 03-12-2019 09:11H) has 1 PICs.\n" 
                + "8. [\u2713] task8 (due: Thu 05-12-2019 09:11H) has 1 PICs.\n" 
                + "1. [\u2715] task1 (due: Sun 01-12-2019 11:11H) has 2 PICs.\n" 
                + "2. [\u2715] task2 (due: Wed 04-12-2019 11:12H) has 3 PICs.\n" 
                + "3. [\u2715] task3 (due: Tue 03-12-2019 11:13H) has 3 PICs.\n" 
                + "5. [\u2715] task5 (due: Tue 03-12-2019 11:22H) has 3 PICs.", 
                out.getOutputToUser());
        model.getMemberList().clear();
        model.getTaskList().clear();
        model.save();
    }

    @Test
    public void listTask_todoPicNum_success() throws DukeException {
        buildTestData();
        Command command = ListTasksTodoParser.parseListTasksTodo("picnum");
        CommandOutput out = command.execute(model);
        assertEquals("Here are todo tasks in order of num of PICs:\n" 
                + "4. [\u2715] task4 (due: Wed 04-12-2019 10:11H) has 1 PICs.\n" 
                + "6. [\u2715] task6 (due: Wed 04-12-2019 13:11H) has 1 PICs.\n" 
                + "1. [\u2715] task1 (due: Sun 01-12-2019 11:11H) has 2 PICs.\n" 
                + "2. [\u2715] task2 (due: Wed 04-12-2019 11:12H) has 3 PICs.\n" 
                + "3. [\u2715] task3 (due: Tue 03-12-2019 11:13H) has 3 PICs.\n" 
                + "5. [\u2715] task5 (due: Tue 03-12-2019 11:22H) has 3 PICs.", 
                out.getOutputToUser());
        model.getMemberList().clear();
        model.getTaskList().clear();
        model.save();
    }

}
