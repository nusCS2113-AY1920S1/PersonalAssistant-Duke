package javafx;

import exception.DukeException;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import main.Duke;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * A GUI for JavaFX.Main.Duke using FXML.
 *
 * @author Lee Zhen Yu
 * @version %I%
 * @since 1.0
 */
public class Main extends Application {

    private Duke duke = new Duke("save.txt");

    public Main() throws DukeException {
    }

    /**
     * Constructor to initialize the main window of the GUI.
     * The settings are loaded from an fxml file.
     *
     * @param stage The stage class
     */
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setDuke(duke);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
