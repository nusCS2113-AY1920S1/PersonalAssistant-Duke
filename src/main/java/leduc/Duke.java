package leduc;

import leduc.command.Command;
import leduc.command.ShortcutCommand;
import leduc.exception.DukeException;
import leduc.storage.Storage;
import leduc.task.TaskList;

/**
 * Represents the main program leduc.Duke.
 * Run the project from here.
 */
public class Duke {
    private Ui ui;
    private TaskList tasks;
    private Storage storage;
    private Parser parser;

    /**
     * Constructor of leduc.Duke class.
     * Initialization of ui,parser, tasks list and storage.
     * The tasks will load the information provided by the data file.
     * @param filePath String which represents the path of the data file to load.
     */
    public Duke(String[] filePath) {
        this.ui = new Ui();
        this.parser = new Parser();
        ShortcutCommand.initializedSetShortcut();
        String file; // file name
        if (filePath.length != 0 ){ // test file in case of test
            file = filePath[0];
        }
        else{ // no test file
            file = System.getProperty("user.dir")+ "/data/duke.txt";
        }
        String configFile = System.getProperty("user.dir")+ "/data/config.txt";


        try{
            this.storage = new Storage(file, configFile);
            this.tasks = new TaskList(storage.load()); // Use of ArrayList (A-Collections) to store tasks
        }
        catch (DukeException e){
            ui.showError(e);
        }
    }

    /**
     * Method which run the duke program.
     */
    public void run() {
        try {
            this.ui.showWelcome();
        }
        catch (DukeException e){
            ui.showError(e);
        }
        boolean isExit = false;
        while (!isExit){
            try {
                String user = this.ui.readCommand();
                Command c = parser.parse(user);
                c.execute(tasks, ui, storage); // parser is needed because stringToDate is in leduc.Parser class
                isExit = c.isExit();
            }
            catch (DukeException e){ // catch one of subclass of dukeException and print the right message
                e.print();
                ui.showError(e);
            }
        }
    }

    /**
     * Main of leduc.Duke.
     * @param args String[] which could be the data file to load.
     */
    public static void main(String[] args) {
        new Duke(args).run();
    }
}
