package duke.commands;

import duke.StorageStub;
import duke.commons.exceptions.DukeException;
import duke.storage.Storage;
import duke.data.tasks.Deadline;
import duke.data.tasks.TaskWithDates;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RescheduleCommandTest {

    @Test
    void execute() throws DukeException {
        Storage storage = new StorageStub();
        LocalDateTime dateTime = LocalDateTime.of(2019, 9, 9, 9, 9);
        TaskWithDates t = new Deadline("Visit Rome", dateTime);
        storage.getTasks().add(t);

        LocalDateTime newDateTime = LocalDateTime.of(2018, 8, 8, 8, 8);
        RescheduleCommand rescheduleCommand = new RescheduleCommand(0, newDateTime);
        rescheduleCommand.execute(storage);

        assertEquals(t.getStartDate(), newDateTime);
    }
}
