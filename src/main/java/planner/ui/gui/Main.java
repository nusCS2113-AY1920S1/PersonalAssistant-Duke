//@@author LongLeCE

package planner.ui.gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import planner.main.CliLauncher;

/**
 * A GUI for Duke using FXML.
 */
public class Main extends Application {

    private CliLauncher planner = new CliLauncher(true);

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/duke.MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setDuke(this.planner);
            stage.show();
        } catch (IOException e) {
            e.getCause().getCause().printStackTrace();
        }
    }
}