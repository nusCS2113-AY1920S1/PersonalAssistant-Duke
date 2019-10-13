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

            // Adds the very first copy of store into undoStack
            storage.addToUndoStackPush(store);

        } catch (DuchessException e) {
            ui.showError(e.getMessage());
            store = new Store();
        }
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
                Command c = Parser.parse(fullCommand);
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