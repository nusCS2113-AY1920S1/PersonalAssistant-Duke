import Events.EventTypes.Deadline;
import Events.EventTypes.Event;
import Events.EventTypes.Task;
import Events.EventTypes.ToDo;
import Events.Formatting.DateObj;
import Events.Storage.TaskList;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DukeTest {
    @Test
    public void dummyTest(){
        assertEquals(2, 2);
    }

    @Test
    public void clashTest(){
        ArrayList<String> readFromFile = new ArrayList<String>();
        String fileContent;
        fileContent = "[T][✘] hello";
        readFromFile.add(fileContent);
        fileContent = "[T][✓] gpaweijgpweijg";
        readFromFile.add(fileContent);
        fileContent = "[E][✘] apfiejpawi (at: 2/12/2019 1800)";
        readFromFile.add(fileContent);

        TaskList tasksTest = new TaskList(readFromFile);
        Task testTask = new Event("hello", "2/12/2019 1800");
        assertEquals(false, tasksTest.addTask(testTask));
    }

    @Test
    public void viewScheduleTest() {
        ArrayList<String> testListString = new ArrayList<>();
        TaskList testList = new TaskList(testListString);
        Task toDoTest = new ToDo("cheese");
        testList.addTask(toDoTest);
        Task deadlineTest1 = new Deadline("eat cheese", "19/09/2019 1900");
        testList.addTask(deadlineTest1);
        Task deadlineTest2 = new Deadline("buy cheese", "19/09/2019 2000");
        testList.addTask(deadlineTest2);
        Task deadlineTest3 = new Deadline("throw cheese", "19/09/2020 1000");
        testList.addTask(deadlineTest3);
        Task eventTest = new Event("cheese party", "20/09/2019 2100");
        testList.addTask(eventTest);
        String dateToView = "19/09/2019";
        String foundTask = "";
        int viewIndex = 1;
        DateObj findDate = new DateObj(dateToView);
        for (Task testViewTask : testList.getTaskArrayList()) {
            if (testViewTask.toString().contains(findDate.toOutputString())) {
                foundTask += viewIndex + ". " + testViewTask.toString() + "\n";
                viewIndex++;
            }
        }
        boolean isTasksFound = !foundTask.isEmpty();
        assertEquals(true, isTasksFound);
    }
}
