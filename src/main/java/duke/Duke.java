package duke;

import java.io.FileNotFoundException;
import java.util.Scanner;

import duke.Data.Storage;
import duke.GUI.ViewModules;
import duke.Task.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

public class Duke extends Application {

    private Ui ui;
    private Storage storage;
    private TaskList tasks;
    private ViewModules viewModules;
    Button button1;
    Button button2;
    Button button3;

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

    @Override
    public void start(Stage stage) {
        //Step 1. Setting up required components
        Pane layoutHome = new Pane();
        //Create buttons and add to layoutHome
        button1 = new Button();
        button1.setText("View Schedule");
        button2 = new Button();
        button2.setText("Manage Students");
        button3 = new Button();
        button3.setText("Manage Training Programmes");

        //This class will handle button events such as clicking on it
//        button1.setOnAction(e -> stage.setScene(layoutSchedule));
//        button2.setOnAction(e -> stage.setScene(layoutStudents));
//        button3.setOnAction(e -> stage.setScene(layoutTraining));
        
        //Create the timetable
        GridPane timetable = ViewModules.timeTable();

        //add all buttons and table to layoutHome
        layoutHome.getChildren().addAll(button1, button2, button3, timetable);

        //Layouts
        Scene scene = new Scene(layoutHome, 1280, 720);
        stage.setScene(scene);
        stage.show();


        //Step 2. Formatting the window to look as expected
        stage.setTitle("Sports Manager");
        stage.setResizable(false);
//        stage.setHeight(720.0);
//        stage.setWidth(1280.0);
        //Move the buttons to their correct positions
        button1.setLayoutX(100);
        button1.setLayoutY(150);
        button2.setLayoutX(100);
        button2.setLayoutY(300);
        button3.setLayoutX(100);
        button3.setLayoutY(450);

        //Change the look of the buttons
        button1.setStyle("-fx-pref-height: 50px; -fx-pref-width: 180px; -fx-background-color: orange;");
        button2.setStyle("-fx-pref-height: 50px; -fx-pref-width: 180px; -fx-background-color: orange;");
        button3.setStyle("-fx-pref-height: 50px; -fx-pref-width: 180px; -fx-background-color: orange;");

        //Move the timetable to its correct position
        timetable.setLayoutX(350);
        timetable.setLayoutY(125);

        //Change the look of the timetable
        timetable.setStyle("-fx-pref-height: 450px; -fx-pref-width: 850px; -fx-background-color: lavender;");
    }

}
