package duke;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.Scanner;

import duke.Data.Storage;
import duke.GUI.ViewModules;
import duke.Task.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Duke extends Application {

    private Ui ui;
    private Storage storage;
    private TaskList tasks;
    public static Stage window;

    public Duke() {

    }

    public Duke(String filePath) throws FileNotFoundException {
        ui = new Ui();
        storage = new Storage(filePath);
        tasks = new TaskList();
    }

    /**
     * This program runs the main duke program
     * @param args expects array of string objects
     */
    public static void main(String[] args) throws FileNotFoundException, ParseException {
        new Duke(".\\src\\main\\java\\duke\\Data\\duke.txt").run();
    }

    public void run() throws FileNotFoundException, ParseException {
        ui.welcome();
        tasks.addAllList(storage);
        while (true) {
            Scanner sc = new Scanner(System.in);
            if (sc.hasNextLine()) {
                String input = sc.nextLine();
                if (input.equals("bye")) {
                    ui.goodbye();
                    System.exit(0);
                }
                ui.readCommand(input, tasks, storage);
            }
        }
    }

    /**
     * Upon running launcher main, start() will run.
     */
    @Override
    public void start(Stage stage) {
        window = stage;
        Scene scene = new Scene(ViewModules.layoutHome(), 1280, 720);
        stage.setScene(scene);
        stage.setTitle("Sports Manager");
        stage.setResizable(false);
        stage.show();
        window.setScene(scene);
    }

}
