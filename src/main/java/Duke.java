import Duke.Commands.Command;
import Duke.Exception.DukeException;
import Duke.Parser.Parser;
import Duke.Task.TaskList;
import Duke.Storage;
import Duke.Ui;

import java.io.File;
import java.util.Scanner;

/**
 * <h1>Duke</h1>
 * The Duke program implements a Task Manager for users to
 * keep track of the tasks that they have to complete or attend to.
 *
 * @author Brian Lim
 * @version 1.0
 * @since 2019-09-07
 */
public class Duke{


    /**
     * Stores and keeps track of all user's task.
     */
    private TaskList tasks;

    /**
     * User Interface that handles the response of Duke.
     */
    private Ui ui;

    /**
     * Stores filepath for Duke to write to and load from.
     */
    private Storage storage;


    public Duke(File filePath){
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
     * This method is used to start up the Duke program.
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
            } catch (NumberFormatException e){
                ui.setMessage("     Invalid Command\n");
            } finally {
                System.out.println(ui.showLine());
            }
        }
        sc.close();
    }
}