package seedu.duke;

import org.junit.jupiter.api.Test;
import seedu.duke.common.command.Command;
import seedu.duke.common.command.InvalidCommand;
import seedu.duke.task.command.TaskDeleteCommand;
import seedu.duke.task.command.TaskDoAfterCommand;
import seedu.duke.task.command.TaskDoneCommand;
import seedu.duke.task.command.TaskReminderCommand;
import seedu.duke.task.command.TaskSnoozeCommand;
import seedu.duke.task.command.TaskAddCommand;
import seedu.duke.task.command.TaskFindCommand;
import seedu.duke.task.entity.Task;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class TaskCommandParseHelperTest {
    @Test
    public void isCommandFormatTest() {
        assertTrue(CommandParseHelper.isCommandFormat("task deadline 123abc -by asdas"));
        assertFalse(CommandParseHelper.isCommandFormat("deadline 123abc -by asdas"));
        assertTrue(CommandParseHelper.isCommandFormat("task deadline 123abc -by asdas -asd nisnds"));
        assertTrue(CommandParseHelper.isCommandFormat("task deadline 123abc 123abc -by asdas -asd nisnds"));
        assertTrue(CommandParseHelper.isCommandFormat("task deadline -by asdas -asd nisnds"));
        assertTrue(CommandParseHelper.isCommandFormat("task deadline 123abc -by asdas -asd nis nds"));
        assertTrue(CommandParseHelper.isCommandFormat("email 123abc -by asdas"));
        assertFalse(CommandParseHelper.isCommandFormat("task deadline 123abc -by "));
        assertTrue(CommandParseHelper.isCommandFormat("task deadline 123abc"));
        assertTrue(CommandParseHelper.isCommandFormat("task deadline 123abc -by asdas"));
        assertTrue(CommandParseHelper.isCommandFormat("task ads deadline 123abc -by asdas"));
        assertTrue(CommandParseHelper.isCommandFormat("task done 1"));
        assertTrue(CommandParseHelper.isCommandFormat("task deadline 123 -time 11/11/1111 1111"));
        assertTrue(CommandParseHelper.isCommandFormat("task bye"));
    }

    @Test
    public void parseOptionsTest() {
        assertEquals(2, CommandParseHelper.parseOptions("todo 123abc -tag sad -remarks pick").size());
        assertEquals("remarks", CommandParseHelper.parseOptions("todo 123abc -tag sad -remarks pick").get(0).getKey());
        assertEquals("pick", CommandParseHelper.parseOptions("todo 123abc -tag sad -remarks pick").get(0).getValue());
        assertEquals("tag", CommandParseHelper.parseOptions("todo 123abc -tag sad -remarks pick").get(1).getKey());
        assertEquals("sad", CommandParseHelper.parseOptions("todo 123abc -tag sad -remarks pick").get(1).getValue());
        assertEquals("remarks",
                CommandParseHelper.parseOptions("todo 123abc -tag sad -remarks pick one side").get(0).getKey());
        assertEquals("pick one side",
                CommandParseHelper.parseOptions("todo 123abc -tag sad -remarks pick one side").get(0).getValue());
        assertEquals("11/11/1111 1111", CommandParseHelper.parseOptions("task deadline 123 -time 11/11/1111 "
                + "1111").get(0).getValue());
        assertEquals(0, CommandParseHelper.parseOptions("todo 123abc").size());
    }

    @Test
    public void stripOptionsTest() {
        assertEquals("todo 123abc", CommandParseHelper.stripOptions("todo 123abc -tag sad -remarks pick"));
        assertEquals("todo 123abc", CommandParseHelper.stripOptions("todo 123abc -tag sad -remarks pick one"));
        assertEquals("todo 123abc", CommandParseHelper.stripOptions("todo 123abc -tag sad"));
        assertEquals("todo 123abc", CommandParseHelper.stripOptions("todo 123abc"));
        assertEquals("task deadline 123", CommandParseHelper.stripOptions("task deadline 123 -time 11/11/1111 "
                + "1111"));
    }

    @Test
    public void parseDoneCommandTest() {
        try {
            Class<?> parser = Class.forName("seedu.duke.task.command.TaskCommandParseHelper");
            Method method =
                    parser.getDeclaredMethod("parseDoneCommand", String.class);
            method.setAccessible(true);
            assertTrue(method.invoke(null, "done 1") instanceof TaskDoneCommand);
            assertTrue(method.invoke(null, "done 1") instanceof TaskDoneCommand);
            assertTrue(method.invoke(null, "done 1  ") instanceof TaskDoneCommand);
            assertTrue(method.invoke(null, "done ") instanceof InvalidCommand);
            assertTrue(method.invoke(null, "done 1  a") instanceof InvalidCommand);
            assertTrue(method.invoke(null, "done 1a") instanceof InvalidCommand);
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
            Class<?> parser = Class.forName("seedu.duke.task.command.TaskCommandParseHelper");
            Method method = parser.getDeclaredMethod("parseDeleteCommand", String.class);
            method.setAccessible(true);
            assertTrue(method.invoke(null, "delete 1") instanceof TaskDeleteCommand);
            assertTrue(method.invoke(null, "delete 1  ") instanceof TaskDeleteCommand);
            assertTrue(method.invoke(null, "delete ") instanceof InvalidCommand);
            assertTrue(method.invoke(null, "delete 1  a") instanceof InvalidCommand);
            assertTrue(method.invoke(null, "delete 1a") instanceof InvalidCommand);
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
            Class<?> parser = Class.forName("seedu.duke.task.command.TaskCommandParseHelper");
            Method method = parser.getDeclaredMethod("parseFindCommand", String.class);
            method.setAccessible(true);
            assertTrue(method.invoke(null, "find 1") instanceof TaskFindCommand);
            assertTrue(method.invoke(null, "find 1 a") instanceof TaskFindCommand);
            assertTrue(method.invoke(null, "find   ") instanceof InvalidCommand);
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
            Class<?> parser = Class.forName("seedu.duke.task.command.TaskCommandParseHelper");
            Method method = parser.getDeclaredMethod("parseReminderCommand", String.class);
            method.setAccessible(true);
            assertTrue(method.invoke(null, "reminder 1") instanceof TaskReminderCommand);
            assertTrue(method.invoke(null, "reminder 1000000000000000") instanceof TaskReminderCommand);
            assertTrue(method.invoke(null, "reminder 1 a") instanceof InvalidCommand);
            assertTrue(method.invoke(null, "reminder -1") instanceof InvalidCommand);
            assertTrue(method.invoke(null, "reminder 00 ") instanceof TaskReminderCommand);
            assertTrue(method.invoke(null, "reminder abc ") instanceof InvalidCommand);
            assertTrue(method.invoke(null, "reminder 123 abc ") instanceof InvalidCommand);
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
            Class<?> parser = Class.forName("seedu.duke.task.command.TaskCommandParseHelper");
            Method method = parser.getDeclaredMethod("parseDoAfterCommand", String.class, ArrayList.class);
            method.setAccessible(true);

            ArrayList<Command.Option> optionListCorrect = new ArrayList<>(Arrays.asList(new Command.Option(
                    "msg", "do after description")));
            method.invoke(null, "doAfter 1", optionListCorrect);

            assertTrue(method.invoke(null, "doAfter 1", optionListCorrect) instanceof TaskDoAfterCommand);
            assertTrue(method.invoke(null, "doafter 1", optionListCorrect) instanceof TaskDoAfterCommand);
            assertTrue(method.invoke(null, "doafter ", optionListCorrect) instanceof InvalidCommand);
            assertTrue(method.invoke(null, "doafter", optionListCorrect) instanceof InvalidCommand);
            assertTrue(method.invoke(null, "doafter 123abc", optionListCorrect) instanceof InvalidCommand);
            assertTrue(method.invoke(null, "doafter 1 23", optionListCorrect) instanceof InvalidCommand);

            ArrayList<Command.Option> optionListEmpty = new ArrayList<>();
            assertTrue(method.invoke(null, "doafter 1", optionListEmpty) instanceof InvalidCommand);

            ArrayList<Command.Option> optionListExtra = new ArrayList<>(Arrays.asList(new Command.Option(
                    "msg", "do after description"), new Command.Option("tag", "123")));
            assertTrue(method.invoke(null, "doafter 1", optionListExtra) instanceof TaskDoAfterCommand);
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
            Class<?> parser = Class.forName("seedu.duke.task.command.TaskCommandParseHelper");
            Method method = parser.getDeclaredMethod("parseSnoozeCommand", String.class);
            method.setAccessible(true);
            assertTrue(method.invoke(null, "snooze 1") instanceof TaskSnoozeCommand);
            assertTrue(method.invoke(null, "snooze 1") instanceof TaskSnoozeCommand);
            assertTrue(method.invoke(null, "snooze 1  ") instanceof TaskSnoozeCommand);
            assertTrue(method.invoke(null, "snooze ") instanceof InvalidCommand);
            assertTrue(method.invoke(null, "snooze 1  a") instanceof InvalidCommand);
            assertTrue(method.invoke(null, "snooze 1a") instanceof InvalidCommand);
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
    public void parseAddToDoCommandTest() {
        try {
            Class<?> parser = Class.forName("seedu.duke.task.command.TaskCommandParseHelper");
            Method method = parser.getDeclaredMethod("parseAddToDoCommand", String.class,
                    String.class, ArrayList.class, String.class);
            method.setAccessible(true);

            ArrayList<String> tagList = new ArrayList<>(Arrays.asList("123", "234"));
            String doafter = "345";

            assertTrue(method.invoke(null, "todo 123", null, null, "") instanceof TaskAddCommand);
            assertTrue(method.invoke(null, "todo 123", null, tagList, "") instanceof TaskAddCommand);
            assertTrue(method.invoke(null, "todo 123", doafter, tagList, "") instanceof TaskAddCommand);
            assertTrue(method.invoke(null, "todo 123 234", null, null, "") instanceof TaskAddCommand);
            assertTrue(method.invoke(null, "todo abc 123 /", null, null, "") instanceof InvalidCommand);
            assertTrue(method.invoke(null, "todo ", null, null, "") instanceof InvalidCommand);
            assertTrue(method.invoke(null, "todo", null, null, "") instanceof InvalidCommand);
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
    public void parseAddDeadlineCommandTest() {
        try {
            Class<?> parser = Class.forName("seedu.duke.task.command.TaskCommandParseHelper");
            Method method = parser.getDeclaredMethod("parseAddDeadlineCommand", String.class,
                    LocalDateTime.class, String.class, ArrayList.class, String.class);
            method.setAccessible(true);

            ArrayList<String> tagList = new ArrayList<>(Arrays.asList("123", "234"));
            LocalDateTime time = Task.parseDate("11/12/2019 1220");
            String doafter = "345";

            assertTrue(method.invoke(null, "deadline 123", time, null, null, "") instanceof TaskAddCommand);
            assertTrue(method.invoke(null, "deadline 123", time, null, tagList, "") instanceof TaskAddCommand);
            assertTrue(method.invoke(null, "deadline 123", time, doafter, tagList, "") instanceof TaskAddCommand);
            assertTrue(method.invoke(null, "deadline 123 234", time, null, null, "") instanceof TaskAddCommand);
            assertTrue(method.invoke(null, "deadline abc 123 /", time, null, null, "") instanceof InvalidCommand);
            assertTrue(method.invoke(null, "deadline ", time, null, null, "") instanceof InvalidCommand);
            assertTrue(method.invoke(null, "deadline", time, null, null, "") instanceof InvalidCommand);
            assertTrue(method.invoke(null, "deadline 123", null, null, null, "") instanceof InvalidCommand);
        } catch (ClassNotFoundException e) {
            fail("No such class");
        } catch (NoSuchMethodException e) {
            fail("No such method");
        } catch (InvocationTargetException e) {
            fail(e.getMessage());
        } catch (IllegalAccessException e) {
            fail("No Access");
        } catch (CommandParseHelper.UserInputException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void parseAddEventCommandTest() {
        try {
            Class<?> parser = Class.forName("seedu.duke.task.command.TaskCommandParseHelper");
            Method method = parser.getDeclaredMethod("parseEventCommand", String.class, LocalDateTime.class,
                    String.class, ArrayList.class, String.class);
            method.setAccessible(true);

            ArrayList<String> tagList = new ArrayList<>(Arrays.asList("123", "234"));
            LocalDateTime time = Task.parseDate("11/12/2019 1220");
            String doafter = "345";

            assertTrue(method.invoke(null, "event 123", time, null, null, "") instanceof TaskAddCommand);
            assertTrue(method.invoke(null, "event 123", time, null, tagList, "") instanceof TaskAddCommand);
            assertTrue(method.invoke(null, "event 123", time, doafter, tagList, "") instanceof TaskAddCommand);
            assertTrue(method.invoke(null, "event 123 234", time, null, null, "") instanceof TaskAddCommand);
            assertTrue(method.invoke(null, "event abc 123 /", time, null, null, "") instanceof InvalidCommand);
            assertTrue(method.invoke(null, "event ", time, null, null, "") instanceof InvalidCommand);
            assertTrue(method.invoke(null, "event", time, null, null, "") instanceof InvalidCommand);
            assertTrue(method.invoke(null, "event 123", null, null, null, "") instanceof InvalidCommand);
        } catch (ClassNotFoundException e) {
            fail("No such class");
        } catch (NoSuchMethodException e) {
            fail("No such method");
        } catch (InvocationTargetException | CommandParseHelper.UserInputException e) {
            fail(e.getMessage());
        } catch (IllegalAccessException e) {
            fail("No Access");
        }
    }
}
