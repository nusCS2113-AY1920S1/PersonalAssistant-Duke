//@@author LongLeCE

package planner.ui.gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import planner.main.CliLauncher;

/**
 * A GUI for Duke using FXML.
 */
public class Main extends Application {

    public static CliLauncher planner;

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setResizable(false);
            MainWindow mainWindow = fxmlLoader.<MainWindow>getController();
            planner = new CliLauncher(mainWindow);
            mainWindow.setPlanner(planner);
            planner.setup();
            stage.show();
            Task task = new Task<Void>() {
                @Override
                public Void call() {
                    planner.run();
                    return null;
                }
            };
            new Thread(task).start();
        } catch (IOException e) {
            e.getCause().getCause().printStackTrace();
        }
    }
}