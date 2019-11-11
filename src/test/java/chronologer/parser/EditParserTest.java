package chronologer.parser;

import chronologer.command.Command;
import chronologer.command.EditCommand;
import chronologer.exception.ChronologerException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

/**
 * Test class for edit parser.
 */
class EditParserTest {

    private Field[] getEditCommandFields(Command command) throws NoSuchFieldException {
        Field[] commandFields = new Field[2];
        commandFields[0] = command.getClass().getDeclaredField("indexOfTask");
        commandFields[1] = command.getClass().getDeclaredField("newDescription");
        commandFields[0].setAccessible(true);
        commandFields[1].setAccessible(true);
        return commandFields;
    }

    private void assertEqualsTaskScheduleCommand(Field[] test, Field[] expected, Command testCommand,
                                                 Command expectedCommand) throws IllegalAccessException {
        Assertions.assertEquals(test[0].get(testCommand), expected[0].get(expectedCommand));
        Assertions.assertEquals(test[1].get(testCommand), expected[1].get(expectedCommand));
    }

    @Test
    void testEditParser() throws ChronologerException, NoSuchFieldException, IllegalAccessException {
        EditParser editParser = new EditParser("edit 2 test something","edit");
        Command testEditCommand = editParser.parse();
        Command expectedEditCommand = new EditCommand(1,"test something");

        Field[] editTestFields = getEditCommandFields(testEditCommand);
        Field[] editFields = getEditCommandFields(expectedEditCommand);
        Assertions.assertEquals(1, editParser.indexOfTask);
        assertEqualsTaskScheduleCommand(editTestFields,editFields,testEditCommand,expectedEditCommand);
    }

    @Test
    void testEmptyDescription(){
        Assertions.assertThrows(ChronologerException.class, () -> {
            new EditParser("edit 1", "edit").parse();
        });
    }

    @Test
    void testEmptyIndex(){
        Assertions.assertThrows(ChronologerException.class, () -> {
            new EditParser("edit", "edit").parse();
        });
    }


}
