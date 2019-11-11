package userinterface;

import commons.Duke;
import java.io.IOException;
import java.util.logging.Logger;
import commons.DukeLogger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * A GUI for Duke using FXML.
 */
public class Main extends Application {
    private Duke duke = new Duke();
    private final Logger logger = DukeLogger.getLogger(Main.class);

    /**
     * This method sets the platform of the GUI.
     * @param stage The window on which the Duke program will appear on.
     */
    @Override
    public void start(Stage stage) {
        DukeLogger.initialise();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            BorderPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setMinWidth(1000);
            stage.setMinHeight(747);
            fxmlLoader.<MainWindow>getController().setDuke(duke);
            stage.setTitle("BetterDuke");
            stage.show();
        } catch (IOException e) {
            logger.severe("MainWindow.fxml cannot be found.");
        }
    }
}