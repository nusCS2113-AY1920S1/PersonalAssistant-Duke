package duke;

import duke.commands.Command;

import java.io.File;
import java.io.IOException;

public class Duke {
    private static String savedDataPath1 = "./data/saved_data.txt";
    private static String savedDataPath2 = "./data/tentative.txt";
    private static Ui ui;
    private static Storage storage1;
    private static Storage storage2;
    private static TaskList tasks;
    private static TaskList tentative;

    /**
     * Constructor for main class to initialise the settings.
     */
    private Duke(String filePath1, String filePath2) throws DukeException {
        ui = new Ui();
        try {
            storage1 = new Storage(filePath1);
            tasks = new TaskList(storage1.load());
            storage2 = new Storage(filePath2);
            tentative = new TaskList(storage2.load());
        } catch (DukeException e) {
            new File("./data").mkdir();
            File file1 = new File("./data/saved_data.txt");
            File file2 = new File(savedDataPath2);
            try {
                file1.createNewFile();
                file2.createNewFile();
                storage1 = new Storage(filePath1);
                storage2 = new Storage(filePath2);
                tasks = new TaskList();
                tentative = new TaskList();
            } catch (IOException error) {
                ui.showLoadingError();
            }
        }
    }

    /**
     * Run the rest of the code here.
     */
    private void run() {
        ui.showWelcome();
        boolean isExit = false;
        boolean isTentative = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();
                Command c = Parser.parse(fullCommand);
                isTentative = Parser.isTentative();
                if (isTentative) {
                    c.execute(tentative, ui, storage2);
                } else {
                    c.execute(tasks, ui, storage1);
                }
                isExit = c.isExit();
            } catch (DukeException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }
    }

    /**
     * Program Start.
     */
    public static void main(String[] args) throws DukeException {
        new Duke(savedDataPath1, savedDataPath2).run();
    }

}