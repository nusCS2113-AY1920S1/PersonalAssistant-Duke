package duke.logic.commands;

import duke.ModelStub;
import duke.commons.exceptions.DukeException;
import duke.model.events.Deadline;
import duke.model.events.TaskWithDates;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RescheduleCommandTest {

    @Test
    void execute() throws DukeException {
        ModelStub model = new ModelStub();
        LocalDateTime dateTime = LocalDateTime.of(2019, 9, 9, 9, 9);
        TaskWithDates t = new Deadline("Visit Rome", dateTime);
        model.getTasks().add(t);

        LocalDateTime newDateTime = LocalDateTime.of(2018, 8, 8, 8, 8);
        RescheduleCommand rescheduleCommand = new RescheduleCommand(0, newDateTime);
        rescheduleCommand.execute(model);

        assertEquals(t.getStartDate(), newDateTime);
    }
}
