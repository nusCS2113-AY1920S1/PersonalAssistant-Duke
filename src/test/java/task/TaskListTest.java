package task;

import exception.DukeException;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class TaskListTest {
    private TaskList testTaskList = new TaskList();
    private Event testEvent = new Event("Sleep /at 01-01-1970 2200");
    private Todo testTodo = new Todo("0", "Sleep");
    private Deadline testDeadline = new Deadline("Sleep /by 01-01-1970 2200");
    private Fixed testFixed = new Fixed("0", "Sleep","8 hours");
    private After testAfter = new After("Sleep /after Work");
    private TaskList testTaskListSave = new TaskList("T | 0 | Send even more Help\n"
            + "R | 0 | Deliver Help | Day\n"
            + "A | 0 | Send less help | Sending Enough\n"
            + "W | 0 | Sleeping | Jan 15th and 25th");

    //Variable to catch system.out.println, must be converted to string to be usable
    private ByteArrayOutputStream systemOutput = new ByteArrayOutputStream();
    private PrintStream originalOut = System.out;

    @BeforeEach
    void setUpStreams() {
        System.setOut(new PrintStream(systemOutput));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }

    TaskListTest() throws DukeException {
    }

    @Test
    void testSize() {
        assertEquals(4, testTaskListSave.size());
    }

    @Test
    void testMarkDone() throws DukeException {
        testTaskListSave.markDone("1");
        assertTrue(testTaskListSave.get(0).checkCompletion());
        assertEquals("Nice! I've marked this task as done:\n"
                + "  [T][Y] Send even more Help\r\n", systemOutput.toString());

        try {
            testTaskListSave.markDone("100");
        } catch (DukeException e) {
            assertEquals("The index was not found within range", e.getMessage());
        }

        try {
            testTaskListSave.markDone("asdasd");
        } catch (DukeException e) {
            assertEquals("That is NOT a valid integer", e.getMessage());
        }

    }

    @Test
    void testBanishDelete() throws DukeException {
        assertEquals("[T][N] Send even more Help", testTaskListSave.get(0).toList());
        testTaskListSave.banishDelete("1");
        assertEquals(3, testTaskListSave.size());
        assertEquals("[R][N] Deliver Help (Every: Day)", testTaskListSave.get(0).toList());
    }

    @Test
    void testSnoozeTask() {
    }

    @Test
    void testGet() throws DukeException {
        assertEquals("[T][N] Send even more Help", testTaskListSave.get(0).toList());
        assertEquals("Send even more Help", testTaskListSave.get(0).getDescription());
        assertEquals("T", testTaskListSave.get(0).getType());
        assertEquals("N", testTaskListSave.get(0).getStatusIcon());

        assertEquals("[R][N] Deliver Help (Every: Day)", testTaskListSave.get(1).toList());
        assertEquals("Deliver Help", testTaskListSave.get(1).getDescription());
        assertEquals("R", testTaskListSave.get(1).getType());
        assertEquals("N", testTaskListSave.get(1).getStatusIcon());
        assertEquals("Day", testTaskListSave.get(1).getAfter());

        try {
            assertEquals("Dummy string", testTaskListSave.get(9).getAfter());
            fail();
        } catch (Exception e) {
            assertEquals("Requested Task not found within list", e.getMessage());
        }
    }

    @Test
    void testAdd() throws DukeException {
        testTaskListSave.add("todo", "Sleep");
        assertEquals("[T][N] Sleep", testTaskListSave.get(4).toList());
        testTaskListSave.add("recurring", "Sleep /every 16 hours");
        assertEquals("[R][N] Sleep (Every: 16 hours)", testTaskListSave.get(5).toList());
        testTaskListSave.add("fixed", "Sleep /need 8 hours");
        assertEquals("[F][N] Sleep (Need: 8 hours)", testTaskListSave.get(6).toList());
        testTaskListSave.add("after", "Sleep /after Work");
        assertEquals("[A][N] Sleep (After: Work)", testTaskListSave.get(7).toList());
        testTaskListSave.add("event", "Sleep /at 01-01-1970 2200");
        assertEquals("[E][N] Sleep (At: 01-01-1970 2200)", testTaskListSave.get(8).toList());
        testTaskListSave.add("deadline", "Sleep /by 01-01-1970 2200");
        assertEquals("[D][N] Sleep (By: 01-01-1970 2200)", testTaskListSave.get(9).toList());
        testTaskListSave.add("within", "Sleep /between Jan 25th and Jan 30th");
        assertEquals("[W][N] Sleep (Between: Jan 25th and Jan 30th)", testTaskListSave.get(10).toList());
        assertEquals(11, testTaskListSave.size());
    }

    @Test
    void testFind() throws DukeException {
        testTaskListSave.find("Sleep");
        assertEquals("Here are the matching tasks in your list:\r\n"
                + "4. [W][N] Sleeping (Between: Jan 15th and 25th)\r\n", systemOutput.toString());
    }

    @Test
    void testViewSchedule() {
    }

    @Test
    void testConflictCheck() {
    }

    @Test
    void testPrint() {
    }

    @Test
    void testSelect() {
    }
}