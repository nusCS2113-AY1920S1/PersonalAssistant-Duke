package seedu.duke;

import javafx.util.Pair;
import org.junit.jupiter.api.Test;
import seedu.duke.common.command.Command;
import seedu.duke.common.command.InvalidCommand;
import seedu.duke.task.command.TaskDeleteCommand;
import seedu.duke.task.command.TaskDoAfterCommand;
import seedu.duke.task.command.TaskDoneCommand;
import seedu.duke.task.command.TaskFindCommand;
import seedu.duke.task.command.TaskReminderCommand;
import seedu.duke.task.command.TaskSnoozeCommand;
import seedu.duke.task.entity.TaskList;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class CommandParserTest {
    @Test
    public void isCommandFormatTest() {
        assertTrue(CommandParser.isCommandFormat("deadline 123abc -by asdas"));
        assertTrue(CommandParser.isCommandFormat("deadline 123abc -by asdas -asd nisnds"));
        assertTrue(CommandParser.isCommandFormat("deadline 123abc 123abc -by asdas -asd nisnds"));
        assertFalse(CommandParser.isCommandFormat("deadline -by asdas -asd nisnds"));
        assertFalse(CommandParser.isCommandFormat("deadline 123abc -by asdas -asd nis nds"));
        assertFalse(CommandParser.isCommandFormat("123abc -by asdas"));
        assertFalse(CommandParser.isCommandFormat("deadline 123abc -by "));
        assertTrue(CommandParser.isCommandFormat("deadline 123abc"));
        assertTrue(CommandParser.isCommandFormat(" deadline 123abc -by asdas"));
        assertTrue(CommandParser.isCommandFormat("ads deadline 123abc -by asdas"));
        assertTrue(CommandParser.isCommandFormat("done 1"));
    }

    @Test
    public void parseOptionsTest() {
        assertEquals(2, CommandParser.parseOptions("todo 123abc -tag sad -remarks pick").size());
        assertEquals("remarks", CommandParser.parseOptions("todo 123abc -tag sad -remarks pick").get(0).getKey());
        assertEquals("pick", CommandParser.parseOptions("todo 123abc -tag sad -remarks pick").get(0).getValue());
        assertEquals("tag", CommandParser.parseOptions("todo 123abc -tag sad -remarks pick").get(1).getKey());
        assertEquals("sad", CommandParser.parseOptions("todo 123abc -tag sad -remarks pick").get(1).getValue());
        assertEquals(0, CommandParser.parseOptions("todo 123abc").size());
    }

    @Test
    public void stripOptionsTest() {
        assertEquals("todo 123abc", CommandParser.stripOptions("todo 123abc -tag sad -remarks pick"));
        assertEquals("todo 123abc", CommandParser.stripOptions("todo 123abc -tag sad"));
        assertEquals("todo 123abc", CommandParser.stripOptions("todo 123abc"));
    }

    @Test
    public void parseDoneCommandTest() {
        try {
            Class<?> parser = Class.forName("seedu.duke.CommandParser");
            Method method =
                    parser.getDeclaredMethod("parseDoneCommand", String.class, ArrayList.class);
            method.setAccessible(true);
            assertTrue(method.invoke(null, "done 1", null) instanceof TaskDoneCommand);
            assertTrue(method.invoke(null, "done 1", null) instanceof TaskDoneCommand);
            assertTrue(method.invoke(null, "done 1  ", null) instanceof TaskDoneCommand);
            assertTrue(method.invoke(null, "done ", null) instanceof InvalidCommand);
            assertTrue(method.invoke(null, "done 1  a", null) instanceof InvalidCommand);
            assertTrue(method.invoke(null, "done 1a", null) instanceof InvalidCommand);
        } catch (ClassNotFoundException e) {
            fail("No such class");
        } catch (NoSuchMethodException e) {
            fail("No such method");
        } catch (InvocationTargetException e) {
            fail(e.getMessage());
        } catch (IllegalAccessException e) {
            fail("No Access");
        }
    }

    @Test
    public void parseDeleteCommandTest() {
        try {
            Class<?> parser = Class.forName("seedu.duke.CommandParser");
            Method method = parser.getDeclaredMethod("parseDeleteCommand", String.class, TaskList.class,
                    ArrayList.class);
            method.setAccessible(true);
            assertTrue(method.invoke(null, "delete 1", null, null) instanceof TaskDeleteCommand);
            assertTrue(method.invoke(null, "delete 1", null, null) instanceof TaskDeleteCommand);
            assertTrue(method.invoke(null, "delete 1  ", null, null) instanceof TaskDeleteCommand);
            assertTrue(method.invoke(null, "delete ", null, null) instanceof InvalidCommand);
            assertTrue(method.invoke(null, "delete 1  a", null, null) instanceof InvalidCommand);
            assertTrue(method.invoke(null, "delete 1a", null, null) instanceof InvalidCommand);
        } catch (ClassNotFoundException e) {
            fail("No such class");
        } catch (NoSuchMethodException e) {
            fail("No such method");
        } catch (InvocationTargetException e) {
            fail(e.getMessage());
        } catch (IllegalAccessException e) {
            fail("No Access");
        }
    }

    @Test
    public void parseFindCommandTest() {
        try {
            Class<?> parser = Class.forName("seedu.duke.CommandParser");
            Method method = parser.getDeclaredMethod("parseFindCommand", String.class, TaskList.class,
                    ArrayList.class);
            method.setAccessible(true);
            assertTrue(method.invoke(null, "find 1", null, null) instanceof TaskFindCommand);
            assertTrue(method.invoke(null, "find 1 a", null, null) instanceof TaskFindCommand);
            assertTrue(method.invoke(null, "find   ", null, null) instanceof InvalidCommand);
        } catch (ClassNotFoundException e) {
            fail("No such class");
        } catch (NoSuchMethodException e) {
            fail("No such method");
        } catch (InvocationTargetException e) {
            fail(e.getMessage());
        } catch (IllegalAccessException e) {
            fail("No Access");
        }
    }

    @Test
    public void parseReminderCommandTest() {
        try {
            Class<?> parser = Class.forName("seedu.duke.CommandParser");
            Method method = parser.getDeclaredMethod("parseReminderCommand", String.class, TaskList.class,
                    ArrayList.class);
            method.setAccessible(true);
            assertTrue(method.invoke(null, "reminder 1", null, null) instanceof TaskReminderCommand);
            assertTrue(method.invoke(null, "reminder 1 a", null, null) instanceof InvalidCommand);
            assertTrue(method.invoke(null, "reminder -1", null, null) instanceof InvalidCommand);
            assertTrue(method.invoke(null, "reminder 00 ", null, null) instanceof TaskReminderCommand);
            assertTrue(method.invoke(null, "reminder abc ", null, null) instanceof InvalidCommand);
            assertTrue(method.invoke(null, "reminder 123 abc ", null, null) instanceof InvalidCommand);
        } catch (ClassNotFoundException e) {
            fail("No such class");
        } catch (NoSuchMethodException e) {
            fail("No such method");
        } catch (InvocationTargetException e) {
            fail(e.getMessage());
        } catch (IllegalAccessException e) {
            fail("No Access");
        }
    }

    @Test
    public void parseDoAfterCommandTest() {
        try {
            Class<?> parser = Class.forName("seedu.duke.CommandParser");
            Method method = parser.getDeclaredMethod("parseDoAfterCommand", String.class, TaskList.class, ArrayList.class);
            method.setAccessible(true);

            ArrayList<Command.Option> optionListEmpty = new ArrayList<>();
            ArrayList<Command.Option> optionListCorrect = new ArrayList<>(Arrays.asList(new Command.Option(
                    "msg","do after description")));
            ArrayList<Command.Option> optionListExtra = new ArrayList<>(Arrays.asList(new Command.Option(
                    "msg", "do after description"), new Command.Option("tag", "123")));
            method.invoke(null, "doAfter 1", null, optionListCorrect);

            assertTrue(method.invoke(null, "doAfter 1", null, optionListCorrect) instanceof TaskDoAfterCommand);
            assertTrue(method.invoke(null, "doafter 1", null, optionListCorrect) instanceof TaskDoAfterCommand);
            assertTrue(method.invoke(null, "doafter ", null, optionListCorrect) instanceof InvalidCommand);
            assertTrue(method.invoke(null, "doafter", null, optionListCorrect) instanceof InvalidCommand);
            assertTrue(method.invoke(null, "doafter 123abc", null, optionListCorrect) instanceof InvalidCommand);
            assertTrue(method.invoke(null, "doafter 1 23", null, optionListCorrect) instanceof InvalidCommand);
            assertTrue(method.invoke(null, "doafter 1", null, optionListEmpty) instanceof InvalidCommand);
            assertTrue(method.invoke(null, "doafter 1", null, optionListExtra) instanceof TaskDoAfterCommand);
        } catch (ClassNotFoundException e) {
            fail("No such class");
        } catch (NoSuchMethodException e) {
            fail("No such method");
        } catch (InvocationTargetException e) {
            fail(e.getMessage());
        } catch (IllegalAccessException e) {
            fail("No Access");
        }
    }

    @Test
    public void parseSnoozeCommandTest() {
        try {
            Class<?> parser = Class.forName("seedu.duke.CommandParser");
            Method method = parser.getDeclaredMethod("parseSnoozeCommand", String.class, TaskList.class,
                    ArrayList.class);
            method.setAccessible(true);
            assertTrue(method.invoke(null, "snooze 1", null, null) instanceof TaskSnoozeCommand);
            assertTrue(method.invoke(null, "snooze 1", null, null) instanceof TaskSnoozeCommand);
            assertTrue(method.invoke(null, "snooze 1  ", null, null) instanceof TaskSnoozeCommand);
            assertTrue(method.invoke(null, "snooze ", null, null) instanceof InvalidCommand);
            assertTrue(method.invoke(null, "snooze 1  a", null, null) instanceof InvalidCommand);
            assertTrue(method.invoke(null, "snooze 1a", null, null) instanceof InvalidCommand);
        } catch (ClassNotFoundException e) {
            fail("No such class");
        } catch (NoSuchMethodException e) {
            fail("No such method");
        } catch (InvocationTargetException e) {
            fail(e.getMessage());
        } catch (IllegalAccessException e) {
            fail("No Access");
        }
    }
}
