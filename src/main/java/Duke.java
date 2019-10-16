import command.Command;
import exception.DukeException;
import parser.ParserFactory;
import storage.Storage;
import task.TaskList;
import ui.Ui;

import java.io.File;
import java.util.ArrayList;

/**
 * The main project class. Initializes a simple task list manager 'Duke' which
 * helps users curate and manage a task list.
 *
 * @author Sai Ganesh Suresh
 * @version v3.0
 */

public class Duke {

    private String filePath = System.getProperty("user.dir") + "/src/DukeDatabase/ArrayList";
    private Storage storage;
    private TaskList tasks;
    private File file = new File(filePath);
    private boolean isExit = false;

    /**
     * This constructor creates a new instance of vital classes and also loads tasks
     * if any from persistent storage.
     */

    public Duke() {
        try {
            storage = new Storage(file);
            tasks = new TaskList(storage.loadFile(file));
        } catch (DukeException e) {
            tasks = new TaskList(new ArrayList<>());
            Ui.printMessage(e.getMessage());
        }
    }

    /**
     * This method runs the main program.
     */

    public void run() {
        Ui.printGreeting();
        Ui.printReminder(tasks);

        do {
            String userInput = Ui.readInput();
            try {
                Command command = ParserFactory.parse(userInput);
                command.execute(tasks, storage);
                isExit = command.isExit();
            } catch (DukeException e) {
                Ui.printMessage(e.getMessage());
            }
        } while (!isExit);
    }

}
