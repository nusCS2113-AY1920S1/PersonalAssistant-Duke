package seedu.duke;

import org.junit.jupiter.api.Test;
import seedu.duke.common.parser.CommandParseHelper;
import seedu.duke.task.TaskList;
import seedu.duke.task.entity.Deadline;
import seedu.duke.task.entity.Event;
import seedu.duke.task.entity.Task;
import seedu.duke.task.entity.ToDo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TaskListTest {
    public TaskList createTaskList() {
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
        return taskList;
    }

    @Test
    public void findKeywordTest() {
        TaskList taskList = createTaskList();

        // item does not exist
        String testResult1 = "There is no matching task in your list.";
        assertEquals(testResult1, taskList.findKeyword("abcde"));

        // exact match
        String testResult2 = "Here are the matching tasks in your list:" + System.lineSeparator()
                + "1. [T][X] random";
        assertEquals(testResult2, taskList.findKeyword("random"));

        // match part of description and tag
        String testResult3 = "Here are the matching tasks in your list:" + System.lineSeparator()
                + "1. [D][X] concatenate (by: 21/12/2019 1200)" + System.lineSeparator()
                + "2. [D][X] tabby (by: 02/02/2020 0000) #cat Priority: MEDIUM" + System.lineSeparator()
                + "3. [E][X] SocCat (by: 29/02/2020 2359)" + System.lineSeparator()
                + "\tAfter which: feed" + System.lineSeparator()
                + "4. [E][X] cat (by: 01/01/1980 1234)(Past) Priority: LOW";
        assertEquals(testResult3, taskList.findKeyword("cat"));

        // match all tasks with time
        String testResult4 = "Here are the matching tasks in your list:" + System.lineSeparator()
                + "1. [D][X] concatenate (by: 21/12/2019 1200)" + System.lineSeparator()
                + "2. [D][X] tabby (by: 02/02/2020 0000) #cat Priority: MEDIUM" + System.lineSeparator()
                + "3. [E][X] SocCat (by: 29/02/2020 2359)" + System.lineSeparator()
                + "\tAfter which: feed" + System.lineSeparator()
                + "4. [E][X] cat (by: 01/01/1980 1234)(Past) Priority: LOW";
        assertEquals(testResult4, taskList.findKeyword("by"));
    }

    @Test
    public void deleteTest() {
        TaskList taskList = createTaskList();

        //positive cases
        String testResult1 = "Noted. I've removed this task:" + System.lineSeparator()
                + "[D][X] concatenate (by: 21/12/2019 1200)" + System.lineSeparator()
                + "Now you have 5 tasks in the list.";
        String testResult2 = "Noted. I've removed this task:" + System.lineSeparator()
                + "[E][X] SocCat (by: 29/02/2020 2359)" + System.lineSeparator()
                + "\tAfter which: feed" + System.lineSeparator()
                + "Now you have 4 tasks in the list.";
        try {
            assertEquals(testResult1, taskList.delete(2));
            assertEquals(testResult2, taskList.delete(3));
        } catch (CommandParseHelper.CommandParseException e) {
            fail("Unable to parse input");
        }

        // negative cases
        assertThrows(CommandParseHelper.CommandParseException.class, () -> taskList.delete(4));
        assertThrows(CommandParseHelper.CommandParseException.class, () -> taskList.delete(5));
        assertThrows(CommandParseHelper.CommandParseException.class, () -> taskList.delete(-1));
    }

    @Test
    public void snoozedTest() {
        TaskList taskList = createTaskList();

        // positive cases
        String testResult1 = "This task cannot be snoozed";
        String testResult2 = "Noted. I've snoozed task 3 by 3 days";
        String testResult3 = "Noted. I've snoozed task 6 by 0 days";
        try {
            assertEquals(testResult1, taskList.snoozed(0, 3));
            assertEquals(testResult2, taskList.snoozed(2, 3));
            assertEquals(testResult3, taskList.snoozed(5, 0));
        } catch (CommandParseHelper.CommandParseException e) {
            fail("Unable to parse input");
        }

        // negative cases
        // invalid index
        assertThrows(CommandParseHelper.CommandParseException.class, () -> taskList.snoozed(-1, 0));
        assertThrows(CommandParseHelper.CommandParseException.class, () -> taskList.snoozed(6, 3));
        // invalid duration
        String failedResult1 = "Number of days snoozed not specified. Default is used. Noted. I've snoozed task 3 by 3 days";
        String failedResult2 = "Number of days snoozed should be integer of range 1 ~ 99999.";
        try {
            assertEquals(failedResult1, taskList.snoozed(2, -1));
            assertEquals(failedResult2, taskList.snoozed(3, 100000));
        } catch (CommandParseHelper.CommandParseException e) {
            fail();
        }
    }

    @Test
    public void setDoAfterTest() {
        TaskList taskList = createTaskList();

        //positive cases
        String testResult1 = "Do after task testing a has been added to task 2";
        String testResult2 = "Do after task test b  has been added to task 6";
        String testResult3 = "Do after task doafter descrip has been added to task 6";
        String testResult4 = "Do after task -/abc has been added to task 3";
        try {
            assertEquals(testResult1, taskList.setDoAfter(1, "testing a"));
            assertEquals(testResult2, taskList.setDoAfter(5, "test b "));
            assertEquals(testResult3, taskList.setDoAfter(5, "doafter descrip"));
            assertEquals(testResult4, taskList.setDoAfter(2, "-/abc"));
        } catch (CommandParseHelper.CommandParseException e) {
            fail("Unable to parse input");
        }

        // negative cases - invalid index
        assertThrows(CommandParseHelper.CommandParseException.class, () ->
                taskList.setDoAfter(-1, "abc"));
        assertThrows(CommandParseHelper.CommandParseException.class, () ->
                taskList.setDoAfter(100000, "abc"));
    }

    @Test
    public void setTimeTest() {
        TaskList taskList = createTaskList();

        // positive cases
        String testResult1 = "Time for task 4 has been changed to 11/11/2019 0800";
        String testResult2 = "Time for task 6 has been changed to 03/01/2019 0659";
        String testResult3 = "Time for task 6 has been changed to 31/08/2019 1334";
        try {
            assertEquals(testResult1, taskList.setTime(3, "11/11/2019 0800"));
            assertEquals(testResult2, taskList.setTime(5, "03/01/2019 0659"));
            assertEquals(testResult3, taskList.setTime(5, "31/08/2019 1334"));
        } catch (CommandParseHelper.CommandParseException e) {
            fail("Unable to parse input");
        }
        // check if time updated correctly
        assertEquals(LocalDateTime.parse("11/11/2019 0800", DateTimeFormatter.ofPattern("dd/MM/uuuu HHmm")),
                taskList.get(3).getTime());
        assertEquals(LocalDateTime.parse("31/08/2019 1334", DateTimeFormatter.ofPattern("dd/MM/uuuu HHmm")),
                taskList.get(5).getTime());

        // negative cases - invalid index
        assertThrows(CommandParseHelper.CommandParseException.class, () ->
                taskList.setTime(-1, "11/11/2019 0200"));
        assertThrows(CommandParseHelper.CommandParseException.class, () ->
                taskList.setTime(100000, "04/05/2020 2113"));
        // invalid description string
        assertThrows(CommandParseHelper.CommandParseException.class, () ->
                taskList.setTime(2, "-/abc"));
        assertThrows(CommandParseHelper.CommandParseException.class, () ->
                taskList.setTime(2, "29/02/2019 1200"));
    }

    @Test
    public void getTaskGuiStringListTest() {
        TaskList taskList = createTaskList();
        ArrayList<String> expectedResult1 = new ArrayList<>();
        expectedResult1.add("[T][X] random");
        expectedResult1.add("[T][X] something Priority: HIGH");
        expectedResult1.add("[D][X] concatenate (by: 21/12/2019 1200)");
        expectedResult1.add("[D][X] tabby (by: 02/02/2020 0000) #cat Priority: MEDIUM");
        expectedResult1.add("[E][X] SocCat (by: 29/02/2020 2359)" + System.lineSeparator() + "\tAfter which: feed");
        expectedResult1.add("[E][X] cat (by: 01/01/1980 1234)(Past) Priority: LOW");
        assertEquals(expectedResult1, taskList.getTaskGuiStringList());

        TaskList emptyTaskList = new TaskList();
        ArrayList<String> emptyResult = new ArrayList<>();
        assertEquals(emptyResult, emptyTaskList.getTaskGuiStringList());
    }

    @Test
    public void setPriorityTest() {
        TaskList taskList = createTaskList();

        //positive cases
        String testResult1 = "Priority of task 2 is set to HIGH";
        String testResult2 = "Priority of task 6 is set to MEDIUM";
        String testResult3 = "Priority of task 6 is set to LOW";
        String testResult4 = "Priority of task 3 is set to NULL";
        try {
            assertEquals(testResult1, taskList.setPriority(1, Task.Priority.HIGH));
            assertEquals(testResult2, taskList.setPriority(5, Task.Priority.MEDIUM));
            assertEquals(testResult3, taskList.setPriority(5, Task.Priority.LOW));
            assertEquals(testResult4, taskList.setPriority(2, Task.Priority.NULL));
        } catch (CommandParseHelper.CommandParseException e) {
            fail("Unable to parse input");
        }
        // check if priority updated successfully
        assertEquals(Task.Priority.HIGH, taskList.get(1).getPriority());
        assertEquals(Task.Priority.LOW, taskList.get(5).getPriority());
        assertEquals(Task.Priority.NULL, taskList.get(2).getPriority());

        // negative cases - invalid index
        assertThrows(CommandParseHelper.CommandParseException.class, () ->
                taskList.setPriority(-1, Task.Priority.HIGH));
        assertThrows(CommandParseHelper.CommandParseException.class, () ->
                taskList.setPriority(100000, Task.Priority.LOW));
    }

    @Test
    public void setTagsTest() {
        TaskList taskList = createTaskList();

        //positive cases
        ArrayList<String> testArray1 = new ArrayList<>();
        testArray1.add("aaa");
        testArray1.add("bbb");
        testArray1.add("abc");
        ArrayList<String> testArray2 = new ArrayList<>();
        testArray2.add("---");

        String testResult1 = "Tags of task 2 has been updated";
        String testResult2 = "Tags of task 6 has been updated";
        String testResult3 = "Tags of task 6 has been updated";
        try {
            assertEquals(testResult1, taskList.setTags(1, testArray1));
            assertEquals(testResult2, taskList.setTags(5, testArray1));
            assertEquals(testResult3, taskList.setTags(5, testArray2));
        } catch (CommandParseHelper.CommandParseException e) {
            fail("Unable to parse input");
        }
        // check if tags updated successfully
        assertEquals(testArray1, taskList.get(1).getTags());
        assertEquals(testArray2, taskList.get(5).getTags());

        // negative cases - invalid index
        assertThrows(CommandParseHelper.CommandParseException.class, () ->
                taskList.setTags(-1, testArray1));
        assertThrows(CommandParseHelper.CommandParseException.class, () ->
                taskList.setTags(100000, testArray1));
    }

    @Test
    public void findClashTest() {
        TaskList taskList = createTaskList();
        ArrayList<String> blankList = new ArrayList<>();

        // positive clash response
        Task clashTask = new Deadline("music lesson", LocalDateTime.parse("21/12/2019 1200",
                DateTimeFormatter.ofPattern("dd/MM/uuuu HHmm")), null, blankList, Task.Priority.NULL, blankList);
        TaskList expectedClash = new TaskList();
        expectedClash.add(taskList.get(2));
        assertEquals(expectedClash, taskList.findClash(clashTask));

        // positive no clash response
        Task notClashTask = new Deadline("something", LocalDateTime.parse("23/12/2019 1930",
                DateTimeFormatter.ofPattern("dd/MM/uuuu HHmm")), null, blankList, Task.Priority.NULL, blankList);
        TaskList expectedNotClash = new TaskList();
        assertEquals(expectedNotClash, taskList.findClash(notClashTask));
    }

    @Test
    public void clearListTest(){
        TaskList taskList = createTaskList();
        String expectedResponse = "Task List has been cleared";
        assertEquals(expectedResponse, taskList.clearList());

        // check if list has been cleared properly
        TaskList blankList = new TaskList();
        assertEquals(blankList, taskList);
    }
}