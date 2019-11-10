package spinbox;

import javafx.scene.layout.GridPane;
import spinbox.exceptions.SpinBoxException;
import spinbox.gui.MainWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * A GUI for SpinBox using FXML.
 */
public class Main extends Application {
    private static final String APPLICATION_NAME = "SpinBox v1.4";
    private static final Logger LOGGER = Logger.getLogger(Main.class.getPackageName());
    private static final String LOGGER_SAVE_FAIL = "Logs cannot be saved to file.";

    @Override
    public void start(Stage stage) {
        try {
            FileHandler fileHandler = new FileHandler("SpinBoxData/spinBox-log.txt",
                    true);
            fileHandler.setFormatter(new SimpleFormatter());
            fileHandler.setLevel(Level.ALL);
            LOGGER.setUseParentHandlers(false);
            LOGGER.addHandler(fileHandler);
        } catch (IOException e) {
            LOGGER.warning(LOGGER_SAVE_FAIL);
        }
        LOGGER.setLevel(Level.WARNING);
        LOGGER.entering(getClass().getName(),"start");
        try {
            stage.setTitle(APPLICATION_NAME);
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            GridPane gridPane = fxmlLoader.load();
            Scene scene = new Scene(gridPane);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().initializeGui();
            stage.show();

        } catch (SpinBoxException | IOException e) {
            e.printStackTrace();
        }
        LOGGER.exiting(getClass().getName(),"start");
    }
}