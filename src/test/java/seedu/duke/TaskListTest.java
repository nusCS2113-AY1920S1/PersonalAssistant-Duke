package seedu.duke;

import org.junit.jupiter.api.Test;
import seedu.duke.task.TaskList;
import seedu.duke.task.entity.Deadline;
import seedu.duke.task.entity.Event;
import seedu.duke.task.entity.Task;
import seedu.duke.task.entity.ToDo;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskListTest {

    @Test
    public void findKeywordTest() {
        ArrayList<String> blankList = new ArrayList<>();
        ArrayList<String> testTag = new ArrayList<>();

        testTag.add("cat");
        Task task1 = new ToDo("random", null, null, blankList, Task.Priority.NULL, blankList);
        Task task2 = new ToDo("something", null, null, blankList, Task.Priority.HIGH, blankList);
        Task task3 = new Deadline("concatenate", LocalDateTime.parse("21/12/2019 1200",
                DateTimeFormatter.ofPattern("dd/MM/uuuu HHmm")), null, blankList, Task.Priority.NULL, blankList);
        Task task4 = new Deadline("tabby", LocalDateTime.parse("02/02/2020 0000",
                DateTimeFormatter.ofPattern("dd/MM/uuuu HHmm")), null, testTag, Task.Priority.MEDIUM, blankList);
        Task task5 = new Event("SocCat", LocalDateTime.parse("29/02/2020 2359",
                DateTimeFormatter.ofPattern("dd/MM/uuuu HHmm")), "feed", blankList, Task.Priority.NULL, blankList);
        Task task6 = new Event("cat", LocalDateTime.parse("01/01/1980 1234",
                DateTimeFormatter.ofPattern("dd/MM/uuuu HHmm")), null, blankList, Task.Priority.LOW, blankList);
        TaskList taskList = new TaskList();
        taskList.add(task1);
        taskList.add(task2);
        taskList.add(task3);
        taskList.add(task4);
        taskList.add(task5);
        taskList.add(task6);

        String testResult1 = "There is no matching task in your list.";
        assertEquals(testResult1, taskList.findKeyword("abcde"));

        String testResult2 = "Here are the matching tasks in your list:" + System.lineSeparator()
                + "1. [T][X] random";
        assertEquals(testResult2, taskList.findKeyword("random"));

        String testResult3 = "Here are the matching tasks in your list:\n"
                + "1. [D][X] concatenate (by: 21/12/2019 1200)\n"
                + "2. [D][X] tabby (by: 02/02/2020 0000) #cat Priority: MEDIUM\n"
                + "3. [E][X] SocCat (by: 29/02/2020 2359)\n"
                + "     After which: feed\n"
                + "4. [E][X] cat (by: 01/01/1980 1234)(Past) Priority: LOW";
        assertEquals(testResult3, taskList.findKeyword("cat"));

        String testResult4 = "Here are the matching tasks in your list:" + System.lineSeparator()
                + "1. [D][X] concatenate";
        assertEquals(testResult4, taskList.findKeyword("by"));
    }

}
