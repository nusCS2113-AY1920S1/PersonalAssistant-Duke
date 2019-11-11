package chronologer.parser;

import chronologer.command.Command;
import chronologer.command.PriorityCommand;
import chronologer.exception.ChronologerException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

/**
 * Test class for priority parser.
 *
 * @author Tan Yi Xiang
 * @version V1.0
 */
class PriorityParserTest {

    private Field[] getPriorityCommandFields(Command command) throws NoSuchFieldException {
        Field[] commandFields = new Field[2];
        commandFields[0] = command.getClass().getDeclaredField("indexOfTask");
        commandFields[1] = command.getClass().getDeclaredField("priorityString");
        commandFields[0].setAccessible(true);
        commandFields[1].setAccessible(true);
        return commandFields;
    }

    private void assertEqualsPriorityCommand(Field[] test, Field[] expected, Command testCommand,
                                            Command expectedCommand) throws IllegalAccessException {
        Assertions.assertEquals(test[0].get(testCommand), expected[0].get(expectedCommand));
        Assertions.assertEquals(test[1].get(testCommand), expected[1].get(expectedCommand));
    }

    @Test
    void testPriorityParser() throws ChronologerException, NoSuchFieldException, IllegalAccessException {
        PriorityParser priorityParser = new PriorityParser("priority 1 high", "priority");
        Command testCommand = priorityParser.parse();
        Command expectedCommand = new PriorityCommand(0, "high");

        Field[] priorityTestFields = getPriorityCommandFields(testCommand);
        Field[] priorityFields = getPriorityCommandFields(expectedCommand);
        Assertions.assertEquals(0, priorityParser.indexOfTask);
        assertEqualsPriorityCommand(priorityTestFields, priorityFields, testCommand, expectedCommand);
    }

    @Test
    void testEmptyIndex() {
        Assertions.assertThrows(ChronologerException.class, () -> {
            ParserFactory.parse("priority");
        });
    }

    @Test
    void testNegativeIndex() {
        Assertions.assertThrows(ChronologerException.class, () -> {
            ParserFactory.parse(("priority -1 low"));
        });
    }


}
