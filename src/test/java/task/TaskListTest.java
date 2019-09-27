package task;

import exception.DukeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TaskListTest {
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
            fail();
        } catch (DukeException e) {
            assertEquals("That is NOT a valid integer", e.getMessage());
        }

    }

    @Test
    void testBanishDelete() throws DukeException {
        assertEquals("[T][N] Send even more Help", testTaskListSave.get(0).toList());
        testTaskListSave.banishDelete("1");
        assertEquals("Noted. I've removed this task:\n"
                + "  [T][N] Send even more Help\r\n"
                + "Now you have 3 tasks in the list.\r\n", systemOutput.toString());
        assertEquals(3, testTaskListSave.size());
        assertEquals("[R][N] Deliver Help (Every: Day)", testTaskListSave.get(0).toList());

        try {
            testTaskListSave.banishDelete("100");
            fail();
        } catch (Exception e) {
            assertEquals("The index was not found within range", e.getMessage());
        }

        try {
            testTaskListSave.banishDelete("asdasdasd");
            fail();
        } catch (Exception e) {
            assertEquals("That is NOT a valid integer", e.getMessage());
        }
    }

    @Test
    void testSnoozeTask() throws DukeException {
        ByteArrayOutputStream freshOutput = new ByteArrayOutputStream();
        testTaskListSave.add("event", "Sleep /at 01-01-1970 2200");
        System.setOut(new PrintStream(freshOutput)); //sets the system output to a different stream
        testTaskListSave.snoozeTask("5 /to 12-12-2013 2345");
        assertEquals("Noted. I've snoozed this task:\n"
                + "  [E][N] Sleep (At: 12-12-2013 2345)\r\n", freshOutput.toString());

        try {
            testTaskListSave.snoozeTask("5 /to /to 12-12-2013 2345");
            fail();
        } catch (Exception e) {
            assertEquals("Too many /to in String", e.getMessage());
        }

        try {
            testTaskListSave.snoozeTask("10 /to 12-12-2013 2345");
            fail();
        } catch (Exception e) {
            assertEquals("The index was not found within range", e.getMessage());
        }

        try {
            testTaskListSave.snoozeTask("2 /to 12-12-2013 2345");
            fail();
        } catch (Exception e) {
            assertEquals("Only Events and Deadlines can be snoozed", e.getMessage());
        }

        try {
            testTaskListSave.snoozeTask("asdasdasd");
            fail();
        } catch (Exception e) {
            assertEquals("That is NOT a valid integer", e.getMessage());
        }
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
        assertEquals("Got it. I've added this task:\n"
                + "  [T][N] Sleep\n"
                + "Now you have 5 tasks in the list.\r\n", systemOutput.toString());
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
        assertEquals("Got it. I've added this task:\n"
                + "  [T][N] Sleep\n"
                + "Now you have 5 tasks in the list.\r\n"
                + "Got it. I've added this task:\n"
                + "  [R][N] Sleep (Every: 16 hours)\n"
                + "Now you have 6 tasks in the list.\r\n"
                + "Got it. I've added this task:\n"
                + "  [F][N] Sleep (Need: 8 hours)\n"
                + "Now you have 7 tasks in the list.\r\n"
                + "Got it. I've added this task:\n"
                + "  [A][N] Sleep (After: Work)\n"
                + "Now you have 8 tasks in the list.\r\n"
                + "Got it. I've added this task:\n"
                + "  [E][N] Sleep (At: 01-01-1970 2200)\n"
                + "Now you have 9 tasks in the list.\r\n"
                + "Got it. I've added this task:\n"
                + "  [D][N] Sleep (By: 01-01-1970 2200)\n"
                + "Now you have 10 tasks in the list.\r\n"
                + "Got it. I've added this task:\n"
                + "  [W][N] Sleep (Between: Jan 25th and Jan 30th)\n"
                + "Now you have 11 tasks in the list.\r\n", systemOutput.toString());

        try {
            testTaskListSave.add("todasdasdo", "Sleep");
            fail();
        } catch (Exception e) {
            assertEquals("What the Hell happened here?\n"
                    + "Command passed successfully to tasklist.add, not found in any case", e.getMessage());
        }
    }

    @Test
    void testFind() throws DukeException {
        testTaskListSave.find("Sleep");
        assertEquals("Here are the matching tasks in your list:\r\n"
                + "4. [W][N] Sleeping (Between: Jan 15th and 25th)\r\n", systemOutput.toString());
    }

    @Test
    void testNotFound() throws DukeException {
        testTaskListSave.find("asdasdasdasdasfsdfsdfsdfsdf");
        assertEquals("There are no matching tasks in the list\r\n", systemOutput.toString());
    }

    @Test
    void testViewSchedule() throws DukeException {
        ByteArrayOutputStream freshOutput = new ByteArrayOutputStream();
        testTaskListSave.add("event", "Sleep /at 01-01-1970 2200");
        System.setOut(new PrintStream(freshOutput)); //sets the system output to a different stream
        testTaskListSave.view_schedule("01-01-1970");
        assertEquals("Here's what the day looks like:\r\n"
                + "5. [E][N] Sleep (At: 01-01-1970 2200)\r\n", freshOutput.toString());
    }

    @Test
    void testNoSchedule() throws DukeException {
        testTaskListSave.view_schedule("02-02-1900 2310");
        assertEquals("You have no tasks today. Enjoy!\r\n", systemOutput.toString());
    }

    @Test
    void testConflictCheck() throws DukeException {
        ByteArrayOutputStream freshOutput = new ByteArrayOutputStream();
        testTaskListSave.add("event", "Sleep /at 01-01-1970 2200");
        testTaskListSave.add("event", "Work /at 01-01-1970 2200");
        System.setOut(new PrintStream(freshOutput)); //sets the system output to a different stream
        testTaskListSave.conflict_check();
        assertEquals("There is a conflict in the schedule!\r\n", freshOutput.toString());
    }

    @Test
    void testEmptyPrint() {
        TaskList testEmptyList = new TaskList();
        testEmptyList.print();
        assertEquals("Whoops, there doesn't seem to be anything "
                + "here at the moment\r\n", systemOutput.toString());
    }

    @Test
    void testPrint() {
        testTaskListSave.print();
        assertEquals("1. [T][N] Send even more Help\r\n"
                + "2. [R][N] Deliver Help (Every: Day)\r\n"
                + "3. [A][N] Send less help (After: Sending Enough)\r\n"
                + "4. [W][N] Sleeping (Between: Jan 15th and 25th)\r\n", systemOutput.toString());
    }

    @Test
    void testSelect() throws DukeException {
        ByteArrayOutputStream freshOutput = new ByteArrayOutputStream();
        testTaskListSave.add("event", "Sleep /at 01-01-1970 2200 "
                                                        + "/at 02-02-1971 2200 "
                                                        + "/at 03-03-1972 2200 "
                                                        + "/at 04-04-1973 2200");
        System.setOut(new PrintStream(freshOutput)); //sets the system output to a different stream
        testTaskListSave.select("5 1");
        assertEquals("Tentative Date selected successfully\r\n", freshOutput.toString());

        try {
            testTaskListSave.select("5 1");
            fail();
        } catch (Exception e) {
            assertEquals("There are no Tentative Slots to be chosen from", e.getMessage());
        }
    }

    @Test
    void testSelectFail() throws DukeException {
        testTaskListSave.add("event", "Sleep /at 01-01-1970 2200 "
                                                        + "/at 02-02-1971 2200 "
                                                        + "/at 03-03-1972 2200 "
                                                        + "/at 04-04-1973 2200");


        try {
            testTaskListSave.select("100 1");
            fail();
        } catch (Exception e) {
            assertEquals("The index was not found within task range", e.getMessage());
        }

        try {
            testTaskListSave.select("3 1");
            fail();
        } catch (Exception e) {
            assertEquals("The Task you chose is not the Event Type!", e.getMessage());
        }

        try {
            testTaskListSave.select("5 10");
            fail();
        } catch (Exception e) {
            assertEquals("Not a valid Selection (out of range)", e.getMessage());
        }
    }
}