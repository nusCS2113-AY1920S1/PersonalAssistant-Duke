package leduc;

import leduc.command.Command;
import leduc.command.ShortcutCommand;
import leduc.exception.DukeException;
import leduc.storage.Storage;
import leduc.task.TaskList;

//import java.net.URL;

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
        this.parser = new Parser();
        ShortcutCommand.initializedSetShortcut();
        String file;
        String configFile;
        String welcomeFile;// file name
        if (filePath.length != 0 ){ // text-ui-test : test file in case of test
            file = filePath[0];
            configFile= filePath[1];
            welcomeFile = filePath[2];
        }
        else{ // no test file
            file = System.getProperty("user.dir")+ "/data/duke.txt";
            configFile= System.getProperty("user.dir")+ "/data/config.txt";
            welcomeFile = System.getProperty("user.dir")+ "/data/welcome.txt";

            // try to use txt file inside the jar file
            //URL file = getClass().getClassLoader().getResource("duke.txt");
            //URL configFile = getClass().getClassLoader().getResource("config.txt");
            //URL welcomeFile = getClass().getClassLoader().getResource("welcome.txt");

        }

        try{
            this.storage = new Storage(file, configFile,welcomeFile);
            this.tasks = new TaskList(storage.load()); // Use of ArrayList (A-Collections) to store tasks
            if(this.storage.getLanguage().equals("en")){
                this.ui = new UiEn();
            }
            else if (this.storage.getLanguage().equals("fr")){
                this.ui = new UiFr();
            }
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
            this.ui.showWelcome(storage);
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
