package parser;

import command.Command;
import exception.DukeException;
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
    private Storage testStorage = new Storage("dummy.txt");

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

    ParserTest() throws DukeException {
    }

    @Test
    void testSingleInput() throws DukeException {

        try {
            Parser.parse("");
            fail();
        } catch (Exception e) {
            assertEquals("Empty Command!", e.getMessage());
        }

        try {
            Parser.parse("list asdijoqwjeoiqjwe");
            fail();
        } catch (Exception e) {
            assertEquals("List should not have any other arguments "
                    + "(whitespace acceptable)", e.getMessage());
        }

        try {
            Parser.parse("bye asdijoqwjeoiqjwe");
            fail();
        } catch (Exception e) {
            assertEquals("List should not have any other arguments "
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
    void testAddCommand() throws DukeException {
        Command c = Parser.parse("todo JUnit Testing");
        c.execute(testTaskList, testUi, testStorage);
        assertEquals("[T][N] JUnit Testing", testTaskList.get(4).toList());

        Command d = Parser.parse("event JUnit Testing /at 01-01-1970 2200");
        d.execute(testTaskList, testUi, testStorage);
        assertEquals("[E][N] JUnit Testing (At: 01-01-1970 2200)", testTaskList.get(5).toList());
    }

    @Test
    void testModCommand() throws DukeException {
        Command c = Parser.parse("done 3");
        c.execute(testTaskList, testUi, testStorage);

        Command d = Parser.parse("done 1");
        d.execute(testTaskList, testUi, testStorage);
        assertEquals("Nice! I've marked this task as done:\n"
                + "  [A][Y] Send less help (After: Sending Enough)\r\n"
                + "Nice! I've marked this task as done:\n"
                + "  [T][Y] Send even more Help\r\n", systemOutput.toString());
    }

    @Test
    void testAnotherModCommand() throws DukeException {
        Command c = Parser.parse("delete 3");
        c.execute(testTaskList, testUi, testStorage);
        assertEquals("Noted. I've removed this task:\n"
                + "  [A][N] Send less help (After: Sending Enough)\r\n"
                + "Now you have 3 tasks in the list.\r\n", systemOutput.toString());
    }

    @Test
    void testFindCommand() throws DukeException {
        Command c = Parser.parse("find Sleep");
        c.execute(testTaskList, testUi, testStorage);
        assertEquals("Here are the matching tasks in your list:\r\n"
                + "4. [E][N] Sleeping (At: 01-01-1970 2200)\r\n", systemOutput.toString());
    }

    @Test
    void testScheduleCommand() throws DukeException {
        Command c = Parser.parse("schedule 01-01-1970");
        c.execute(testTaskList, testUi, testStorage);
        assertEquals("Here's what the day looks like:\r\n"
                + "4. [E][N] Sleeping (At: 01-01-1970 2200)\r\n", systemOutput.toString());
    }
}