package logic.parser.edit;

import common.DukeException;
import logic.command.AddTaskCommand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class EditTaskNameParserTest {

    @Test
    public void editTaskNameParser_emptyTaskIndex_throwsException() {
        new AddTaskCommand("Create event poster");
        assertThrows(DukeException.class, () ->
                EditTaskNameParser.parseEditTaskName(" /to Create 2 event posters"));

    }

    @Test
    public void editTaskNameParser_emptyNewName_throwsException() {
        new AddTaskCommand("Create event poster");
        assertThrows(DukeException.class, () ->
                EditTaskNameParser.parseEditTaskName("1 /to "));

    }

    @Test
    public void editTaskNameParser_invalidTaskIndex_throwsException() {
        new AddTaskCommand("Create event poster");
        assertThrows(DukeException.class, () ->
                EditTaskNameParser.parseEditTaskName("a /to Create 2 event posters"));

    }

    @Test
    public void editTaskNameParser_toNotFound_throwsException() {
        new AddTaskCommand("Create event poster");
        assertThrows(DukeException.class, () ->
                EditTaskDateTimeParser.parseEditTaskDateTime("1 Create 2 event posters"));
    }
}
