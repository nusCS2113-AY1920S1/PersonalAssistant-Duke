import duchess.logic.commands.Command;
import duchess.logic.parser.Parser;
import duchess.storage.Storage;
import duchess.storage.Store;
import duchess.ui.Ui;
import duchess.logic.commands.exceptions.DukeException;
import duchess.model.task.TaskList;

public class Duke {

    private Storage storage;
    private Store store;
    private Ui ui;

    /**
     * Creates an instant of Duke to be executed.
     *
     * @param filePath name of file to store tasks
     */
    private Duke(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);

        try {
            store = storage.load();
        } catch (DukeException e) {
            ui.showError(e.getMessage());
            store = new Store();
        }
    }

    /**
     * Begins the execution of Duke.
     */
    private void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.beginBlock();
                Command c = Parser.parse(fullCommand);
                c.execute(store, ui, storage);
                isExit = c.isExit();
            } catch (DukeException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.endBlock();
            }
        }
    }

    public static void main(String[] args) {
        new Duke("data.json").run();
    }

}
