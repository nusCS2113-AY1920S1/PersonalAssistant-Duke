package duke;

import java.io.FileNotFoundException;
import java.util.Scanner;

import duke.Data.Storage;
import duke.Task.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Duke extends Application {

    private Ui ui;
    private Storage storage;
    private TaskList tasks;
    Button button1;
    Button button2;
    Button button3;

    public Duke() {

    }

    public Duke(String filePath) throws FileNotFoundException {
        ui = new Ui();
        storage = new Storage(filePath);
        tasks = new TaskList();
    }

    /**
     * This program runs the main duke program
     *
     * @param args expects array of string objects
     */
    public static void main(String[] args) throws FileNotFoundException {
        new Duke(".\\src\\main\\java\\duke\\Data\\duke.txt").run();
    }

    public void run() {
        ui.welcome();
        tasks.addAllList(storage);
        while (true) {
            Scanner sc = new Scanner(System.in);
            if (sc.hasNextLine()) {
                String input = sc.nextLine();
                if (input.equals("bye")) {
                    ui.goodbye();
                    break;
                }
                ui.readCommand(input, tasks, storage);
            }
        }
    }

    @Override
    public void start(Stage stage) {
        //Step 1. Setting up required components

        Pane layout = new Pane();    //create a Pane

        button1 = new Button();                //create a button for View Schedule
        button1.setText("View Schedule");

        button2 = new Button();                //create a button for Manage Students
        button2.setText("Manage Students");

        button3 = new Button();                //create a button for Manage Training Programmes
        button3.setText("Manage Training Programmes");

        layout.getChildren().addAll(button1, button2, button3);   //add all buttons to Pane

        Scene scene = new Scene(layout, 1000,600);   //create a scene, set, and show
        stage.setScene(scene);
        stage.show();

        //Step 2. Formatting the window to look as expected

        stage.setTitle("Main menu");
        stage.setResizable(false);
        stage.setHeight(600.0);
        stage.setWidth(1000.0);

        //Move the buttons to their correct positions
        button1.setLayoutX(100);
        button1.setLayoutY(100);

        button2.setLayoutX(100);
        button2.setLayoutY(250);

        button3.setLayoutX(100);
        button3.setLayoutY(400);

        //Change the look of the buttons
        button1.setStyle("-fx-pref-height: 50px; -fx-pref-width: 180px; -fx-background-color: orange;");

        button2.setStyle("-fx-pref-height: 50px; -fx-pref-width: 180px; -fx-background-color: orange;");

        button3.setStyle("-fx-pref-height: 50px; -fx-pref-width: 180px; -fx-background-color: orange;");

    }

}
