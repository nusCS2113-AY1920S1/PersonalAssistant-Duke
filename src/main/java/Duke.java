import duchess.exceptions.DuchessException;
import duchess.logic.commands.Command;
import duchess.model.task.DuchessLog;
import duchess.parser.Parser;
import duchess.storage.Storage;
import duchess.storage.Store;
import duchess.ui.Ui;

public class Duke {

    private Storage storage;
    private Store store;
    private Ui ui;
    private Parser parser;

    /**
     * Creates an instant of Duke to be executed.
     *
     * @param filePath name of file to store tasks
     */
    private Duke(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        parser = new Parser();

        try {
            store = storage.load();
        } catch (DuchessException e) {
            store = new Store();
            ui.showError(e.getMessage());
        }
        storage.addToUndoStackPush(store);
    }

    /**
     * Begins the execution of Duke.
     */
    private void run() {
        ui.showWelcome();
        DuchessLog duchessLog = new DuchessLog();

        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                duchessLog.add(fullCommand);
                ui.beginBlock();
                Command c = parser.parse(fullCommand);
                DuchessLog.addValidCommands(c);

                c.execute(store, ui, storage);

                // Take snapshot here, you save copies of Store store here
                storage.addToUndoStackPush(store);

                isExit = c.isExit();
            } catch (DuchessException e) {
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