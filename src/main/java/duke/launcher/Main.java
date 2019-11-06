package duke.launcher;

import duke.data.ScheduleStorage;
import duke.parser.ParserCommand;
import duke.data.Storage;
import duke.models.Schedule;
import duke.models.students.StudentList;
import duke.models.MyPlan;
import duke.task.TaskList;
import duke.view.CliView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;

public class Main extends Application {
    /**
     * Declaring new parser type.
     */
    private ParserCommand parser = new ParserCommand();
    /**
     * ui is the command line user interface object.
     */
    private CliView cliView;
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
    private StudentList students;
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
    public Main() throws IOException {
        cliView = new CliView();
        storage = new Storage(".\\src\\main\\java\\duke\\data\\duke.txt");
        tasks = new TaskList();
        students = new StudentList();
        plan = new MyPlan(new Storage(
            ".\\src\\main\\java\\duke\\data\\plan.txt").loadPlans());
    }

    /**
     * This program runs the main duke program.
     *
     * @param args expects array of string objects
     */
    public static void main(final String[] args) {
        CliView cliView = new CliView();
        cliView.execute();
    }

    /**
     * Upon running launcher main, start() will run.
     *
     * @param stage The top level JavaFX container.
     */
    @Override
    public void start(final Stage stage) {
        try {
            URL url = Main.class.getClassLoader().getResource("view/menu.fxml");
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
