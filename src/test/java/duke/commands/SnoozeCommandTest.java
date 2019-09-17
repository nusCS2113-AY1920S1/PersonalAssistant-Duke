package duke.commands;

import duke.commons.DukeException;
import duke.parsers.ParserStorageUtil;
import duke.storage.Storage;
import duke.tasks.Deadline;
import duke.tasks.Task;
import duke.tasks.TaskWithDates;
import duke.tasks.UniqueTaskList;
import duke.ui.Ui;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SnoozeCommandTest {
    @Test
    void execute() throws DukeException, IOException {
        Ui ui = new Ui();
        LocalDateTime date = LocalDateTime.of(2019,9,9,9,9);
        LocalDateTime date1 = LocalDateTime.of(2018,9,9,9,9);
        final String message = "Alright! I've snoozed this task:\n  ";
        String filePath = "data/tasks.txt";
        Storage storage = new Storage(filePath, ui);

        UniqueTaskList tasks = storage.getTasks();

        TaskWithDates task = new Deadline("homework1", date1);
        task.updateDate(date);
        tasks.add(task);
        FileWriter writer = new FileWriter(filePath);
        for (Task task1 : tasks) {
            writer.write(ParserStorageUtil.toStorageString(task1) + "\n");
        }
        writer.close();

        storage.read();

        SnoozeCommand snoozeCommand = new SnoozeCommand(0, date);
        snoozeCommand.execute(ui, storage);

        assertEquals(ui.getResponse(),message + task);
    }
}
