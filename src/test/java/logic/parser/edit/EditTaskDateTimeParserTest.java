package logic.parser.edit;

import common.DukeException;
import logic.command.AddTaskCommand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class EditTaskDateTimeParserTest {

    @Test
    public void editTaskDateTimeParser_emptyInput_throwsException() {
        new AddTaskCommand("Create event poster");
        assertThrows(DukeException.class, () ->
                EditTaskDateTimeParser.parseEditTaskDateTime(""));
    }

    @Test
    public void editTaskDateTimeParser_invalidIndex_throwsException() {
        new AddTaskCommand("Create event poster");
        assertThrows(DukeException.class, () ->
                EditTaskDateTimeParser.parseEditTaskDateTime("a /to 12/12/2020 0000"));
    }

    @Test
    public void editTaskDateTimeParser_emptyIndex_throwsException() {
        new AddTaskCommand("Create event poster");
        assertThrows(DukeException.class, () ->
                EditTaskDateTimeParser.parseEditTaskDateTime(" /to 12/12/2020 0000"));
    }

    @Test
    public void editTaskDateTimeParser_invalidDateTime_throwsException() {
        new AddTaskCommand("Create event poster");
        assertThrows(DukeException.class, () ->
                EditTaskDateTimeParser.parseEditTaskDateTime("1 /to 12/a/2020 0000"));
    }

    @Test
    public void editTaskDateTimeParser_toNotFound_throwsException() {
        new AddTaskCommand("Create event poster");
        assertThrows(DukeException.class, () ->
                EditTaskDateTimeParser.parseEditTaskDateTime("1 12/12/2020 0000"));
    }
}
