package seedu.duke;

import org.junit.jupiter.api.Test;
import seedu.duke.command.DoneCommand;
import seedu.duke.command.InvalidCommand;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {
    @Test
    public void isCommandFormatTest() {
        assertTrue(Parser.isCommandFormat("deadline 123abc -by asdas"));
        assertTrue(Parser.isCommandFormat("deadline 123abc -by asdas -asd nisnds"));
        assertFalse(Parser.isCommandFormat("deadline -by asdas -asd nisnds"));
        assertFalse(Parser.isCommandFormat("deadline 123abc -by asdas -asd nis nds"));
        assertFalse(Parser.isCommandFormat("123abc -by asdas"));
        assertFalse(Parser.isCommandFormat("deadline 123abc -by "));
        assertTrue(Parser.isCommandFormat("deadline 123abc"));
        assertTrue(Parser.isCommandFormat(" deadline 123abc -by asdas"));
        assertFalse(Parser.isCommandFormat("ads deadline 123abc -by asdas"));
        assertTrue(Parser.isCommandFormat("done 1"));
    }

    @Test
    public void parseDoneCommandTest() {
        try {
            Class<?> parser = Class.forName("seedu.duke.Parser");
            Method method = parser.getDeclaredMethod("parseDoneCommand", String.class);
            method.setAccessible(true);
            assertEquals(true, method.invoke(null, "done 1") instanceof DoneCommand);
            assertEquals(true, method.invoke(null, "done 1") instanceof DoneCommand);
            assertEquals(true, method.invoke(null, "done 1  ") instanceof DoneCommand);
            assertEquals(true, method.invoke(null, "done ") instanceof InvalidCommand);
            assertEquals(true, method.invoke(null, "done 1  a") instanceof InvalidCommand);
            assertEquals(true, method.invoke(null, "done 1a") instanceof InvalidCommand);
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
    public void parseOptionsTest() {
        String input = "todo 123abc -tag sad -remarks pick";
        Pattern optionPattern = Pattern.compile("(?<key>-[\\w]+)\\s+(?<value>[\\w]+)\\s*$");
        Matcher optionMatcher = optionPattern.matcher(input);
        assertEquals("todo 123abc -tag sad ", optionMatcher.replaceFirst(""));

        assertEquals(2, Parser.parseOptions("todo 123abc -tag sad -remarks pick").size());
        assertEquals("remarks", Parser.parseOptions("todo 123abc -tag sad -remarks pick").get(0).getKey());
        assertEquals("pick", Parser.parseOptions("todo 123abc -tag sad -remarks pick").get(0).getValue());
        assertEquals("tag", Parser.parseOptions("todo 123abc -tag sad -remarks pick").get(1).getKey());
        assertEquals("sad", Parser.parseOptions("todo 123abc -tag sad -remarks pick").get(1).getValue());
        assertEquals(0, Parser.parseOptions("todo 123abc").size());
    }

    @Test
    public void stripOptionsTest() {
        assertEquals("todo 123abc", Parser.stripOptions("todo 123abc -tag sad -remarks pick"));
        assertEquals("todo 123abc", Parser.stripOptions("todo 123abc -tag sad"));
        assertEquals("todo 123abc", Parser.stripOptions("todo 123abc"));
    }
}
