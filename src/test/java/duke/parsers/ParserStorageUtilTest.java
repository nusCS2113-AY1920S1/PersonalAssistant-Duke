package duke.parsers;

import duke.UiStub;
import duke.commands.AddCommand;
import duke.commons.DukeDateTimeParseException;
import duke.commons.DukeException;
import duke.storage.Storage;
import duke.tasks.Deadline;
import duke.tasks.Task;
import duke.tasks.Todo;
import javafx.scene.layout.VBox;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class ParserStorageUtilTest {

    @Test
    void createTaskFromDate() throws DukeException {
        LocalDateTime startDate = LocalDateTime.of(2018, 8, 8, 8, 8);
        Deadline d = new Deadline("Visit Rome", startDate);
        Deadline t = (Deadline) ParserStorageUtil.createTaskFromStorage(ParserStorageUtil.toStorageString(d));
        assertFalse(t.isDone());
        assertEquals(t.getDescription(), "Visit Rome");
        assertEquals(t.getDeadline(), startDate.toString());
    }
}
