package duke;

import org.junit.jupiter.api.Test;
import duke.exception.DukeException;
<<<<<<< HEAD:src/main/oldduke/test/TaskListTest.java
import duke.list.tasklist.TaskList;
=======
import duke.list.TaskList;
>>>>>>> branch-booking-list:src/test/java/duke/TaskListTest.java
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskListTest {

    /**
     * Creates test cases by adding tasks to taskList.
     * @return list of tasks in taskList
     */
    public TaskList taskListTestStr() {
        TaskList taskList = new TaskList();
        taskList.addDeadlineTask("return book", "2/12/2019 1800");
        taskList.addTodoTask("text ui testing");
        taskList.addDeadlineTask("assignment 3", "9/9/2019 1800");
        taskList.addEventTask("project meeting", "Aug 6th 2-4pm");
        taskList.addTodoTask("JUnit testing");
        return taskList;
    }

    /**
     * Tests findTask method.
     * @return list of items corresponding to the found items
     * @throws DukeException if not able to find any matching items
     */
    public ArrayList<String> findActualTestStr() throws DukeException {
        String description = "test";
        ArrayList<String> arrListFind = new ArrayList<>();
        for (int i = 0; i < taskListTestStr().findTask(description).size(); i++) {
            arrListFind.add((i + 1) + " " + taskListTestStr().findTask(description).get(i));
        }
        return arrListFind;
    }

    /**
     * Tests deleteTask method.
     * @return list of remaining tasks in taskList
     */
    public ArrayList<String> deleteActualTestStr() {
        ArrayList<String> arrListDelete = new ArrayList<>();
        TaskList taskList = new TaskList();
        taskList.addDeadlineTask("return book", "2/12/2019 1800");
        taskList.addTodoTask("text ui testing");
        taskList.addDeadlineTask("assignment 3", "9/9/2019 1800");
        taskList.addEventTask("project meeting", "Aug 6th 2-4pm");
        taskList.addTodoTask("JUnit testing");
        taskList.deleteTask(0);
        for (int i = 0; i < taskList.getSize(); i++) {
            arrListDelete.add((i + 1) + " " + taskList.getTaskList().get(i));
        }
        return arrListDelete;
    }

    /**
     * Tests listTask method.
     * @return list of tasks in taskList
     */
    public ArrayList<String> listActualTestStr() {
        ArrayList<String> arrList = new ArrayList<>();
        taskListTestStr().getTaskList().remove(0);
        for (int i = 0; i < taskListTestStr().getSize(); i++) {
            arrList.add(taskListTestStr().listTask().get(i));
        }
        return arrList;
    }

    /**
     * States the expected results for findTask method.
     * @return list of expected items
     */
    public ArrayList<String> addExpectedFindTestStr() {
        ArrayList<String> arrListTest = new ArrayList<>();
        arrListTest.add("1. [T][-] text ui testing");
        arrListTest.add("2. [T][-] JUnit testing");
        return arrListTest;
    }

    /**
     * States the expected results for deleteTask method.
     * @return list of expected items
     */
    public ArrayList<String> addExpectedDeleteTestStr() {
        ArrayList<String> arrListTest = new ArrayList<>();
        arrListTest.add("1. [T][-] text ui testing");
        arrListTest.add("2. [D][-] assignment 3 (by: 9th of September 2019, 6.00pm)");
        arrListTest.add("3. [E][-] project meeting (at: Aug 6th 2-4pm)");
        arrListTest.add("4. [T][-] JUnit testing");
        return arrListTest;
    }

    /**
     * States the expected results for listTask method.
     * @return list of expected items
     */
    public ArrayList<String> addExpectedListTestStr() {
        ArrayList<String> arrListTest = new ArrayList<>();
        arrListTest.add("     1. [D][-] return book (by: 2nd of December 2019, 6.00pm)");
        arrListTest.add("     2. [T][-] text ui testing");
        arrListTest.add("     3. [D][-] assignment 3 (by: 9th of September 2019, 6.00pm)");
        arrListTest.add("     4. [E][-] project meeting (at: Aug 6th 2-4pm)");
        arrListTest.add("     5. [T][-] JUnit testing");
        return arrListTest;
    }

    @Test
    public void shouldTestFindCommand() throws DukeException {
        assertEquals(addExpectedFindTestStr(), findActualTestStr());
    }

    @Test
    public void shouldTestDeleteCommand() {
        assertEquals(addExpectedDeleteTestStr(), deleteActualTestStr());
    }

    @Test
    public void shouldTestListCommand() {
        assertEquals(addExpectedListTestStr(), listActualTestStr());
    }
}
