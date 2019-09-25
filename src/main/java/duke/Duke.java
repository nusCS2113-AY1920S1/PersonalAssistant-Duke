package duke;

import java.io.FileNotFoundException;
import java.util.Scanner;

import duke.Data.Storage;
import duke.GUI.ViewModules;
import duke.Module.Schedule;
import duke.Task.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Duke extends Application {

    private Ui ui;
    private Storage storage;
    private TaskList tasks;
    private ViewModules viewModules;
    Button buttonSchedule;
    Button buttonStudents;
    Button buttonTraining;
    Button buttonMenu;

    public Duke() {

    }

    public Duke(String filePath) throws FileNotFoundException {
        ui = new Ui();
        storage = new Storage(filePath);
        tasks = new TaskList();
        viewModules = new ViewModules();
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

    /**
     * Upon running duke main, Start() will run.
     * Creates the initial layout for the GUI
     *
     * @param stage
     */
    @Override
    public void start(Stage stage) {

        Pane layoutHome = new Pane();

        //Create buttons and add to layoutHome
        buttonSchedule = new Button();
        buttonSchedule.setText("View Schedule");
        buttonStudents = new Button();
        buttonStudents.setText("Manage Students");
        buttonTraining = new Button();
        buttonTraining.setText("Manage Training Programmes");


        Scene sceneSchedule = new Scene(ViewModules.layoutSchedule(), 1280, 720);
        Scene sceneStudents = new Scene(ViewModules.layoutStudents(), 1280, 720);
        Scene sceneTraining = new Scene(ViewModules.layoutTraining(), 1280, 720);

        //Button click event will lead to new scene layout
        buttonSchedule.setOnAction(e -> stage.setScene(sceneSchedule));
        buttonStudents.setOnAction(e -> stage.setScene(sceneStudents));
        buttonTraining.setOnAction(e -> stage.setScene(sceneTraining));

        //Create the timetable of the week
        GridPane timetable = ViewModules.timeTable();

        //add all buttons and table to layoutHome
        layoutHome.getChildren().addAll(buttonSchedule, buttonStudents, buttonTraining, timetable);

        //Scene properties
        Scene scene = new Scene(layoutHome, 1280, 720);
        stage.setScene(scene);
        stage.setTitle("Sports Manager");
        stage.setResizable(false);
        stage.show();


        //Change the look of the buttons
        buttonSchedule.setStyle("-fx-pref-height: 50px; -fx-pref-width: 180px; -fx-background-color: orange;");
        buttonStudents.setStyle("-fx-pref-height: 50px; -fx-pref-width: 180px; -fx-background-color: orange;");
        buttonTraining.setStyle("-fx-pref-height: 50px; -fx-pref-width: 180px; -fx-background-color: orange;");

        //Move the buttons to their correct positions
        buttonSchedule.setLayoutX(100);
        buttonSchedule.setLayoutY(150);
        buttonStudents.setLayoutX(100);
        buttonStudents.setLayoutY(300);
        buttonTraining.setLayoutX(100);
        buttonTraining.setLayoutY(450);
    }

}
