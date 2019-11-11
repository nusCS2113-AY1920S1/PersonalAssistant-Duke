package logic.command;

import common.DukeException;
import gui.TasksCounter;
import gui.UiController;
import gui.Window;
import logic.LogicController;
import logic.parser.AddMemberParser;
import logic.parser.AddTaskParser;
import logic.parser.DoneCommandParser;
import logic.parser.LinkCommandParser;
import logic.parser.list.ListMembersParser;
import model.Model;
import model.ModelController;
import org.junit.jupiter.api.Test;
import storage.Storage;


import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author yuyanglin28

public class ListMemberCommandTest {

    private Model model;
    private LogicController logicController;

    /**
     * This method is to generate test data
     * @throws DukeException throw exception during building data, no exception here
     */
    public void buildTestData() throws DukeException {
        model = new ModelController();
        model.getMemberList().clear();
        model.getTaskList().clear();
        logicController = new LogicController(model);
        TasksCounter tc = new TasksCounter(logicController.model.getTaskList());
        new Window(tc, logicController);

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
        LinkCommandParser.parseLinkCommand("1 3 2 4 6 8 /to test1").execute(model);
        LinkCommandParser.parseLinkCommand("1 2 3 5 /to test2").execute(model);
        LinkCommandParser.parseLinkCommand("2 5 7 /to test3").execute(model);
        LinkCommandParser.parseLinkCommand("2 /to test4").execute(model);
    }

    @Test
    public void listMember_all_empty() throws DukeException {
        model = new ModelController();
        model.getMemberList().clear();
        model.getTaskList().clear();
        logicController = new LogicController(model);
        TasksCounter tc = new TasksCounter(logicController.model.getTaskList());
        new Window(tc, logicController);

        Command command = ListMembersParser.parseListMembers("");
        CommandOutput out = command.execute(model);
        assertEquals("There are currently no member in project manager", out.getOutputToUser());
    }

    @Test
    public void listMember_progress_empty() throws DukeException {
        model = new ModelController();
        model.getMemberList().clear();
        model.getTaskList().clear();
        logicController = new LogicController(model);
        TasksCounter tc = new TasksCounter(logicController.model.getTaskList());
        new Window(tc, logicController);

        Command command = ListMembersParser.parseListMembers("progress");
        CommandOutput out = command.execute(model);
        assertEquals("There are currently no member in project manager", out.getOutputToUser());
    }

    @Test
    public void listMember_todoNum_empty() throws DukeException {
        model = new ModelController();
        model.getMemberList().clear();
        model.getTaskList().clear();
        logicController = new LogicController(model);
        TasksCounter tc = new TasksCounter(logicController.model.getTaskList());
        new Window(tc, logicController);

        Command command = ListMembersParser.parseListMembers("todonum");
        CommandOutput out = command.execute(model);
        assertEquals("There are currently no member in project manager", out.getOutputToUser());
    }

    @Test
    public void listMember_all_success() throws DukeException {
        buildTestData();
        Command command = ListMembersParser.parseListMembers("");
        CommandOutput out = command.execute(model);
        assertEquals("These are members in member list:\n"
                + "1. test1\n"
                + "2. test2\n"
                + "3. test3\n"
                + "4. test4\n"
                + "5. test5",
                out.getOutputToUser());
        model.getMemberList().clear();
        model.getTaskList().clear();
        model.save();
    }

    @Test
    public void listMember_progress_success() throws DukeException {
        buildTestData();
        AddMemberParser.parseAddMember("test6").execute(model);
        LinkCommandParser.parseLinkCommand("1 3 5 2 /to test6").execute(model);
        DoneCommandParser.parseDoneCommand("3 5 6").execute(model);
        Command command = ListMembersParser.parseListMembers("progress");
        CommandOutput out = command.execute(model);
        assertEquals("Here are the members in order of progress.\n"
                + "5. test5 progress is: 1.0\n"
                + "3. test3 progress is: 0.67\n"
                + "1. test1 progress is: 0.5\n"
                + "2. test2 progress is: 0.5\n"
                + "6. test6 progress is: 0.5\n"
                + "4. test4 progress is: 0.0",
                out.getOutputToUser());
        model.getMemberList().clear();
        model.getTaskList().clear();
        model.save();
    }

    @Test
    public void listMember_todoNum_success() throws DukeException {
        buildTestData();
        AddMemberParser.parseAddMember("test6").execute(model);
        LinkCommandParser.parseLinkCommand("1 3 5 2 /to test6").execute(model);
        DoneCommandParser.parseDoneCommand("2 4").execute(model);
        Command command = ListMembersParser.parseListMembers("todonum");
        CommandOutput out = command.execute(model);
        assertEquals("Here are the members in order of num of todo tasks.\n"
                + "4. test4 has todo tasks: 0\n"
                + "5. test5 has todo tasks: 0\n"
                + "3. test3 has todo tasks: 1\n"
                + "1. test1 has todo tasks: 3\n"
                + "2. test2 has todo tasks: 3\n"
                + "6. test6 has todo tasks: 3",
                out.getOutputToUser());
        model.getMemberList().clear();
        model.getTaskList().clear();
        model.save();
    }
}
