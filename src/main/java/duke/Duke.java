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

    private Ui ui;
    private Storage storage;
    private TaskList tasks;
    private ManageStudents students;
    private Schedule schedule;
    private MyPlan plan;
    public static Stage window;
    private Storage studentRead;



    public Duke() throws FileNotFoundException, ParseException {
        ui = new Ui();
        storage = new Storage(".\\src\\main\\java\\duke\\data\\duke.txt");
        tasks = new TaskList();
        students = new ManageStudents();
        schedule = new Schedule(new Storage(".\\src\\main\\java\\duke\\data\\timeslots.txt").loadSchedule());
        plan = new MyPlan();
        studentRead = new Storage("C:\\Users\\Dell\\Desktop\\main\\src\\main\\java\\duke\\data\\studentList.txt");
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
    public void start(Stage stage) {
        try {
            URL url = Duke.class.getClassLoader().getResource("view/menu.fxml");
            System.out.println(url);
            Parent root = FXMLLoader.load(url);
            stage.setScene(new Scene(root, 1280,720));
            stage.setTitle("Sports Manager");
            stage.show();
            studentRead.readStudentListFile(students.getStudentList());

        }
        catch (IOException e) {
            System.err.println("Could not find menu.fxml");
        }
    }

}
