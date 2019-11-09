import duchess.exceptions.DuchessException;
import duchess.logic.commands.Command;
import duchess.model.DuchessHistory;
import duchess.parser.Parser;
import duchess.storage.Storage;
import duchess.storage.Store;
import duchess.ui.Ui;

import java.io.IOException;
import java.util.Arrays;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Duchess {

    private Storage storage;
    private Store store;
    private Ui ui;
    private Parser parser;

    /**
     * Creates an instant of Duchess to be executed.
     *
     * @param filePath name of file to store tasks
     */
    private Duchess(String filePath) {
        Logger logger = Logger.getLogger("Duchess");
        logger.setUseParentHandlers(false);

        try {
            FileHandler logFileHandler = new FileHandler("duchess.log");
            logFileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(logFileHandler);
        } catch (IOException e) {
            System.out.println("Unable to start logger, continuing without logging.");
        }

        ui = new Ui();
        storage = new Storage(filePath);
        parser = new Parser(logger);

        try {
            store = storage.load();
        } catch (DuchessException e) {
            store = new Store();
            ui.showError(e.getMessage());
        }
        storage.addToUndoStackPush(store);
    }

    /**
     * Begins the execution of Duchess.
     */
    private void run() {
        ui.showWelcome();
        DuchessHistory duchessHistory = new DuchessHistory();

        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                duchessHistory.add(fullCommand);
                ui.beginBlock();
                Command c = parser.parse(fullCommand);
                DuchessHistory.addValidCommands(c);

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
        new Duchess("data.json").run();
    }
}