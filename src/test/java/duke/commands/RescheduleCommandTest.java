package duke.commands;

import duke.UiStub;
import duke.commons.exceptions.DukeException;
import duke.storage.Storage;
import duke.data.tasks.Deadline;
import duke.data.tasks.TaskWithDates;
import javafx.scene.layout.VBox;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RescheduleCommandTest {

    @Test
    void execute() throws DukeException {
        UiStub ui = new UiStub(new VBox());
        Storage storage = new Storage("tasks1.txt", ui);
        LocalDateTime dateTime = LocalDateTime.of(2019, 9, 9, 9, 9);
        TaskWithDates t = new Deadline("Visit Rome", dateTime);
        storage.getTasks().add(t);

        LocalDateTime newDateTime = LocalDateTime.of(2018, 8, 8, 8, 8);
        RescheduleCommand rescheduleCommand = new RescheduleCommand(0, newDateTime);
        rescheduleCommand.execute(ui, storage);

        assertEquals(t.getStartDate(), newDateTime);
    }
}
