package duke.logic;

import duke.command.Command;
import duke.commons.DukeException;
import duke.parser.Parser;
import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;

public class Duke {

    private static final Storage STORAGE = new Storage("duke.dat");
    private static TaskList tasks = new TaskList();
    private Ui ui;

    public Duke(Ui ui) {
        this.ui = ui;
        try {
            tasks = STORAGE.deserialize();
        } catch (DukeException e) {
            e.printStackTrace();
            ui.showError(e.getMessage());
            ui.disableInput();
        }
        ui.refreshTaskList(tasks, tasks);
    }

    public void executeInput(String input) {
        try {
            Command command = Parser.getCommand(input);
            command.execute(tasks, STORAGE, ui);
        } catch (DukeException e) {
            ui.showError(e.getMessage());
        }
    }

}
