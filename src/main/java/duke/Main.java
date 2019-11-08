package duke;

import duke.ui.MainWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

//@@author talesrune-reused
/**
 * A GUI for Duke using FXML.
 */
public class Main extends Application {
    private Duke duke = new Duke();
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public Main() throws IOException {
    }

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();
            Scene scene = new Scene(anchorPane);
            stage.setScene(scene);
            stage.setTitle("DUKE Manager");
            fxmlLoader.<MainWindow>getController().setDuke(duke);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            logger.log(Level.SEVERE,"Unable to load main window.", e);
        }
    }
}