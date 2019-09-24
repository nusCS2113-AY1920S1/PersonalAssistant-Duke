import duke.commands.Command;
import duke.exception.DukeException;
import duke.parser.Parser;
import duke.task.TaskList;
import duke.Storage;
import duke.Ui;

import java.io.File;
import java.util.Scanner;

/**
 * <h1>duke</h1>
 * The duke program implements a task Manager for users to
 * keep track of the tasks that they have to complete or attend to.
 *
 * @author Brian Lim
 * @version 1.0
 * @since 2019-09-07
 */
public class Duke {
    /**
     * Stores and keeps track of all user's task.
     */
    private TaskList tasks;

    /**
     * User Interface that handles the response of duke.
     */
    private Ui ui;

    /**
     * Stores filepath for duke to write to and load from.
     */
    private Storage storage;

    /**
     * Creates duke with the specified filePath.
     * @param filePath filePath of text file to load into duke
     */
    public Duke(File filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        tasks = storage.load();
    }

    /**
     * This is the main method which makes use of run method.
     * A specific filepath is called in order to load previously stored
     * tasks into the TaskList param when initiating the program.
     * <p>
     * If the file is loaded successfully, all tasks that was stored previously
     * before program exits is loaded into TaskList, else it will be empty.
     * </p>
     * @param args Unused.
     */
    public static void main(String[] args) {
        File currentDir = new File(System.getProperty("user.dir"));
        File filePath = new File(currentDir.toString() + "\\src\\main\\data\\duke.txt");
        new Duke(filePath).run();
    }

    /**
     * This method is used to start up the duke program.
     * The program will keep running till the user inputs the command
     * 'Bye' and exits the while loop.
     */
    public void run() {
        boolean isExit = false;
        Scanner sc = new Scanner(System.in);
        System.out.println(ui.showWelcome());


        while (!isExit) {
            try {
                String fullCommand = ui.readCommand(sc);
                Command c = Parser.parse(fullCommand);
                c.execute(tasks, ui, storage);
                isExit = c.isExit();
            } catch (DukeException e) {
                ui.setMessage(e.getMessage());
            } catch (NumberFormatException e) {
                ui.setMessage("Invalid command\n");
            } finally {
                System.out.println(ui.showLine());
            }
        }
        sc.close();
    }
}