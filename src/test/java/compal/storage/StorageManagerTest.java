package compal.storage;

import compal.model.tasks.Deadline;
import compal.model.tasks.DoAfterTasks;
import compal.model.tasks.FixedDurationTask;
import compal.model.tasks.Event;
import compal.model.tasks.RecurringTask;
import compal.model.tasks.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static compal.model.tasks.Task.Priority.high;

public class StorageManagerTest {
    //***Class Properties/Variables***--------------------------------------------------------------------------------->
    private static final String userPreferencesFilePath = "./prefs.txt";

    private StorageManager sm;
    private String description = "Test content";
    private String date = "01/10/2019";
    private Task.Priority priority = high;
    private String startTime = "1130";
    private String endTime = "1230";
    private String symbol = "RT";
    private Deadline deadline;
    private DoAfterTasks doAfterTasks;
    private Event event;
    private FixedDurationTask fdt;
    private RecurringTask recurringTask;

    /**
     * Set up all tasks and storage manager.
     *
     * @author peize
     */
    @BeforeEach
    public void setup() {
        sm = new StorageManager();
        deadline = new Deadline(description, priority, date, endTime);
        doAfterTasks = new DoAfterTasks(description, priority, date);
        event = new Event(description, priority, date, startTime, endTime);
        fdt = new FixedDurationTask(description, priority, date, startTime, endTime);
        recurringTask = new RecurringTask(description, priority, date, startTime, endTime, symbol);
    }

    @Test
    public void loadCompalTest() {
        ArrayList<Task> tempList = new ArrayList<>();
        tempList.add(deadline);
        tempList.add(doAfterTasks);
        tempList.add(event);
        tempList.add(fdt);
        tempList.add(recurringTask);
        ArrayList<Task> tempList2;
        ArrayList<Task> tempList3;
        tempList3 = sm.loadCompal();
        sm.saveCompal(tempList);
        tempList2 = sm.loadCompal();
        for (int i = 0; i < tempList.size(); i++) {
            assertEquals(tempList.get(i).getSymbol(), tempList2.get(i).getSymbol());
            assertEquals(tempList.get(i).getDescription(), tempList2.get(i).getDescription());
            assertEquals(tempList.get(i).getDate(), tempList2.get(i).getDate());
            assertEquals(tempList.get(i).getStartTime(), tempList2.get(i).getStartTime());
            assertEquals(tempList.get(i).getEndTime(), tempList2.get(i).getEndTime());
            assertEquals(tempList.get(i).gethasReminder(), tempList2.get(i).gethasReminder());
            assertEquals(tempList.get(i).getisDone(), tempList2.get(i).getisDone());
            assertEquals(tempList.get(i).getPriority(), tempList2.get(i).getPriority());
        }
        sm.saveCompal(tempList3);
    }

    @Test
    public void saveUserNameTest() {
        String name = "Test";
        String name2 = "";
        String tempName = "";
        try {
            File f = new File(userPreferencesFilePath);
            BufferedReader br = new BufferedReader(new FileReader(f));
            name2 = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        sm.storeUserName(name);
        try {
            File f = new File(userPreferencesFilePath);
            BufferedReader br = new BufferedReader(new FileReader(f));
            tempName = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(name, tempName);
        sm.storeUserName(name2);
    }

    @Test
    public void getUserNameTest() {
        String realName = "";
        try {
            File f = new File(userPreferencesFilePath);
            BufferedReader br = new BufferedReader(new FileReader(f));
            realName = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(realName, sm.getUserName());
    }
}
