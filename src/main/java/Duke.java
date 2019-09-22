import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import Commands.*;
import ControlPanel.*;
import Tasks.*;

/**
 * The main class which controls the overall flow, run the program
 */
public class Duke extends Application {

    private Ui ui;
    private TaskList tasks;
    private Storage storage;

    /**
     * Add a default constructor to make GUI launcher run since it would like to
     * initialize duke without any parameter.
     */
    public Duke() {

    }

    /**
     * Duke class acts as a constructor to initialize and setup
     * @param filePath the path of the tasks.txt which contains the data of the tasks' list
     */
    public Duke(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (Exception e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    /**
     * This method runs the overall program
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while(!isExit && ui.inputStatus()){
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();
                Command c = Parser.parse(fullCommand);
                c.execute(tasks, ui, storage);
                isExit = c.isExit();
            } catch (ParseException | DukeException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }

        }
    }

    /**
     * the main class which trigger the program
     * @param args arguments
     * @throws DukeException if any exception is caught
     */
    public static void main(String[] args) throws DukeException {
        Path currentDir = Paths.get("data/tasks.txt");
        //System.out.println(currentDir.toAbsolutePath());
        String file = currentDir.toAbsolutePath().toString();
        //System.out.println(file);
        new Duke(file).run();
    }

    @Override
    public void start(Stage stage) {
        Label helloWorld = new Label("Hello World!"); // Creating a new Label control
        Scene scene = new Scene(helloWorld); // Setting the scene to be our Label

        stage.setScene(scene); // Setting the stage to show our screen
        stage.show(); // Render the stage.
    }

}//duke class
