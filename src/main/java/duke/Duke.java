package duke;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.Scanner;

import duke.Data.Storage;
import duke.GUI.ViewModules;
import duke.Module.Schedule;
import duke.Sports.ManageStudents;
import duke.Task.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Duke extends Application {

    private Ui ui;
    private Storage storage;
    private TaskList tasks;
    private ManageStudents students;
    private Schedule schedule;
    public static Stage window;



    public Duke() throws FileNotFoundException, ParseException {
        ui = new Ui();
        storage = new Storage(".\\src\\main\\java\\duke\\Data\\duke.txt");
        tasks = new TaskList();
        students = new ManageStudents();
        Schedule schedule = new Schedule(new Storage(".\\src\\main\\java\\duke\\Data\\timeslots.txt").loadSchedule());

    }

    /**
     * This program runs the main duke program
     * @param args expects array of string objects
     */
    public static void main(String[] args) throws FileNotFoundException, ParseException {
        new Duke().run();
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

                ui.readCommand(input, tasks, storage, students, schedule);
            }
        }
    }

    /**
     * Upon running launcher main, start() will run.
     */
    @Override
    public void start(Stage stage) {
        try {
            URL url = Duke.class.getClassLoader().getResource("view/menu.fxml");
            System.out.println(url);
            Parent root = FXMLLoader.load(url);
            stage.setScene(new Scene(root, 1280,720));
            stage.setTitle("Sports Manager");
            stage.show();

//            Scene scene = new Scene(ViewModules.layoutHome(), 1280, 720);
//            stage.setScene(scene);
//            stage.setTitle("Sports Manager");
//            stage.setResizable(false);
//            stage.show();
//            window.setScene(scene);
        }
        catch (IOException e) {
            System.err.println("Could not find sample.fxml");
        }
    }

}
