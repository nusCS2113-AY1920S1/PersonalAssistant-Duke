package parser;

import command.*;
import exception.DukeException;
import org.junit.jupiter.api.Test;
import storage.Storage;
import task.TaskList;
import ui.UI;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    private static UI testUi = new UI();
    private static Storage testStorage = new Storage("dummy.txt");
    //private TaskList testTasklist = new TaskList(Storage.load());

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
}