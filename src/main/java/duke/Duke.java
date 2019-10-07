package duke;


import duke.command.Command;
import duke.core.DukeException;
import duke.core.Parser;
import duke.core.Storage;
import duke.core.TaskList;
import duke.core.Ui;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

/**
 * Represents Duke, a Personal Assistant to help
 * users tracking their progress.
 */
public class Duke implements Runnable {
    /**
     * A Storage object that handles reading tasks from a local
     * file and saving them to the same file.
     */
    public static Storage globalStorage;
    /**
     * A TaskList object that deals with add, delete, mark as done,
     * find functions of a list of tasks.
     */
    public static TaskList globalTasks;
    /**
     * A Ui object that deals with interactions with the user.
     */
    public static Ui globalUi;
    /**
     * Constructs a Duke object with a relative file path.
     * Initialize the user interface and reads tasks from the specific text file.
     * @param filePath A string that represents the path of the local file
     *          used for storing tasks.
     */
    public Duke(String filePath) {
        globalUi = new Ui();
        globalStorage = new Storage(filePath);
        try {
            globalTasks = new TaskList(globalStorage.load());
        } catch (DukeException e) {
            globalUi.showLoadingError();
            globalTasks = new TaskList();
        }
    }

    /**
     * Runs the Duke program.
     * Reads user input until a "bye" message is received.
     */
    public void run() {
        try {
            testCSV();
        } catch (IOException e) {
            e.printStackTrace();
        }
        globalUi.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = globalUi.readCommand();
                globalUi.showLine();
                Command c = Parser.parse(fullCommand);
                c.execute(globalTasks, globalUi, globalStorage);
                isExit = c.isExit();
            } catch (DukeException e) {
                globalUi.showError(e.getMessage());
            } finally {
                globalUi.showLine();
            }
        }
        System.exit(0);
    }
    /**
     * Starts the Duke thread and Reminder thread concurrently
     * by passing a filepath to duke and a global ui object&
     * task list to Reminder
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        Duke d = new Duke("./data/duke.txt");
        Reminder r = new Reminder(globalTasks,globalUi);
        Thread t1 = new Thread(d);
        Thread t2 = new Thread(r);
        t1.start();
        t2.start();
    }
    public void testCSV() throws FileNotFoundException, IOException {
        Reader in = new FileReader("./data/file.csv");
        Iterable<CSVRecord> records = CSVFormat.EXCEL.withHeader("Last Name", "First Name").withFirstRecordAsHeader().parse(in);
//        Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
        for (CSVRecord record : records) {
            String lastName = record.get("Last Name");
            String firstName = record.get("First Name");
            System.out.println(lastName + " | " + firstName);
        }
    }
}
