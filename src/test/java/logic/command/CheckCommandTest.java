package logic.command;

import common.DukeException;
import logic.parser.AddTaskParser;
import logic.parser.CheckCommandParser;
import logic.parser.DoneCommandParser;
import logic.parser.LinkCommandParser;
import model.Model;
import model.ModelController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author yuyanglin28

public class CheckCommandTest {

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
    public void checkCommand_hasCrash() throws DukeException {
        buildTestData();
        CommandOutput out = CheckCommandParser.parseCheckCommand("").execute(model);
        assertEquals("Here are the busy days of some members: \n"
                + "test1: \n"
                + "2019/12/03 2 tasks:\n"
                + "3. task3\n"
                + "5. task5\n"
                + "2019/12/04 3 tasks:\n"
                + "4. task4\n"
                + "2. task2\n"
                + "6. task6\n"
                + "[END OF test1 ]\n"
                + "test2: \n"
                + "2019/12/03 2 tasks:\n"
                + "3. task3\n"
                + "5. task5\n"
                + "[END OF test2 ]\n"
                + "test3: \n"
                + "2019/12/03 2 tasks:\n"
                + "3. task3\n"
                + "5. task5\n"
                + "[END OF test3 ]",
                out.getOutputToUser());
        model.getMemberList().clear();
        model.getTaskList().clear();
        model.save();
    }

    @Test
    public void checkCommand_noCrash() throws DukeException {
        buildTestData();
        DoneCommandParser.parseDoneCommand("1 2 3 4 5 6").execute(model);
        CommandOutput out = CheckCommandParser.parseCheckCommand("").execute(model);
        assertEquals("All tasks have proper deadlines.", out.getOutputToUser());
        model.getMemberList().clear();
        model.getTaskList().clear();
        model.save();
    }
}
