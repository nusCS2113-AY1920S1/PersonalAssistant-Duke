package task;

import util.factories.TaskFactory;
import models.task.Task;
import models.task.TaskState;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskTest {
    private TaskFactory consoleInputFactory = new TaskFactory();

    @Test
    public void alwaysTrue() {
        assertEquals(2, 2);
    }

    @Test
    public void testAddTask() {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date dueDate = formatter.parse("19/10/2019");
            ArrayList<String> taskRequirements = new ArrayList<>();
            taskRequirements.add("requirement1");
            Task dummyTask = new Task("task1",1,dueDate,10, TaskState.TODO, taskRequirements);
            Task task = consoleInputFactory.createTask("t/task1 p/1 d/19/10/2019 c/10 s/todo r/requirement1");
            assertEquals(dummyTask.getDetails(),task.getDetails());
            assertEquals(dummyTask.getTaskRequirements(), task.getTaskRequirements());

            SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy");
            Date dueDate2 = formatter2.parse("1/4/1989");
            ArrayList<String> taskRequirements2 = new ArrayList<>();
            taskRequirements2.add("requirement1");
            taskRequirements2.add("requirement2");
            Task dummyTask2 = new Task("task2",2,dueDate2,10, TaskState.OPEN, taskRequirements2);
            Task task2 = consoleInputFactory.createTask("t/task2 p/2 d/1/4/1989 c/10 r/requirement1 r/requirement2");
            assertEquals(dummyTask2.getDetails(),task2.getDetails());
            assertEquals(dummyTask2.getTaskRequirements(), task2.getTaskRequirements());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
