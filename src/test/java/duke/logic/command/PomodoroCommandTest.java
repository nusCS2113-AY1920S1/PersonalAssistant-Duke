package duke.logic.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.tasklist.TaskList;
import duke.ui.Ui;

class PomodoroCommandTest {
    private static final String FILE_PATH = "data/editCommandTest.json";

    private static final Ui ui = new Ui();
    private static final Storage storage = new Storage(FILE_PATH);
    private static final TaskList tasks = new TaskList();

    @Test
    void execute_wrongPomodoroCommand_failure() {
        PomodoroCommand pomodoroCommand = new PomodoroCommand("starts");
        Exception exception = assertThrows(DukeException.class, () ->
                pomodoroCommand.execute(tasks, ui, storage));
        assertEquals("☹ OOPS!!! I'm sorry, but I don't know what pomodoro you are referring to",
                exception.getMessage());
    }
}
