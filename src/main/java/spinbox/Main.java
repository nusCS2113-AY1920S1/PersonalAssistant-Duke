package spinbox;

import javafx.scene.layout.GridPane;
import spinbox.exceptions.SpinBoxException;
import spinbox.gui.MainWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * A GUI for SpinBox using FXML.
 */
public class Main extends Application {
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            GridPane gridPane = fxmlLoader.load();
            Scene scene = new Scene(gridPane);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().initializeGui();
            stage.show();

        } catch (SpinBoxException | IOException e) {
            e.printStackTrace();
        }
    }
}