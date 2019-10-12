package temp;

import controllers.temp.TaskFactory;
import exceptions.DukeException;
import models.temp.tasks.ITask;
import models.temp.tasks.Tentative;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TentativeTest {
    private TaskFactory taskFactory = new TaskFactory();

    @Test
    public void alwaysTrue() {
        assertEquals(2, 2);
    }

    @Test
    void tentativeTaskCreation() {
        try {
            String input = "tentative do Tutorial /at 23/09/2019 1600 or 19/10/2019 1200";
            String[] dummyTentativeDateStrings = {"23 September 2019 04.00 PM", "19 October 2019 12.00 PM"};

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hhmm");
            Date[] dummyTentativeDateObjects = {formatter.parse("23/09/2019 1600"), formatter.parse("19/10/2019 1200")};
            Tentative dummyTask = new Tentative("do Tutorial", dummyTentativeDateStrings, dummyTentativeDateObjects);
            ITask generatedTask = taskFactory.createTask(input);
            assertEquals(generatedTask.getDescription(), dummyTask.getDescription());
        } catch (DukeException | ParseException e) {
            e.printStackTrace();
        }
    }
    
}
