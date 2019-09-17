package duke.commands;

import duke.commons.DukeException;
import duke.storage.Storage;
import duke.tasks.Deadline;
import duke.tasks.TaskWithDates;
import duke.ui.Ui;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SnoozeCommandTest {

    @Test
    void execute() throws DukeException {
        Ui ui = new Ui();
        LocalDateTime date = LocalDateTime.of(2019,9,9,9,9);
        LocalDateTime date1 = LocalDateTime.of(2018,9,9,9,9);
        Storage storage = new Storage("data/tasks.txt", ui);
        TaskWithDates task = new Deadline("homework1" , date1);
        SnoozeCommand snoozeCommand = new SnoozeCommand(2, date);
        snoozeCommand.execute(ui, storage);
        assertEquals(task.getStartDate(), date);
    }
}
