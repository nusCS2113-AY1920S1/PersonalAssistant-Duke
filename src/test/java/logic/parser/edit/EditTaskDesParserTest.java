package logic.parser.edit;

import common.DukeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class EditTaskDesParserTest {

    @Test
    public void editTask_index_empty() {
        assertThrows(DukeException.class, () ->
                EditTaskDesParser.parseEditTaskDes("/to des"));
    }

    @Test
    public void editTask_content_empty() {
        assertThrows(DukeException.class, () ->
                EditTaskDesParser.parseEditTaskDes("1 /to"));
    }
}
