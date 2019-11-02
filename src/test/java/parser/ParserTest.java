package parser;

import command.Command;
import degree.DegreeManager;
import exception.DukeException;
import list.DegreeList;
import main.Duke;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storage.Storage;
import task.TaskList;
import ui.UI;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;



class ParserTest {

    private TaskList testTaskList = new TaskList("T | 0 | Send even more Help\n"
            + "R | 0 | Deliver Help | Day\n"
            + "A | 0 | Send less help | Sending Enough\n"
            + "E | 0 | Sleeping | 01-01-1970 2200");
    private UI testUi = new UI();
    private Storage testStorage = new Storage("dummy.txt", "dummydegree.txt");
    private DegreeList testList = new DegreeList();

    //Variable to catch system.out.println, must be converted to string to be usable
    private ByteArrayOutputStream systemOutput = new ByteArrayOutputStream();
    private PrintStream originalOut = System.out;
    private DegreeManager degreesManager = new DegreeManager();

    @BeforeEach
    void setUpStreams() {
        System.setOut(new PrintStream(systemOutput));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }

    ParserTest() throws DukeException {
    }

    @Test
    void testSingleInput() throws DukeException {

        try {
            Parser.parse("");
            fail();
            //Empty commands should be rooted out in duke, not within parser
        } catch (Exception e) {
            assertEquals("Empty Command!", e.getLocalizedMessage());
        }

        try {
            Parser.parse("list asdijoqwjeoiqjwe");
            fail();
        } catch (Exception e) {
            assertEquals("list should not have any other arguments "
                    + "(whitespace acceptable)", e.getMessage());
        }

        try {
            Parser.parse("bye asdijoqwjeoiqjwe");
            fail();
        } catch (Exception e) {
            assertEquals("bye should not have any other arguments "
                    + "(whitespace acceptable)", e.getMessage());
        }

        try {
            Parser.parse("choices asdijoqwjeoiqjwe");
            fail();
        } catch (Exception e) {
            assertEquals("choices should not have any other arguments "
                    + "(whitespace acceptable)", e.getMessage());
        }
    }

    @Test
    void testTaskInputFail() throws DukeException {

        try {
            Parser.parse("todo");
            fail();
        } catch (Exception e) {
            assertEquals("☹ OOPS!!! The description of a todo cannot be empty.", e.getMessage());
        }

        try {
            Parser.parse("event");
            fail();
        } catch (Exception e) {
            assertEquals("☹ OOPS!!! The description of a event cannot be empty.", e.getMessage());
        }

        try {
            Parser.parse("deadline");
            fail();
        } catch (Exception e) {
            assertEquals("☹ OOPS!!! The description of a deadline cannot be empty.", e.getMessage());
        }

        try {
            Parser.parse("schedule");
            fail();
        } catch (Exception e) {
            assertEquals("☹ OOPS!!! The description of a schedule cannot be empty.", e.getMessage());
        }

        try {
            Parser.parse("snooze");
            fail();
        } catch (Exception e) {
            assertEquals("☹ OOPS!!! The description of a snooze cannot be empty.", e.getMessage());
        }
    }

    @Test
    void testOtherInputs() throws DukeException {
        try {
            Parser.parse("help asdiqwoiejqwe");
            fail();
        } catch (Exception e) {
            assertEquals("I do not understand that command. "
                    + "Type \"help\" for a full list of available commands", e.getMessage());
        }
    }

    @Test
    void testBadCommand() throws DukeException {
        try {
            Command c = Parser.parse("asdiqwoiejqwe");
            c.execute(testTaskList, testUi, testStorage, testList, this.degreesManager);
            fail();
        } catch (Exception e) {
            assertEquals("☹ OOPS!!! I'm sorry, but I don't know what that means :-(", e.getMessage());
        }
    }

    @Test
    void testAddCommand() throws DukeException {
        Command c = Parser.parse("todo JUnit Testing");
        c.execute(testTaskList, testUi, testStorage, testList, this.degreesManager);
        assertEquals("[T][N] JUnit Testing", testTaskList.get(4).toList());

        Command d = Parser.parse("event JUnit Testing /at 01-01-1970 2200");
        d.execute(testTaskList, testUi, testStorage, testList, this.degreesManager);
        assertEquals("[E][N] JUnit Testing (At: 01-01-1970 2200)", testTaskList.get(5).toList());

        d.unExecute(testTaskList, testUi, testStorage, testList, this.degreesManager);
        try {
            assertEquals("[E][N] JUnit Testing (At: 01-01-1970 2200)", testTaskList.get(5).toList());
            fail();
        } catch (Exception e) {
            assertEquals("Requested Task not found within list", e.getMessage());
        }

        c.unExecute(testTaskList, testUi, testStorage, testList, this.degreesManager);
        try {
            assertEquals("[T][N] JUnit Testing", testTaskList.get(4).toList());
            fail();
        } catch (Exception e) {
            assertEquals("Requested Task not found within list", e.getMessage());
        }
    }

    @Test
    void testModCommand() throws DukeException {
        Command c = Parser.parse("done 3");
        c.execute(testTaskList, testUi, testStorage, testList, this.degreesManager);

        Command d = Parser.parse("done 1");
        d.execute(testTaskList, testUi, testStorage, testList, this.degreesManager);
        assertEquals("Nice! I've marked this task as done:\n"
                + "  [A][Y] Send less help (After: Sending Enough)\r\n"
                + "Nice! I've marked this task as done:\n"
                + "  [T][Y] Send even more Help\r\n", systemOutput.toString());

        d.unExecute(testTaskList, testUi, testStorage, testList, this.degreesManager);
        assertEquals("[T][N] Send even more Help", testTaskList.get(0).toList());

        c.unExecute(testTaskList, testUi, testStorage, testList, this.degreesManager);
        assertEquals("[A][N] Send less help (After: Sending Enough)", testTaskList.get(2).toList());

        try {
            assertEquals("[T][N] JUnit Testing", testTaskList.get(4).toList());
            fail();
        } catch (Exception e) {
            assertEquals("Requested Task not found within list", e.getMessage());
        }
    }

    @Test
    void testAnotherModCommand() throws DukeException {
        Command c = Parser.parse("delete 3");
        c.execute(testTaskList, testUi, testStorage, testList, this.degreesManager);
        assertEquals("Noted. I've removed this task:\n"
                + "  [A][N] Send less help (After: Sending Enough)\r\n"
                + "Now you have 3 tasks in the list.\r\n", systemOutput.toString());
    }

    @Test
    void testFindCommand() throws DukeException {
        Command c = Parser.parse("find Sleep");
        c.execute(testTaskList, testUi, testStorage, testList, this.degreesManager);
        assertEquals("Here are the matching tasks in your list:\r\n"
                + "4. [E][N] Sleeping (At: 01-01-1970 2200)\r\n", systemOutput.toString());
    }

    @Test
    void testScheduleCommand() throws DukeException {
        Command c = Parser.parse("schedule 01-01-1970");
        c.execute(testTaskList, testUi, testStorage, testList, this.degreesManager);
        assertEquals("Here's what the day looks like:\r\n"
                + "4. [E][N] Sleeping (At: 01-01-1970 2200)\r\n", systemOutput.toString());
    }

    @Test
    void testSortCommand() throws DukeException {
        Command c = Parser.parse("sort priority");
        c.execute(testTaskList, testUi, testStorage, testList, this.degreesManager);
        assertEquals("Done! Your tasks have been sorted by priority; the most important one is at the top:\n\r"
                + "\n"
                + "1. [E][N] Sleeping (At: 01-01-1970 2200)\r\n"
                + "2. [T][N] Send even more Help\r\n"
                + "3. [R][N] Deliver Help (Every: Day)\r\n"
                + "4. [A][N] Send less help (After: Sending Enough)\r\n", systemOutput.toString());

        c.unExecute(testTaskList, testUi, testStorage, testList, this.degreesManager);

        systemOutput.reset();
        testTaskList.print();
        assertEquals("1. [T][N] Send even more Help\r\n"
                + "2. [R][N] Deliver Help (Every: Day)\r\n"
                + "3. [A][N] Send less help (After: Sending Enough)\r\n"
                + "4. [E][N] Sleeping (At: 01-01-1970 2200)\r\n", systemOutput.toString());
    }
}