package duke;

import duke.data.Storage;
import duke.module.Schedule;
import duke.sports.ManageStudents;
import duke.sports.MyPlan;
import duke.task.TaskList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.Scanner;

public class Duke extends Application {

    /**
     * ui is the command line user interface object.
     */
    private Ui ui;
    /**
     * Storage is the main class that writes files.
     */
    private Storage storage;
    /**
     * Tasks is the activities that can be saved in a day.
     */
    private TaskList tasks;
    /**
     * Students object that manages students in classes.
     */
    private ManageStudents students;
    /**
     * schedule manages all the tasks in the month/week/day.
     */
    private Schedule schedule;
    /**
     * Plan is the training circuit plan for the day.
     */
    private MyPlan plan;
    /**
     * Width of the UI.
     */
    private final int width = 1280;
    /**
     * Height of the UI.
     */
    private final int height = 720;
    /**
     * Constructor method.
     *
     * @throws FileNotFoundException if storage or schedule files are not found
     * @throws ParseException        if unable to load schedule
     */
    public Duke() throws FileNotFoundException, ParseException {
        ui = new Ui();
        storage = new Storage(".\\src\\main\\java\\duke\\data\\duke.txt");
        tasks = new TaskList();
        students = new ManageStudents();
        schedule = new Schedule(
            new Storage(".\\src\\main\\java\\duke\\data\\timeslots.txt"
            ).loadSchedule());
        plan = new MyPlan();
    }

    /**
     * This program runs the main duke program.
     *
     * @param args                  expects array of string objects
     * @throws  FileNotFoundException if Storage class cannot find files
     * @throws  ParseException        upon loadSchedule error
     */
    public static void main(final String[] args)
        throws FileNotFoundException, ParseException {
        new Duke().run();
    }

    /**
     * Method starts all funtions required to operate main program.
     *
     * @throws FileNotFoundException if Storage class cannot find files
     * @throws ParseException        upon loadSchedule error
     */
    public void run() throws FileNotFoundException, ParseException {
        ui.welcome();
        tasks.addAllList(storage);
        ui.mainMenu();
        while (true) {
            Scanner sc = new Scanner(System.in);
            if (sc.hasNextLine()) {
                String input = sc.nextLine();
                if (input.equals("bye")) {
                    ui.goodbye();
                    System.exit(0);
                }

                ui.readCommand(input, tasks, storage, students, schedule, plan);
            }
        }
    }

    /**
     * Upon running launcher main, start() will run.
     */
    @Override
    public void start(final Stage stage) {
        try {
            URL url = Duke.class.getClassLoader().getResource("view/menu.fxml");
            System.out.println(url);
            Parent root = FXMLLoader.load(url);
            stage.setScene(new Scene(root, width, height));
            stage.setTitle("Sports Manager");
            stage.show();
        } catch (IOException e) {
            System.err.println("Could not find menu.fxml");
        }
    }

}
