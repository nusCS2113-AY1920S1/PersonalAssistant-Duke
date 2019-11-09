package seedu.duke;

import org.junit.jupiter.api.Test;
import seedu.duke.common.command.Command;
import seedu.duke.common.command.InvalidCommand;
import seedu.duke.common.parser.CommandParseHelper;
import seedu.duke.task.TaskList;
import seedu.duke.task.command.TaskAddCommand;
import seedu.duke.task.command.TaskDeleteCommand;
import seedu.duke.task.command.TaskDoAfterCommand;
import seedu.duke.task.command.TaskDoneCommand;
import seedu.duke.task.command.TaskFindCommand;
import seedu.duke.task.command.TaskReminderCommand;
import seedu.duke.task.command.TaskSnoozeCommand;
import seedu.duke.task.command.TaskUpdateCommand;
import seedu.duke.task.entity.Task;
import seedu.duke.task.parser.TaskCommandParseHelper;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class TaskCommandParseHelperTest {
    private void fakeModel() {
        try {
            Method add = List.class.getDeclaredMethod("add", Object.class);
            TaskList newTaskList = new TaskList();
            add.invoke(newTaskList, new Task(""));
            add.invoke(newTaskList, new Task(""));
            add.invoke(newTaskList, new Task(""));
            add.invoke(newTaskList, new Task(""));
            add.invoke(newTaskList, new Task(""));

            Class<?> modelClass = Class.forName("seedu.duke.common.model.Model");
            Object model = modelClass.getMethod("getInstance").invoke(null);
            Field taskList = modelClass.getDeclaredField("taskList");
            taskList.setAccessible(true);
            taskList.set(model, newTaskList);
        } catch (ClassNotFoundException | NoSuchFieldException | NoSuchMethodException
                | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void isCommandFormatTest() {
        //positive cases
        assertTrue(CommandParseHelper.isCommandFormat("task deadline 123abc -by asdas"));
        assertTrue(CommandParseHelper.isCommandFormat("task deadline 123abc -by asdas -asd nisnds"));
        assertTrue(CommandParseHelper.isCommandFormat("task deadline 123abc 123abc -by asdas -asd nisnds"));
        assertTrue(CommandParseHelper.isCommandFormat("task deadline -by asdas -asd nisnds"));
        assertTrue(CommandParseHelper.isCommandFormat("task deadline 123abc -by asdas -asd nis nds"));
        assertTrue(CommandParseHelper.isCommandFormat("email 123abc -by asdas"));
        assertTrue(CommandParseHelper.isCommandFormat("task deadline 123abc"));
        assertTrue(CommandParseHelper.isCommandFormat("task deadline 123abc -by asdas"));
        assertTrue(CommandParseHelper.isCommandFormat("task ads deadline 123abc -by asdas"));
        assertTrue(CommandParseHelper.isCommandFormat("task done 1"));
        assertTrue(CommandParseHelper.isCommandFormat("task deadline 123 -time 11/11/1111 1111"));
        assertTrue(CommandParseHelper.isCommandFormat("task bye"));

        //negative cases
        //not starting with email/task
        assertFalse(CommandParseHelper.isCommandFormat("deadline 123abc -by asdas"));
        //empty option
        assertFalse(CommandParseHelper.isCommandFormat("task deadline 123abc -by "));
        assertFalse(CommandParseHelper.isCommandFormat("task deadline 123abc -by 123 -time"));
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
            Class<?> parser = Class.forName("seedu.duke.task.parser.TaskCommandParseHelper");
            Method method =
                    parser.getDeclaredMethod("parseDoneCommand", String.class);
            method.setAccessible(true);
            //positive cases
            assertTrue(method.invoke(null, "done 1") instanceof TaskDoneCommand);
            assertTrue(method.invoke(null, "done 1") instanceof TaskDoneCommand);
            assertTrue(method.invoke(null, "done 1  ") instanceof TaskDoneCommand);

            //negative cases
            //no index
            assertTrue(method.invoke(null, "done ") instanceof InvalidCommand);
            //random character after index with space
            assertTrue(method.invoke(null, "done 1  a") instanceof InvalidCommand);
            //random character after index
            assertTrue(method.invoke(null, "done 1a") instanceof InvalidCommand);
            //large index
            assertTrue(method.invoke(null, "done 1000000") instanceof InvalidCommand);
            //negative index
            assertTrue(method.invoke(null, "done -1000") instanceof InvalidCommand);
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
            fakeModel();
            Class<?> parser = Class.forName("seedu.duke.task.parser.TaskCommandParseHelper");
            Method method = parser.getDeclaredMethod("parseDeleteCommand", String.class);
            method.setAccessible(true);
            //positive cases
            assertTrue(method.invoke(null, "delete 1") instanceof TaskDeleteCommand);
            assertTrue(method.invoke(null, "delete 1  ") instanceof TaskDeleteCommand);

            //negative cases
            //no index
            assertTrue(method.invoke(null, "delete ") instanceof InvalidCommand);
            //random character after index with space
            assertTrue(method.invoke(null, "delete 1  a") instanceof InvalidCommand);
            //random character after index
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
            Class<?> parser = Class.forName("seedu.duke.task.parser.TaskCommandParseHelper");
            Method method = parser.getDeclaredMethod("parseFindCommand", String.class);
            method.setAccessible(true);
            //positive cases
            assertTrue(method.invoke(null, "find 1") instanceof TaskFindCommand);
            assertTrue(method.invoke(null, "find 1 a") instanceof TaskFindCommand);

            //negative cases
            //no keyword
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
            Class<?> parser = Class.forName("seedu.duke.task.parser.TaskCommandParseHelper");
            Method method = parser.getDeclaredMethod("parseReminderCommand", String.class);
            method.setAccessible(true);
            //positive cases
            assertTrue(method.invoke(null, "reminder 1") instanceof TaskReminderCommand);
            assertTrue(method.invoke(null, "reminder 1000000000000000") instanceof TaskReminderCommand);
            assertTrue(method.invoke(null, "reminder 00 ") instanceof TaskReminderCommand);

            //negative cases
            //random character after day limit with space
            assertTrue(method.invoke(null, "reminder 1 a") instanceof InvalidCommand);
            //negative day limit
            assertTrue(method.invoke(null, "reminder -1") instanceof InvalidCommand);
            //non-integer day limit
            assertTrue(method.invoke(null, "reminder abc ") instanceof InvalidCommand);
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
            Class<?> parser = Class.forName("seedu.duke.task.parser.TaskCommandParseHelper");
            Method method = parser.getDeclaredMethod("parseDoAfterCommand", String.class, ArrayList.class);
            method.setAccessible(true);

            ArrayList<Command.Option> optionListCorrect = new ArrayList<>(Arrays.asList(new Command.Option(
                    "msg", "do after description")));

            ArrayList<Command.Option> optionListExtra = new ArrayList<>(Arrays.asList(new Command.Option(
                    "msg", "do after description"), new Command.Option("tag", "123")));

            //positive cases
            assertTrue(method.invoke(null, "doAfter 1", optionListCorrect) instanceof TaskDoAfterCommand);
            assertTrue(method.invoke(null, "doafter 1", optionListCorrect) instanceof TaskDoAfterCommand);
            assertTrue(method.invoke(null, "doafter 1", optionListExtra) instanceof TaskDoAfterCommand);

            //negative cases
            //no index
            assertTrue(method.invoke(null, "doafter ", optionListCorrect) instanceof InvalidCommand);
            //no index or space
            assertTrue(method.invoke(null, "doafter", optionListCorrect) instanceof InvalidCommand);
            //non-integer index
            assertTrue(method.invoke(null, "doafter 123abc", optionListCorrect) instanceof InvalidCommand);
            //more than 1 integer
            assertTrue(method.invoke(null, "doafter 1 23", optionListCorrect) instanceof InvalidCommand);
            ArrayList<Command.Option> optionListEmpty = new ArrayList<>();
            //no description
            assertTrue(method.invoke(null, "doafter 1", optionListEmpty) instanceof InvalidCommand);
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
            Class<?> parser = Class.forName("seedu.duke.task.parser.TaskCommandParseHelper");
            Method method = parser.getDeclaredMethod("parseSnoozeCommand", String.class, ArrayList.class);
            method.setAccessible(true);
            ArrayList<Command.Option> optionList = new ArrayList<Command.Option>();
            //positive cases
            assertTrue(method.invoke(null, "snooze 1", optionList) instanceof TaskSnoozeCommand);
            assertTrue(method.invoke(null, "snooze 1", optionList) instanceof TaskSnoozeCommand);
            assertTrue(method.invoke(null, "snooze 1  ", optionList) instanceof TaskSnoozeCommand);

            //negative cases
            //no index
            assertTrue(method.invoke(null, "snooze ", optionList) instanceof InvalidCommand);
            //random character after index with space
            assertTrue(method.invoke(null, "snooze 1  a", optionList) instanceof InvalidCommand);
            //random character after index
            assertTrue(method.invoke(null, "snooze 1a", optionList) instanceof InvalidCommand);
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
            Class<?> parser = Class.forName("seedu.duke.task.parser.TaskCommandParseHelper");
            Method method = parser.getDeclaredMethod("parseAddToDoCommand", String.class,
                    String.class, ArrayList.class, String.class);
            method.setAccessible(true);

            ArrayList<String> tagList = new ArrayList<>(Arrays.asList("123", "234"));
            String doafter = "345";

            //positive cases
            assertTrue(method.invoke(null, "todo 123", null, null, "") instanceof TaskAddCommand);
            assertTrue(method.invoke(null, "todo 123", null, tagList, "") instanceof TaskAddCommand);
            assertTrue(method.invoke(null, "todo 123", doafter, tagList, "") instanceof TaskAddCommand);
            assertTrue(method.invoke(null, "todo 123 234", null, null, "") instanceof TaskAddCommand);

            //negative cases
            //invalid character at the end
            assertTrue(method.invoke(null, "todo abc 123 /", null, null, "") instanceof InvalidCommand);
            //no name
            assertTrue(method.invoke(null, "todo ", null, null, "") instanceof InvalidCommand);
            //no name or space
            assertTrue(method.invoke(null, "todo", null, null, "") instanceof InvalidCommand);
        } catch (ClassNotFoundException e) {
            fail("No such class");
        } catch (NoSuchMethodException e) {
            //fail("No such method");
        } catch (InvocationTargetException e) {
            fail(e.getMessage());
        } catch (IllegalAccessException e) {
            fail("No Access");
        }
    }

    @Test
    public void parseAddDeadlineCommandTest() {
        try {
            Class<?> parser = Class.forName("seedu.duke.task.parser.TaskCommandParseHelper");
            Method method = parser.getDeclaredMethod("parseAddDeadlineCommand", String.class,
                    LocalDateTime.class, String.class, ArrayList.class, String.class);
            method.setAccessible(true);

            ArrayList<String> tagList = new ArrayList<>(Arrays.asList("123", "234"));
            LocalDateTime time = Task.parseDate("11/12/2019 1220");
            String doafter = "345";

            //positive cases
            assertTrue(method.invoke(null, "deadline 123", time, null, null, "") instanceof TaskAddCommand);
            assertTrue(method.invoke(null, "deadline 123", time, null, tagList, "") instanceof TaskAddCommand);
            assertTrue(method.invoke(null, "deadline 123", time, doafter, tagList, "") instanceof TaskAddCommand);
            assertTrue(method.invoke(null, "deadline 123 234", time, null, null, "") instanceof TaskAddCommand);

            //negative cases
            //invalid character at the back
            assertTrue(method.invoke(null, "deadline abc 123 /", time, null, null, "") instanceof InvalidCommand);
            //no name
            assertTrue(method.invoke(null, "deadline ", time, null, null, "") instanceof InvalidCommand);
            //no name or space
            assertTrue(method.invoke(null, "deadline", time, null, null, "") instanceof InvalidCommand);
            //no time
            assertTrue(method.invoke(null, "deadline 123", null, null, null, "") instanceof InvalidCommand);
        } catch (ClassNotFoundException e) {
            fail("No such class");
        } catch (NoSuchMethodException e) {
            //fail("No such method");
        } catch (InvocationTargetException e) {
            fail(e.getMessage());
        } catch (IllegalAccessException e) {
            fail("No Access");
        } catch (CommandParseHelper.CommandParseException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void parseAddEventCommandTest() {
        try {
            Class<?> parser = Class.forName("seedu.duke.task.parser.TaskCommandParseHelper");
            Method method = parser.getDeclaredMethod("parseEventCommand", String.class, LocalDateTime.class,
                    String.class, ArrayList.class, String.class);
            method.setAccessible(true);

            ArrayList<String> tagList = new ArrayList<>(Arrays.asList("123", "234"));
            LocalDateTime time = Task.parseDate("11/12/2019 1220");
            String doafter = "345";

            //positive cases
            assertTrue(method.invoke(null, "event 123", time, null, null, "") instanceof TaskAddCommand);
            assertTrue(method.invoke(null, "event 123", time, null, tagList, "") instanceof TaskAddCommand);
            assertTrue(method.invoke(null, "event 123", time, doafter, tagList, "") instanceof TaskAddCommand);
            assertTrue(method.invoke(null, "event 123 234", time, null, null, "") instanceof TaskAddCommand);
            //negative cases
            //invalid character at the end
            assertTrue(method.invoke(null, "event abc 123 /", time, null, null, "") instanceof InvalidCommand);
            //no name
            assertTrue(method.invoke(null, "event ", time, null, null, "") instanceof InvalidCommand);
            //no name or space
            assertTrue(method.invoke(null, "event", time, null, null, "") instanceof InvalidCommand);
            //no time
            assertTrue(method.invoke(null, "event 123", null, null, null, "") instanceof InvalidCommand);
        } catch (ClassNotFoundException e) {
            fail("No such class");
        } catch (NoSuchMethodException e) {
            //fail("No such method");
        } catch (InvocationTargetException | CommandParseHelper.CommandParseException e) {
            fail(e.getMessage());
        } catch (IllegalAccessException e) {
            fail("No Access");
        }
    }

    @Test
    public void checkTimeStringTest() {
        assertEquals("Mon", TaskCommandParseHelper.checkTimeString("Mon 1212").getKey());
        assertEquals("1212", TaskCommandParseHelper.checkTimeString("Mon 1212").getValue());
        assertEquals("1212", TaskCommandParseHelper.checkTimeString("Mon 1212  ").getValue());
        assertEquals("Tue", TaskCommandParseHelper.checkTimeString("Tue").getKey());
        assertEquals(null, TaskCommandParseHelper.checkTimeString("Tue").getValue());
        assertEquals("Tue", TaskCommandParseHelper.checkTimeString("Tue   ").getKey());
        assertEquals("Thu", TaskCommandParseHelper.checkTimeString("thu").getKey());
        assertEquals("2322", TaskCommandParseHelper.checkTimeString("thu 2322").getValue());
        assertEquals("Fri", TaskCommandParseHelper.checkTimeString("Fri    2000").getKey());
        assertEquals("Tue", TaskCommandParseHelper.checkTimeString("Tue tue").getKey());
        assertEquals(null, TaskCommandParseHelper.checkTimeString("1212").getValue());
        assertEquals(null, TaskCommandParseHelper.checkTimeString("").getKey());
        assertEquals(null, TaskCommandParseHelper.checkTimeString("").getValue());
        assertEquals(null, TaskCommandParseHelper.checkTimeString("").getKey());
        assertEquals("1212", TaskCommandParseHelper.checkTimeString("1212 1212").getKey());
        assertEquals("1212", TaskCommandParseHelper.checkTimeString("1212").getKey());
    }

    @Test
    public void parseUpdateCommandTest() {
        try {
            Class<?> parser = Class.forName("seedu.duke.task.parser.TaskCommandParseHelper");
            Method method = parser.getDeclaredMethod("parseUpdateCommand", String.class, ArrayList.class);
            method.setAccessible(true);

            ArrayList<Command.Option> optionListCorrect = new ArrayList<>(Arrays.asList(new Command.Option(
                    "doafter", "do after description")));

            ArrayList<Command.Option> optionListExtra = new ArrayList<>(Arrays.asList(new Command.Option(
                            "priority", " high"), new Command.Option("tag ", "123"),
                    new Command.Option("doafter ", "description"), new Command.Option("time", "Mon"),
                    new Command.Option("tag", "efg ")));

            ArrayList<Command.Option> optionListExtra2 = new ArrayList<>(Arrays.asList(new Command.Option(
                    "doafter", "do after description"), new Command.Option("msg", "something")));

            //positive cases
            assertTrue(method.invoke(null, "update 1", optionListCorrect) instanceof TaskUpdateCommand);
            assertTrue(method.invoke(null, "update 1 ", optionListExtra) instanceof TaskUpdateCommand);
            assertTrue(method.invoke(null, "update  1", optionListExtra) instanceof TaskUpdateCommand);
            assertTrue(method.invoke(null, "update 1", optionListExtra2) instanceof TaskUpdateCommand);

            //negative cases
            //no index
            assertTrue(method.invoke(null, "update ", optionListCorrect) instanceof InvalidCommand);
            //no index or space
            assertTrue(method.invoke(null, "update", optionListCorrect) instanceof InvalidCommand);
            //non-integer index
            assertTrue(method.invoke(null, "update 123abc", optionListCorrect) instanceof InvalidCommand);
            //more than 1 integer
            assertTrue(method.invoke(null, "update 1 23", optionListCorrect) instanceof InvalidCommand);
            ArrayList<Command.Option> optionListEmpty = new ArrayList<>();
            //no description
            assertTrue(method.invoke(null, "update 1", optionListEmpty) instanceof InvalidCommand);
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
