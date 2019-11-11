package logic.parser.edit;

import common.DukeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

//@@author JasonChanWQ

public class EditTaskNameParserTest {

    @Test
    public void editTaskNameParser_emptyInput_throwsException() {
        assertThrows(DukeException.class, () ->
                EditTaskNameParser.parseEditTaskName(""));
    }

    @Test
    public void editTaskNameParser_emptyTaskIndex_throwsException() {
        assertThrows(DukeException.class, () ->
                EditTaskNameParser.parseEditTaskName(" /to Create 2 event posters"));
    }

    @Test
    public void editTaskNameParser_emptyNewName_throwsException() {
        assertThrows(DukeException.class, () ->
                EditTaskNameParser.parseEditTaskName("1 /to "));

    }

    @Test
    public void editTaskNameParser_invalidTaskIndex_throwsException() {
        assertThrows(DukeException.class, () ->
                EditTaskNameParser.parseEditTaskName("a /to Create 2 event posters"));

    }

    @Test
    public void editTaskNameParser_toNotFound_throwsException() {
        assertThrows(DukeException.class, () ->
                EditTaskDateTimeParser.parseEditTaskDateTime("1 Create 2 event posters"));
    }
}
