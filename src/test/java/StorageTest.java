import duke.DateTime;
import duke.Storage;
import duke.exceptions.DukeException;
import duke.items.tasks.Task;
import duke.items.tasks.Todo;
import duke.items.tasks.Deadline;
import duke.items.tasks.Event;
import duke.items.tasks.Within;
import duke.items.tasks.Tentative;
import duke.items.tasks.Recurring;
import duke.items.tasks.FileTask;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class StorageTest {

    private static final String UPPER_CASE_CHARACTER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER_CASE_CHARACTER = UPPER_CASE_CHARACTER.toLowerCase();
    private static final String NUMBERS = "0123456789";
    private static final String SPECIAL_CHARACTER = "~!@#$%^&*()_+~[]{},./;'<>?\\`-=";
    private static final String CHARACTER_STRING = UPPER_CASE_CHARACTER
            + LOWER_CASE_CHARACTER + NUMBERS + SPECIAL_CHARACTER;

    /**
     * Generates Random String for JUnit testing.
     * @param count String Length
     * @return Random String
     */
    public String randomString(int count) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < count; i++) {
            int character = (int)(Math.random() * CHARACTER_STRING.length());
            builder.append(CHARACTER_STRING.charAt(character));
        }
        return builder.toString();
    }

    @Test
    public void setAndLoadData() throws DukeException {
        File file = new File("data/storageTest.txt");
        file.delete();

        Storage test = new Storage("data/storageTest.txt");
        List<Task> testTasks = new ArrayList<Task>();
        Random random = new Random();
        for (int i = 0; i < 2000; i++) {
            int taskType = random.nextInt(7);
            int randomDone = random.nextInt(2);
            String randomTaskName = randomString(random.nextInt(100) + 2);
            String randomStringForTask = randomString(random.nextInt(20));
            long randomStartDateInMillis = System.currentTimeMillis() + random.nextInt();
            long randomEndDateInMillis = randomStartDateInMillis + random.nextInt() + 60000;
            DateTime randomStartDate = new DateTime(new Date(randomStartDateInMillis));
            DateTime randomEndDate = new DateTime(new Date(randomEndDateInMillis));
            switch (taskType) {
            case 0:
                testTasks.add(new Todo(randomDone, randomTaskName));
                break;
            case 1:
                testTasks.add(new Deadline(randomDone, randomTaskName,randomStartDate));
                break;
            case 2:
                testTasks.add(new Event(randomDone, randomTaskName, randomStartDate, randomEndDate));
                break;
            case 3:
                testTasks.add(new Within(randomDone, randomTaskName, randomStartDate, randomEndDate));
                break;
            case 4:
                testTasks.add(new Tentative(randomDone, randomTaskName, randomStartDate, randomEndDate));
                break;
            case 5:
                testTasks.add(
                        new Recurring(randomTaskName, randomStartDate, randomEndDate, random.nextInt(3000)));
                break;
            case 6:
                testTasks.add(new FileTask(randomDone, randomTaskName));
                break;
            default:
                break;
            }
        }
        test.setData(testTasks);
        List<Task> testTasksCopy = test.loadData();
        for (int i = 0; i < testTasksCopy.size(); i++) {
            String testTasksString = testTasks.get(i).toString();
            String testTasksCopyString = testTasksCopy.get(i).toString();
            assertTrue(testTasksString.equals(testTasksCopyString));
        }
    }

}
